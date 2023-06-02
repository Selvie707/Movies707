package com.example.movies.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movies.R;
import com.example.movies.adapter.GenreAdapter;
import com.example.movies.adapter.RecentlyWatchedAdapter;
import com.example.movies.adapter.SliderAdapter;
import com.example.movies.api.GetApi;
import com.example.movies.api.Retrofit;
import com.example.movies.data.SliderItem;
import com.example.movies.detail.DetailRecentlyWatched;
import com.example.movies.detail.EditProfile;
import com.example.movies.domain.CategoryDomain;
import com.example.movies.models.Genre;
import com.example.movies.models.Result;
import com.example.movies.models.Root;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private SearchView svSearch;
    private TextView tvUserName;
    private EditText etSearch;

    private ProgressBar pbMain;
    private RecentlyWatchedAdapter adapterTrending;
    private ArrayList<Result> alTrending;

    private ArrayList<Genre> alGenre;
    private GenreAdapter adapterGenre;

    private BottomNavigationView bnvBottomMenu;
    private ActionBar abJudulBar;

    private ImageView ivCategory;

    private RecyclerView.Adapter rvadapter;
    private RecyclerView rvCategory, rvTrending;

//    private ArrayList<Result> rwData;

    private ViewPager2 vp2;

    private Handler sliderHandler = new Handler();

    private String username;

    private int mView = 0;

    static final String STATE_MODE = "MODE VIEW";

    // Card Recently Watched
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        outState.putInt(STATE_MODE, mView);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("theacc", MODE_PRIVATE);
        username = sp.getString("username", "");

        svSearch = findViewById(R.id.et_main_search);
        svSearch.clearFocus();
        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });

        tvUserName = findViewById(R.id.tv_welcomeName);
        tvUserName.setText(username);

//        Intent intent = getIntent();
//        tvUserName.setText(intent.getStringExtra("xUsername"));

        Popular();

        rvTrending = findViewById(R.id.rv_recently_watched);
        rvTrending.setHasFixedSize(true);
        pbMain = findViewById(R.id.pb_main);
        rvTrending.setLayoutManager(new GridLayoutManager(this, 3));

        LinearLayoutManager llm = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        rvCategory = findViewById(R.id.rv_category);
        rvCategory.setLayoutManager(llm);

        RetriverCategory();
        RetriverTrending();

//        rwData.addAll(DataRecentlyWatched.DataMovie());

//        if (savedInstanceState != null) {
//            mView = savedInstanceState.getInt(STATE_MODE);
//            ShowDataRWMovie();
//        }
//        else {
//            ShowDataRWMovie();
//        }

        BottomMenu();
    }

    // Bottom Menu
    private void BottomMenu () {
        bnvBottomMenu = findViewById(R.id.bn_menu_bottom);
        bnvBottomMenu.setSelectedItemId(R.id.menu_home);

        bnvBottomMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        return true;
                    case R.id.menu_movie:
                        startActivity(new Intent(getApplicationContext(), MovieActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.menu_download:
                        startActivity(new Intent(getApplicationContext(), DownloadActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.menu_favorit:
                        startActivity(new Intent(getApplicationContext(), FavoriteActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.menu_profile:

                        String username = "Via707";
                        String bio = "let's go to the space";

                        Intent intentProfile = new Intent(getApplicationContext(), ProfileActivity.class);

                        intentProfile.putExtra("varProfilName", username);
                        intentProfile.putExtra("varProfilBio", bio);

                        startActivity(intentProfile);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }

    private void filterList(String text) {
        ArrayList<Result> filteredList = new ArrayList<>();
        try {
            for (Result item : alTrending) {
                if (item.getTitle() != null) {
                    if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                        filteredList.add(item);
                    }
                }
            }
        } catch (NullPointerException e) {
            Log.e(TAG, "Caught NullPointerException: " + e.getMessage());
            e.printStackTrace();
        }


        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No Movie Found", Toast.LENGTH_SHORT).show();
        } else {
            adapterTrending.setFilteredList(filteredList);
        }
    }

    private void RetriverTrending() {
        pbMain.setVisibility(View.VISIBLE);

        GetApi ardData = Retrofit.getRetrofit().create(GetApi.class);
        Call<Root> retriverProcess = ardData.getTrending("0a9d3ed8c00f265589f595b6f3b92228");

        retriverProcess.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                alTrending = response.body().getResults();
                adapterTrending = new RecentlyWatchedAdapter(alTrending);
                rvTrending.setAdapter(adapterTrending);
                pbMain.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal guys", Toast.LENGTH_SHORT).show();
                pbMain.setVisibility(View.INVISIBLE);
            }
        });
    }

    // Popular Image Slider
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            vp2.setCurrentItem(vp2.getCurrentItem() + 1);
        }
    };

    // Popular Image Slider
    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    // Popular Image Slider
    @Override
    protected void onResume() {
        super.onResume();
//        RetriverTrending();
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }

    // Popular Image Slider
    private void Popular() {
        vp2 = findViewById(R.id.vp_popular);

        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.nutcracker));
        sliderItems.add(new SliderItem(R.drawable.cinderella));
        sliderItems.add(new SliderItem(R.drawable.peterpan));

        vp2.setAdapter(new SliderAdapter(sliderItems, vp2));
        vp2.setClipToPadding(false);
        vp2.setClipChildren(false);
        vp2.setOffscreenPageLimit(3);
        vp2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer cpt = new CompositePageTransformer();
        cpt.addTransformer(new MarginPageTransformer(32));
        cpt.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });

        vp2.setPageTransformer(cpt);

        vp2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000);
            }
        });

        vp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailRecentlyWatched.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Genre Category
    private void RetriverCategory() {
        pbMain.setVisibility(View.VISIBLE);

        GetApi ardData = Retrofit.getRetrofit().create(GetApi.class);
        Call<Root> retriverCategory = ardData.getGenre("0a9d3ed8c00f265589f595b6f3b92228");

        retriverCategory.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                alGenre = response.body().getGenres();
                adapterGenre = new GenreAdapter(alGenre);
                rvCategory.setAdapter(adapterGenre);
                pbMain.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal guys", Toast.LENGTH_SHORT).show();
                pbMain.setVisibility(View.INVISIBLE);
            }
        });
    }

    // Category
//    private void RVCategory() {
//        LinearLayoutManager llm = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
//        rvCategory = findViewById(R.id.rv_category);
//        rvCategory.setLayoutManager(llm);
//
//        ArrayList<CategoryDomain> category = new ArrayList<>();
//        category.add(new CategoryDomain("Drama", "@drawable/picpic"));
//        category.add(new CategoryDomain("Romance", "@drawable/picpic"));
//        category.add(new CategoryDomain("Fantasy", "@drawable/picpic"));
//        category.add(new CategoryDomain("Action", "@drawable/picpic"));
//        category.add(new CategoryDomain("Horror", "@drawable/picpic"));
//        category.add(new CategoryDomain("Comedy", "@drawable/picpic"));
//
//        rvadapter = new CategoryAdapter(category);
//        rvCategory.setAdapter(rvadapter);
//    }

        // Recently Watched
//    private void ShowDataRWMovie() {
//        mView = 0;
////        int colCount = getResources().getInteger(R.integer.)
//        rvTrending.setLayoutManager(new GridLayoutManager(this, 3));
//        RecentlyWatchedAdapter rwa = new RecentlyWatchedAdapter(rwData);
//        rvTrending.setAdapter(rwa);
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    // Recently Watched
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        ShowDataRWMovie();
        return super.onOptionsItemSelected(item);
    }
}