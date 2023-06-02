package com.example.movies.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movies.R;

public class MainMovie extends AppCompatActivity {
    ImageView ivMain, ivmMainPic, ivSharpCorner;
    private Button btnSignUp, btnLogin;

    private String ambilSesi;
    SharedPreferenced cSp = new SharedPreferenced();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_movie);

        ivMain = findViewById(R.id.iv_mainmovie_picture);
        ivSharpCorner = findViewById(R.id.siv_mainmovie_sharpshapecorner);

        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        boolean isDarkTheme = currentNightMode == Configuration.UI_MODE_NIGHT_YES;

        Drawable drawable = getResources().getDrawable(R.drawable.aigirlgorgeous, getTheme());
        Drawable drawables = getResources().getDrawable(R.drawable.aigirlpretty, getTheme());

        if (isDarkTheme) {
            ivMain.setImageDrawable(drawable);
            ivSharpCorner.setImageResource(R.drawable.theedgesdark);
        } else {
            ivMain.setImageDrawable(drawables);
            ivSharpCorner.setImageResource(R.drawable.theedges);
        }

        btnSignUp = findViewById(R.id.btn_main_movie_sign_up);
        btnLogin = findViewById(R.id.btn_main_movie_login);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMovie.this, TheSignUp.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMovie.this, TheLogin.class);
                startActivity(intent);
            }
        });
    }
}