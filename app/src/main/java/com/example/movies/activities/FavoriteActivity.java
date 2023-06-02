package com.example.movies.activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;
import com.example.movies.adapter.FavoritAdapter;
import com.example.movies.apivia.Retrofit;
import com.example.movies.apivia.TheApi;
import com.example.movies.myfavmodels.Datum;
import com.example.movies.myfavmodels.Root;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteActivity extends AppCompatActivity {
    private TextView tvInfoemprylist;
    private Spinner spSort, spYear, spGenre, spRatting;
    private SearchView svSearch;
    private BottomNavigationView bnvBottomMenu;
    private ProgressBar pbMovie;

    private RecyclerView rvRecentlyWatched;

    private FavoritAdapter adapterFavorit;
    private ArrayList<Datum> listFavorit;
    private int theid;
    private String sortBy, selectedYear, selectedGenre;

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
        setContentView(R.layout.activity_favorite);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("theacc", MODE_PRIVATE);
        theid = Integer.parseInt(sp.getString("id", ""));

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

        tvInfoemprylist = findViewById(R.id.tv_favorite_infoemptylist);

        spSort = findViewById(R.id.sp_az);
        spYear = findViewById(R.id.sp_year);
        spGenre = findViewById(R.id.sp_genre);
        spRatting = findViewById(R.id.sp_ratting);

        rvRecentlyWatched = findViewById(R.id.rv_movie_movie);
        rvRecentlyWatched.setHasFixedSize(true);

        pbMovie = findViewById(R.id.pb_main);
        rvRecentlyWatched.setLayoutManager(new GridLayoutManager(this, 3));

        spSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RetriverMovie(theid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RetriverMovie(theid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RetriverMovie(theid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        RetriverMovie(theid);

        bnvBottomMenu = findViewById(R.id.bn_menu_bottom);
        bnvBottomMenu.setSelectedItemId(R.id.menu_favorit);

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
                        return true;
                    case R.id.menu_profile:
                        Intent intentProfile = new Intent(getApplicationContext(), ProfileActivity.class);
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
        ArrayList<Datum> filteredList = new ArrayList<>();
        try {
            for (Datum item : listFavorit) {
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
            adapterFavorit.setFilteredList(filteredList);
        }
    }

    private void RetriverMovie(int theid) {
        pbMovie.setVisibility(View.VISIBLE);

        TheApi ardData = Retrofit.getRetrofit().create(TheApi.class);
        Call<Root> retriverProcess = ardData.getTheMovie(theid, spSort.getSelectedItem().toString(), spYear.getSelectedItem().toString(), spGenre.getSelectedItem().toString());

        retriverProcess.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.body().getPesan().equals("Data Kosong")) {
                    tvInfoemprylist.setVisibility(View.VISIBLE);
                    rvRecentlyWatched.setVisibility(View.GONE);
                    pbMovie.setVisibility(View.INVISIBLE);
                }
                if (response.isSuccessful()) {
                    listFavorit = response.body().getData();
                    if (listFavorit != null) {
                        rvRecentlyWatched.setVisibility(View.VISIBLE);
                        tvInfoemprylist.setVisibility(View.GONE);
                        adapterFavorit = new FavoritAdapter (listFavorit);
                        rvRecentlyWatched.setAdapter(adapterFavorit);
                        pbMovie.setVisibility(View.INVISIBLE);
                    } else {

                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(FavoriteActivity.this, "Gagal guys", Toast.LENGTH_SHORT).show();
                pbMovie.setVisibility(View.INVISIBLE);
            }
        });
    }

//    private void ShowDataRWMovie() {
//        mView = 0;
////        int colCount = getResources().getInteger(R.integer.)
//        rvRecentlyWatched.setLayoutManager(new GridLayoutManager(this, 3));
//        FavoritAdapter rwa = new FavoritAdapter(favoritData);
//        rvRecentlyWatched.setAdapter(rwa);
//    }

    // Recently Watched
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        ShowDataRWMovie();
        return super.onOptionsItemSelected(item);
    }
}