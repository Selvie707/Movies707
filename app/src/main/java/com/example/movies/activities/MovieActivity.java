package com.example.movies.activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.movies.R;
import com.example.movies.adapter.MovieAdapter;
import com.example.movies.adapter.SliderAdapter;
import com.example.movies.api.GetApi;
import com.example.movies.api.Retrofit;
import com.example.movies.data.SliderItem;
import com.example.movies.models.Result;
import com.example.movies.models.Root;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieActivity extends AppCompatActivity {
    private ProgressBar pbMovie;
    private MovieAdapter adapterMovie;
    private ArrayList<Result> listMovie;
    private LinearLayoutManager llmMovie;
    private SearchView svSearch;
    private BottomNavigationView bnvBottomMenu;
    private RecyclerView rvRecentlyWatched;
    private TextView tvInfoemptylist;

//    private ArrayList<Movie> movieData = new ArrayList<>();

    private ViewPager2 vp2;

    private Handler sliderHandler = new Handler();

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
        setContentView(R.layout.activity_movie);

        Popular();

        tvInfoemptylist = findViewById(R.id.tv_download_infoemptylist);

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

        rvRecentlyWatched = findViewById(R.id.rv_movie_movie);
        rvRecentlyWatched.setHasFixedSize(true);

        pbMovie = findViewById(R.id.pb_movie);
        rvRecentlyWatched.setLayoutManager(new GridLayoutManager(this, 3));

        RetriverMovie();

//        listMovie.addAll(DataMovie.Movie());
//
//        if (savedInstanceState != null) {
//            mView = savedInstanceState.getInt(STATE_MODE);
//            ShowDataRWMovie();
//        }
//        else {
//            ShowDataRWMovie();
//        }

        BottomMenu();
    }

    private void filterList(String text) {
        ArrayList<Result> filteredList = new ArrayList<>();
        try {
            for (Result item : listMovie) {
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
            tvInfoemptylist.setVisibility(View.VISIBLE);
            rvRecentlyWatched.setVisibility(View.GONE);
        } else {
            tvInfoemptylist.setVisibility(View.GONE);
            rvRecentlyWatched.setVisibility(View.VISIBLE);
            adapterMovie.setFilteredList(filteredList);
        }
    }

    // Bottom Menu
    private void BottomMenu () {
        bnvBottomMenu = findViewById(R.id.bn_menu_bottom);
        bnvBottomMenu.setSelectedItemId(R.id.menu_movie);

        bnvBottomMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.menu_movie:
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

    private void RetriverMovie() {
        pbMovie.setVisibility(View.VISIBLE);

        GetApi ardData = Retrofit.getRetrofit().create(GetApi.class);
        Call<Root> retriverProcess = ardData.getMovie("0a9d3ed8c00f265589f595b6f3b92228");

        retriverProcess.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                listMovie = response.body().getResults();
                adapterMovie = new MovieAdapter(listMovie);
                rvRecentlyWatched.setAdapter(adapterMovie);
                pbMovie.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(MovieActivity.this, "Gagal guys", Toast.LENGTH_SHORT).show();
                pbMovie.setVisibility(View.INVISIBLE);
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
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }

    // Popular Image Slider
    private void Popular() {
        vp2 = findViewById(R.id.vp_popular_movie);

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
    }

    // Recently Watched
//    private void ShowDataRWMovie() {
//        mView = 0;
////        int colCount = getResources().getInteger(R.integer.)
//        rvRecentlyWatched.setLayoutManager(new GridLayoutManager(this, 3));
//        MovieAdapter rwa = new MovieAdapter(listMovie);
//        rvRecentlyWatched.setAdapter(rwa);
//    }

    // Recently Watched
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        ShowDataRWMovie();
        return super.onOptionsItemSelected(item);
    }
}