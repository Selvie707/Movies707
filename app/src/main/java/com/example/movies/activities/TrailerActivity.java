package com.example.movies.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movies.R;
import com.example.movies.adapter.DownloadAdapter;
import com.example.movies.adapter.TrailerAdapter;
import com.example.movies.api.GetApi;
import com.example.movies.apivia.Retrofit;
import com.example.movies.apivia.TheApi;
import com.example.movies.detail.DetailDownloadMovie;
import com.example.movies.myfavmodels.Datum;
import com.example.movies.myfavmodels.Root;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrailerActivity extends AppCompatActivity {
    private ImageView ivBack;
    private TextView tvTitle;
    private RecyclerView rvTrailerList;
    private YouTubePlayerView ypvVideo;
    private FloatingActionButton fabFullScreen, fabBack;
    private ArrayList<Datum> listFavorit;
    private TrailerAdapter trailerAdapter;
    private ProgressBar pbMovie;
    private int theid, iid;
    private String idd, title, releasedate, genre, posterpath, sinopsis, trailerid, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("theacc", MODE_PRIVATE);
        theid = Integer.parseInt(sp.getString("id", ""));

        Intent intent = getIntent();
        id = intent.getStringExtra("varIdd");

        if (id != null && !id.isEmpty()) {
            iid = Integer.parseInt(id);
        } else {
            Toast.makeText(this, "The ID is empty guys", Toast.LENGTH_SHORT).show();
        }

        pbMovie = findViewById(R.id.pb_main);
        rvTrailerList = findViewById(R.id.rv_trailer_trailerlist);
        ypvVideo = findViewById(R.id.yp_detail_trailer);
        fabFullScreen = findViewById(R.id.fab_trailer_screen);
        tvTitle = findViewById(R.id.tv_trailer_judul);
        ivBack = findViewById(R.id.iv_trailer_back);
        fabBack = findViewById(R.id.fab_trailer_back);

        rvTrailerList.setHasFixedSize(true);
        rvTrailerList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        RetriverMovie(theid);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrailerActivity.this, DetailDownloadMovie.class));
                finish();
            }
        });

        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrailerActivity.this, DownloadActivity.class));
                finish();
            }
        });

        fabFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int orientation = getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    fabFullScreen.setImageResource(R.drawable.ic_exitfullscreen);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    fabFullScreen.setImageResource(R.drawable.ic_fullscreen);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    Toast.makeText(TrailerActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        GetApi api = com.example.movies.api.Retrofit.getRetrofit().create(GetApi.class);
        Call<com.example.movies.detailmodel.Root> detailApi = api.getDetail(iid, ("0a9d3ed8c00f265589f595b6f3b92228"));

        detailApi.enqueue(new Callback<com.example.movies.detailmodel.Root>() {
            @Override
            public void onResponse(Call<com.example.movies.detailmodel.Root> call, Response<com.example.movies.detailmodel.Root> response) {
                idd = String.valueOf(response.body().getId());
                title = response.body().getTitle();
                releasedate = response.body().getRelease_date();
                genre = String.valueOf(response.body().getGenres());
                posterpath = response.body().getPoster_path();
                sinopsis = response.body().getOverview();

                String rate = String.valueOf(response.body().getVote_average());

                tvTitle.setText(response.body().getTitle());
//                fabFavorite.setOnClickListener(view -> {
//                    TheApi ardData = com.example.movies.apivia.Retrofit.getRetrofit().create(TheApi.class);
//                    Call<com.example.movies.myfavmodels.Root> retPro = ardData.createMovie(idd, theid, title, posterpath, RetriverMovie(), ratting, releasedate, sinopsis);
//
//                    retPro.enqueue(new Callback<com.example.movies.myfavmodels.Root>() {
//                        @Override
//                        public void onResponse(Call<com.example.movies.myfavmodels.Root> call1, Response<com.example.movies.myfavmodels.Root> response1) {
//                            if (cbFavorit.isChecked()) {
//                                if (response1.body().getPesan().equals("ID sudah ada")) {
//                                    Toast.makeText(DetailDownloadMovie.this, "This Movie is already on your download list, let's watch it!", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
//                                    boolean isDarkTheme = currentNightMode == Configuration.UI_MODE_NIGHT_YES;
//
//                                    AlertDialog.Builder dialog = new AlertDialog.Builder(DetailDownloadMovie.this);
//                                    View abView = getLayoutInflater().inflate(R.layout.alert_box_addmovie, null);
//                                    dialog.setView(abView);
//
//                                    AlertDialog theAlertDialog = dialog.create();
//
//                                    if (isDarkTheme) {
//                                        theAlertDialog.getWindow().getDecorView().setBackgroundResource(R.drawable.outlined_alertboxdark);
//                                    } else {
//                                        theAlertDialog.getWindow().getDecorView().setBackgroundResource(R.drawable.outlined_alertbox);
//                                    }
//
//                                    theAlertDialog.setCancelable(false);
//
//                                    abView.findViewById(R.id.btn_alertbox_confirm).setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            theAlertDialog.dismiss();
//                                            finish();
//                                        }
//                                    });
//                                    theAlertDialog.show();
//                                }
//                            }
//                            else {
//                                TheApi ardDatas = com.example.movies.apivia.Retrofit.getRetrofit().create(TheApi.class);
//                                Call<com.example.movies.myfavmodels.Root> retPros = ardDatas.deleteFavorite(idd, theid);
//                                retPros.enqueue(new Callback<com.example.movies.myfavmodels.Root>() {
//                                    @Override
//                                    public void onResponse(Call<com.example.movies.myfavmodels.Root> call, Response<com.example.movies.myfavmodels.Root> response) {
//                                        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
//                                        boolean isDarkTheme = currentNightMode == Configuration.UI_MODE_NIGHT_YES;
//
//                                        AlertDialog.Builder dialog = new AlertDialog.Builder(DetailDownloadMovie.this);
//                                        View abView = getLayoutInflater().inflate(R.layout.alert_box_removemovie, null);
//                                        dialog.setView(abView);
//
//                                        AlertDialog theAlertDialog = dialog.create();
//
//                                        if (isDarkTheme) {
//                                            theAlertDialog.getWindow().getDecorView().setBackgroundResource(R.drawable.outlined_alertboxdark);
//                                        } else {
//                                            theAlertDialog.getWindow().getDecorView().setBackgroundResource(R.drawable.outlined_alertbox);
//                                        }
//
//                                        theAlertDialog.setCancelable(false);
//
//                                        abView.findViewById(R.id.btn_alertbox_confirm).setOnClickListener(new View.OnClickListener() {
//                                            @Override
//                                            public void onClick(View v) {
//                                                theAlertDialog.dismiss();
//                                                finish();
//                                            }
//                                        });
//                                        theAlertDialog.show();
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<com.example.movies.myfavmodels.Root> call, Throwable t) {
//                                        MyToast("Something went wrong while unfavorite the movie");
//                                        finish();
//                                    }
//                                });
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<com.example.movies.myfavmodels.Root> call1, Throwable t) {
//                            Toast.makeText(DetailDownloadMovie.this, "Failed to connect to the server : "
//                                    + t.getMessage(), Toast.LENGTH_SHORT).show();
//                            finish();
//                        }
//                    });
//                });
//                fabPlay.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        startActivity(new Intent(DetailDownloadMovie.this, TrailerActivity.class));
//                    }
//                });
//                ivDownload.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        TheApi ardData = com.example.movies.apivia.Retrofit.getRetrofit().create(TheApi.class);
//                        Call<com.example.movies.myfavmodels.Root> retPro = ardData.deleteDownload(idd, theid);
//
//                        retPro.enqueue(new Callback<com.example.movies.myfavmodels.Root>() {
//                            @Override
//                            public void onResponse(Call<com.example.movies.myfavmodels.Root> call1, Response<com.example.movies.myfavmodels.Root> response1) {
//                                int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
//                                boolean isDarkTheme = currentNightMode == Configuration.UI_MODE_NIGHT_YES;
//
//                                AlertDialog.Builder dialog = new AlertDialog.Builder(DetailDownloadMovie.this);
//                                View abView = getLayoutInflater().inflate(R.layout.alert_box_removemovie, null);
//                                dialog.setView(abView);
//
//                                AlertDialog theAlertDialog = dialog.create();
//
//                                if (isDarkTheme) {
//                                    theAlertDialog.getWindow().getDecorView().setBackgroundResource(R.drawable.outlined_alertboxdark);
//                                } else {
//                                    theAlertDialog.getWindow().getDecorView().setBackgroundResource(R.drawable.outlined_alertbox);
//                                }
//
//                                theAlertDialog.setCancelable(false);
//
//                                abView.findViewById(R.id.btn_alertbox_confirm).setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        theAlertDialog.dismiss();
//                                        finish();
//                                    }
//                                });
//                                theAlertDialog.show();
//                            }
//
//                            @Override
//                            public void onFailure(Call<com.example.movies.myfavmodels.Root> call1, Throwable t) {
//                                Toast.makeText(DetailDownloadMovie.this, "Failed to connect to the server : "
//                                        + t.getMessage(), Toast.LENGTH_SHORT).show();
//                                finish();
//                            }
//                        });
//                    }
//                });
            }

            @Override
            public void onFailure(Call<com.example.movies.detailmodel.Root> call, Throwable t) {

            }
        });
    }

    private void RetriverMovie(int theid) {
        pbMovie.setVisibility(View.VISIBLE);

        TheApi ardData = Retrofit.getRetrofit().create(TheApi.class);
        Call<Root> retriverProcess = ardData.getDownload(theid, "A-Z", "Year", "Genre");

        retriverProcess.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.body().getPesan().equals("Data Kosong")) {
                    rvTrailerList.setVisibility(View.GONE);
                    pbMovie.setVisibility(View.INVISIBLE);
                }
                if (response.isSuccessful()) {
                    listFavorit = response.body().getData();
                    if (listFavorit != null) {
                        rvTrailerList.setVisibility(View.VISIBLE);
                        trailerAdapter = new TrailerAdapter(listFavorit);
                        rvTrailerList.setAdapter(trailerAdapter);
                        pbMovie.setVisibility(View.INVISIBLE);

                        Intent intent = getIntent();
                        String trailerid = intent.getStringExtra("varTrailerId");

                        if (trailerid == null) {
                            ypvVideo.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                                @Override
                                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                                    youTubePlayer.cueVideo("kpGo2_d3oYE", 0);
                                }
                            });
                        } else {
                            ypvVideo.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                                @Override
                                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                                    youTubePlayer.cueVideo(trailerid, 0);
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(TrailerActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                pbMovie.setVisibility(View.INVISIBLE);
            }
        });
    }
}