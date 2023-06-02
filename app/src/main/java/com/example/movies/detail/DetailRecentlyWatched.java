package com.example.movies.detail;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movies.R;
import com.example.movies.adapter.DetailGenreAdapter;
import com.example.movies.api.GetApi;
import com.example.movies.api.Retrofit;
import com.example.movies.apivia.TheApi;
import com.example.movies.detailmodel.Genre;
import com.example.movies.detailmodel.Root;
import com.example.movies.login.TheSignUp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRecentlyWatched extends AppCompatActivity {
    private String arrayString;
    private ArrayList<Genre> alGenre;
    private DetailGenreAdapter adapterGenre;
    private LinearLayoutManager llmMovie;

    private RecyclerView rvGenre;
    private ImageView ivPhoto, ivDownload;
    private TextView tvTitle, tvOverview, tvRate;

    private String idd, title, releasedate, genre, posterpath, sinopsis;
    private int theid;
    private double ratting;

    private CheckBox cbFavorit;

    private int mView = 0; // 0 card, 1 grid
    //static variabel
    static final String STATE_MODE = "MODE VIEW";

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(STATE_MODE, mView);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recently_watched);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("theacc", MODE_PRIVATE);
        theid = Integer.parseInt(sp.getString("id", ""));

//        Log.d("spTheid", String.valueOf(theid));

        Intent intent = getIntent();
        int id = Integer.parseInt(intent.getStringExtra("varId"));

        rvGenre = findViewById(R.id.rv_detailrecentlywatched_genre);

        llmMovie = new GridLayoutManager(this, 2);
        rvGenre.setLayoutManager(llmMovie);

        ivPhoto = findViewById(R.id.iv_detail_movie);
        tvTitle = findViewById(R.id.tv_detail_title);
        tvRate = findViewById(R.id.tv_detailrecentlywatched_ratting);
        tvOverview = findViewById(R.id.tv_detail_sinopsis);

        RetriverMovie();

        GetApi api = Retrofit.getRetrofit().create(GetApi.class);
        Call<Root> detailApi = api.getDetail(Integer.parseInt(String.valueOf(id)),
                ("0a9d3ed8c00f265589f595b6f3b92228"));

        detailApi.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                idd = String.valueOf(response.body().getId());
                title = response.body().getTitle();
                releasedate = response.body().getRelease_date();
                genre = String.valueOf(response.body().getGenres());
                posterpath = response.body().getPoster_path();
                sinopsis = response.body().getOverview();
                ratting = response.body().getVote_average();

                Log.d("favoritelist", "onResponse: " + theid+idd+title+releasedate+genre+posterpath+sinopsis+ratting+RetriverMovie());

                String rate = String.valueOf(response.body().getVote_average());

                tvTitle.setText(response.body().getTitle());
                tvRate.setText(rate);
                tvOverview.setText(response.body().getOverview());
                Glide.with(DetailRecentlyWatched.this).load(response.body().getPoster_path()).into(ivPhoto);

                cbFavorit = findViewById(R.id.cb_detail_favorite);
                cbFavorit.setOnClickListener(view -> {
                    TheApi ardData = com.example.movies.apivia.Retrofit.getRetrofit().create(TheApi.class);
                    Call<com.example.movies.myfavmodels.Root> retPro = ardData.createMovie(idd, theid, title, posterpath, RetriverMovie(), ratting, releasedate, sinopsis.replace("'", ""));

                    retPro.enqueue(new Callback<com.example.movies.myfavmodels.Root>() {
                        @Override
                        public void onResponse(Call<com.example.movies.myfavmodels.Root> call1, Response<com.example.movies.myfavmodels.Root> response1) {
                            if (response1.body().getPesan().equals("ID sudah ada")) {
                                Toast.makeText(DetailRecentlyWatched.this, "This Movie is already on your favorite list, let's watch it!", Toast.LENGTH_SHORT).show();
                            } else {
                                if (cbFavorit.isChecked()) {
                                    int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                                    boolean isDarkTheme = currentNightMode == Configuration.UI_MODE_NIGHT_YES;

                                    AlertDialog.Builder dialog = new AlertDialog.Builder(DetailRecentlyWatched.this);
                                    View abView = getLayoutInflater().inflate(R.layout.alert_box_addmovie, null);
                                    dialog.setView(abView);

                                    AlertDialog theAlertDialog = dialog.create();

                                    if (isDarkTheme) {
                                        theAlertDialog.getWindow().getDecorView().setBackgroundResource(R.drawable.outlined_alertboxdark);
                                    } else {
                                        theAlertDialog.getWindow().getDecorView().setBackgroundResource(R.drawable.outlined_alertbox);
                                    }

                                    theAlertDialog.setCancelable(false);

                                    abView.findViewById(R.id.btn_alertbox_confirm).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            theAlertDialog.dismiss();
                                            finish();
                                        }
                                    });
                                    theAlertDialog.show();
                                }
                                else {
                                    TheApi ardDatas = com.example.movies.apivia.Retrofit.getRetrofit().create(TheApi.class);
                                    Call<com.example.movies.myfavmodels.Root> retPros = ardDatas.deleteFavorite(idd, theid);
                                    Log.d("Unfavorite", idd + theid);
                                    retPros.enqueue(new Callback<com.example.movies.myfavmodels.Root>() {
                                        @Override
                                        public void onResponse(Call<com.example.movies.myfavmodels.Root> call, Response<com.example.movies.myfavmodels.Root> response) {
                                            int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                                            boolean isDarkTheme = currentNightMode == Configuration.UI_MODE_NIGHT_YES;

                                            AlertDialog.Builder dialog = new AlertDialog.Builder(DetailRecentlyWatched.this);
                                            View abView = getLayoutInflater().inflate(R.layout.alert_box_removemovie, null);
                                            dialog.setView(abView);

                                            AlertDialog theAlertDialog = dialog.create();

                                            if (isDarkTheme) {
                                                theAlertDialog.getWindow().getDecorView().setBackgroundResource(R.drawable.outlined_alertboxdark);
                                            } else {
                                                theAlertDialog.getWindow().getDecorView().setBackgroundResource(R.drawable.outlined_alertbox);
                                            }

                                            theAlertDialog.setCancelable(false);

                                            abView.findViewById(R.id.btn_alertbox_confirm).setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    theAlertDialog.dismiss();
                                                    finish();
                                                }
                                            });
                                            theAlertDialog.show();
                                        }

                                        @Override
                                        public void onFailure(Call<com.example.movies.myfavmodels.Root> call, Throwable t) {
                                            MyToast("Something went wrong while unfavorite the movie");
                                            finish();
                                        }
                                    });
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<com.example.movies.myfavmodels.Root> call1, Throwable t) {
                            Toast.makeText(DetailRecentlyWatched.this, "Gagal terhubung ke server : "
                                    + t.getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                });

                ivDownload = findViewById(R.id.iv_detailrecentlywatched_download);
                ivDownload.setOnClickListener(v -> {
                    TheApi apiDownload = com.example.movies.apivia.Retrofit.getRetrofit().create(TheApi.class);
                    Call<com.example.movies.myfavmodels.Root> retPro = apiDownload.createDownload(idd, theid, title, posterpath, RetriverMovie(), ratting, releasedate, sinopsis.replace("'", ""));

                    retPro.enqueue(new Callback<com.example.movies.myfavmodels.Root>() {
                        @Override
                        public void onResponse(Call<com.example.movies.myfavmodels.Root> call1, Response<com.example.movies.myfavmodels.Root> response1) {
                            if (response1.body().getPesan().equals("ID sudah ada")) {
                                Toast.makeText(DetailRecentlyWatched.this, "This Movie is already on your download list, let's watch it!", Toast.LENGTH_SHORT).show();
                            } else {
                                int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                                boolean isDarkTheme = currentNightMode == Configuration.UI_MODE_NIGHT_YES;

                                AlertDialog.Builder dialog = new AlertDialog.Builder(DetailRecentlyWatched.this);
                                View abView = getLayoutInflater().inflate(R.layout.alert_box_addmovie, null);
                                dialog.setView(abView);

                                AlertDialog theAlertDialog = dialog.create();

                                if (isDarkTheme) {
                                    theAlertDialog.getWindow().getDecorView().setBackgroundResource(R.drawable.outlined_alertboxdark);
                                } else {
                                    theAlertDialog.getWindow().getDecorView().setBackgroundResource(R.drawable.outlined_alertbox);
                                }

                                theAlertDialog.setCancelable(false);

                                abView.findViewById(R.id.btn_alertbox_confirm).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        theAlertDialog.dismiss();
                                        finish();
                                    }
                                });
                                theAlertDialog.show();
                            }
                        }

                        @Override
                        public void onFailure(Call<com.example.movies.myfavmodels.Root> call1, Throwable t) {
                            Toast.makeText(DetailRecentlyWatched.this, "Gagal terhubung ke server : "
                                    + t.getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                });
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {

            }
        });
    }

    private void MyToast (String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private String RetriverMovie() {

        Intent intent = getIntent();
        String id = intent.getStringExtra("varId");

//        Log.d("genreid", id);

        GetApi apii = Retrofit.getRetrofit().create(GetApi.class);
        Call<Root> retriverGenre = apii.getDetail(Integer.parseInt(id), ("0a9d3ed8c00f265589f595b6f3b92228"));

        retriverGenre.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                    LinearLayoutManager llm = new LinearLayoutManager(DetailRecentlyWatched.this, LinearLayoutManager.HORIZONTAL, false);
                    rvGenre.setLayoutManager(llm);
    //                if (alGenre == null) {
    //                    Toast.makeText(DetailRecentlyWatched.this, "There is no genre for this movie", Toast.LENGTH_SHORT).show();
    //                } else {
                    alGenre = response.body().getGenres();
                    arrayString = TextUtils.join(", ", alGenre);
                    adapterGenre = new DetailGenreAdapter(alGenre);
                    rvGenre.setAdapter(adapterGenre);
//                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(DetailRecentlyWatched.this, "Gagal guys", Toast.LENGTH_SHORT).show();
            }
        });

        return arrayString;
    }

    @Override
    protected void onResume() {
        super.onResume();
        RetriverMovie();
    }
}