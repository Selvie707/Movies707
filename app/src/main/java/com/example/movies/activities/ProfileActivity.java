package com.example.movies.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movies.R;
import com.example.movies.accountmodels.Root;
import com.example.movies.apivia.Retrofit;
import com.example.movies.apivia.TheApi;
import com.example.movies.detail.AboutUs;
import com.example.movies.detail.EditProfile;
import com.example.movies.detail.HelpCenter;
import com.example.movies.detail.RateThisApp;
import com.example.movies.login.MainMovie;
import com.example.movies.login.TheLogin;
import com.example.movies.login.TheSignUp;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvProfileName, tvProfileBio;
    private ImageView ivProfilePic;
    private int theid;
    private static String username, bio, profilepic;

    private BottomNavigationView bnvBottomMenu;
    private RecyclerView rvRecentlyWatched;

    private EditText etEditProfile, etRate, etHelpCenter, etAboutUs, etLogOut, etDeleteAcc;

    private SharedPreferences sp, spA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sp = getApplicationContext().getSharedPreferences("theacc", MODE_PRIVATE);
        spA = getApplicationContext().getSharedPreferences("theaccadditional", MODE_PRIVATE);
        theid = Integer.parseInt(sp.getString("id", ""));
        username = sp.getString("username", "");
        bio = sp.getString("bio", "");
        profilepic = sp.getString("profilepic", "");

        Log.d("theprofile", profilepic);

        tvProfileName = findViewById(R.id.tv_profil_username);
        tvProfileBio = findViewById(R.id.tv_profile_description);
        ivProfilePic = findViewById(R.id.civ_profile);

        if (profilepic == null || profilepic.isEmpty()) {
            ivProfilePic.setImageResource(R.drawable.via);
        } else {
            ivProfilePic.setImageURI(Uri.parse(profilepic));
        }

        ivProfilePic.setImageURI(Uri.parse(profilepic));

        tvProfileName.setText(username);
        tvProfileBio.setText(bio);

        //

        bnvBottomMenu = findViewById(R.id.bn_menu_bottom);
        bnvBottomMenu.setSelectedItemId(R.id.menu_profile);

        ProfileDetailListener();

        BottomMenu();
    }

    // Bottom Menu
    private void BottomMenu () {
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
                        startActivity(new Intent(getApplicationContext(), FavoriteActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.menu_profile:
                        return true;
                }
                return false;
            }
        });
    }

    // Profile Detail Intent
    private void ProfileDetailListener () {
        etEditProfile = findViewById(R.id.et_edit_profile);
        etRate = findViewById(R.id.et_rate);
        etHelpCenter = findViewById(R.id.et_help_center);
        etAboutUs = findViewById(R.id.et_about_us);
        etLogOut = findViewById(R.id.et_log_out);
        etDeleteAcc = findViewById(R.id.et_profile_deleteacc);

        etEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, EditProfile.class);
                intent.putExtra("varUsername", username);
                intent.putExtra("varBio", bio);
                startActivity(intent);
            }
        });

        etRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, RateThisApp.class);
                startActivity(intent);
            }
        });

        etHelpCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, HelpCenter.class);
                startActivity(intent);
            }
        });

        etAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, AboutUs.class);
                startActivity(intent);
            }
        });

        etLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                boolean isDarkTheme = currentNightMode == Configuration.UI_MODE_NIGHT_YES;

                AlertDialog.Builder dialog = new AlertDialog.Builder(ProfileActivity.this);
                View abView = getLayoutInflater().inflate(R.layout.alert_box, null);
                dialog.setView(abView);

                AlertDialog theAlertDialog = dialog.create();

                if (isDarkTheme) {
                    theAlertDialog.getWindow().getDecorView().setBackgroundResource(R.drawable.outlined_alertboxdark);
                } else {
                    theAlertDialog.getWindow().getDecorView().setBackgroundResource(R.drawable.outlined_alertbox);
                }

                theAlertDialog.setCancelable(false);

                abView.findViewById(R.id.btn_alertbox_no).setOnClickListener(v -> {
                    theAlertDialog.dismiss();
                });

                abView.findViewById(R.id.btn_alertbox_yes).setOnClickListener(v -> {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.clear().apply();
                    SharedPreferences.Editor editorA = spA.edit();
                    editorA.clear().apply();

                    Intent intent = new Intent(ProfileActivity.this, MainMovie.class);
                    startActivity(intent);
                });
                theAlertDialog.show();
            }
        });

        etDeleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                boolean isDarkTheme = currentNightMode == Configuration.UI_MODE_NIGHT_YES;

                AlertDialog.Builder dialog = new AlertDialog.Builder(ProfileActivity.this);
                View abView = getLayoutInflater().inflate(R.layout.alert_box_deleteacc, null);
                dialog.setView(abView);

                AlertDialog theAlertDialog = dialog.create();

                if (isDarkTheme) {
                    theAlertDialog.getWindow().getDecorView().setBackgroundResource(R.drawable.outlined_alertboxdark);
                } else {
                    theAlertDialog.getWindow().getDecorView().setBackgroundResource(R.drawable.outlined_alertbox);
                }

                theAlertDialog.setCancelable(false);

                abView.findViewById(R.id.btn_alertbox_no).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        theAlertDialog.dismiss();
                    }
                });

                abView.findViewById(R.id.btn_alertbox_yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.clear().apply();
                        SharedPreferences.Editor editorA = spA.edit();
                        editorA.clear().apply();

                        TheApi ardData = Retrofit.getRetrofit().create(TheApi.class);
                        Call<Root> call = ardData.deleteAcc(theid);
                        call.enqueue(new Callback<Root>() {
                            @Override
                            public void onResponse(Call<Root> call, Response<Root> response) {
                                int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                                boolean isDarkTheme = currentNightMode == Configuration.UI_MODE_NIGHT_YES;

                                AlertDialog.Builder dialog = new AlertDialog.Builder(ProfileActivity.this);
                                View abView = getLayoutInflater().inflate(R.layout.alert_box_deleteaccount, null);
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

                                        Intent intent = new Intent(ProfileActivity.this, MainMovie.class);
                                        startActivity(intent);
                                    }
                                });
                                theAlertDialog.show();
                            }

                            @Override
                            public void onFailure(Call<Root> call, Throwable t) {
                                Toast.makeText(ProfileActivity.this, "There is something wrong while deleting your account", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                });
                theAlertDialog.show();
            }
        });
    }
}