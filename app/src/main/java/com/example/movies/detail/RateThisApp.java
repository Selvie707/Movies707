package com.example.movies.detail;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movies.R;
import com.example.movies.activities.ProfileActivity;

public class RateThisApp extends AppCompatActivity {

    private CheckBox cbRateOne, cbRateTwo, cbRateThree, cbRateFour, cbRateFive;
    private Button btnKirim;
    boolean nightMode;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_this_app);

        cbRateOne = findViewById(R.id.cb_rate_one);
        cbRateTwo = findViewById(R.id.cb_rate_two);
        cbRateThree = findViewById(R.id.cb_rate_three);
        cbRateFour = findViewById(R.id.cb_rate_four);
        cbRateFive = findViewById(R.id.cb_rate_five);

        btnKirim = findViewById(R.id.btn_rate_send);

        GetRateClick();
        ButtonKirim();
    }

    // Get Rate Click
    private void GetRateClick () {
        cbRateOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cbRateOne.setButtonDrawable(R.drawable.ic_rate_orange);
                cbRateTwo.setButtonDrawable(R.drawable.ic_rate_black);
                cbRateThree.setButtonDrawable(R.drawable.ic_rate_black);
                cbRateFour.setButtonDrawable(R.drawable.ic_rate_black);
                cbRateFive.setButtonDrawable(R.drawable.ic_rate_black);
            }
        });

        cbRateTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cbRateOne.setButtonDrawable(R.drawable.ic_rate_orange);
                cbRateTwo.setButtonDrawable(R.drawable.ic_rate_orange);
                cbRateThree.setButtonDrawable(R.drawable.ic_rate_black);
                cbRateFour.setButtonDrawable(R.drawable.ic_rate_black);
                cbRateFive.setButtonDrawable(R.drawable.ic_rate_black);
            }
        });

        cbRateThree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cbRateOne.setButtonDrawable(R.drawable.ic_rate_orange);
                cbRateTwo.setButtonDrawable(R.drawable.ic_rate_orange);
                cbRateThree.setButtonDrawable(R.drawable.ic_rate_orange);
                cbRateFour.setButtonDrawable(R.drawable.ic_rate_black);
                cbRateFive.setButtonDrawable(R.drawable.ic_rate_black);
            }
        });

        cbRateFour.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cbRateOne.setButtonDrawable(R.drawable.ic_rate_orange);
                cbRateTwo.setButtonDrawable(R.drawable.ic_rate_orange);
                cbRateThree.setButtonDrawable(R.drawable.ic_rate_orange);
                cbRateFour.setButtonDrawable(R.drawable.ic_rate_orange);
                cbRateFive.setButtonDrawable(R.drawable.ic_rate_black);
            }
        });

        cbRateFive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cbRateOne.setButtonDrawable(R.drawable.ic_rate_orange);
                cbRateTwo.setButtonDrawable(R.drawable.ic_rate_orange);
                cbRateThree.setButtonDrawable(R.drawable.ic_rate_orange);
                cbRateFour.setButtonDrawable(R.drawable.ic_rate_orange);
                cbRateFive.setButtonDrawable(R.drawable.ic_rate_orange);
            }
        });
    }

    // Send Button Clicked
    private void ButtonKirim () {
        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RateThisApp.this, "Thank you for the rate!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RateThisApp.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}