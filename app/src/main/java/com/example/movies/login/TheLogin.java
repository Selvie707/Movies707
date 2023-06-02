package com.example.movies.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movies.R;
import com.example.movies.accountmodels.Datum;
import com.example.movies.accountmodels.Root;
import com.example.movies.activities.MainActivity;
import com.example.movies.apivia.Retrofit;
import com.example.movies.apivia.TheApi;
import com.example.movies.data.MyDBHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TheLogin extends AppCompatActivity {
    ConstraintLayout clAccessDenied;
    private CheckBox cbLogin;
    private MyDBHelper mdb;
    private Button btnLogin;
    private TextView tvSignUp;
    private EditText etUsername, etPassword;

    private String username, password, dbUsername, dbPassword;

    private ArrayList<String> alId, alFName, alUsername, alEmail, alPassword, alPName, alBio;

    private ArrayList<Datum> alAccount = new ArrayList<>();
    private SharedPreferences sp, spA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_login);

        mdb = new MyDBHelper(TheLogin.this);

        alFName = new ArrayList<>();
        alUsername = new ArrayList<>();
        alEmail = new ArrayList<>();
        alPassword = new ArrayList<>();
        alPName = new ArrayList<>();
        alBio = new ArrayList<>();

//        SQLiteToArrayList();

//        for (int i = 0; i < 3; i++) {
//            String usernamee = alFName.get(i);
//            Toast.makeText(TheLogin.this, usernamee, Toast.LENGTH_SHORT).show();
//        }

        clAccessDenied = findViewById(R.id.cl_login_accessdenied);
        btnLogin = findViewById(R.id.btn_login_login);
        tvSignUp = findViewById(R.id.tv_login_signup);
        etUsername = findViewById(R.id.et_login_username);
        etPassword = findViewById(R.id.et_login_password);
        cbLogin = findViewById(R.id.cb_signup_agree);

        sp = getSharedPreferences("theacc", MODE_PRIVATE);
        spA = getSharedPreferences("theaccadditional", MODE_PRIVATE);

        if (spA.getString("usernameA", null) != null && spA.getString("passwordA", null) != null) {
            etUsername.setText(spA.getString("usernameA", null));
            etPassword.setText(spA.getString("passwordA", null));
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                if(username.trim().isEmpty()) {
                    etUsername.setError("Masukkan Username");
                }
                if(password.trim().isEmpty()) {
                    etPassword.setError("Masukkan Password");
                }
                else {
                    performLogin(username, password);
                }
            }
        });

        username = etUsername.getText().toString();

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TheLogin.this, TheSignUp.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

//    private void SQLiteToArrayList () {
//        Cursor cursor = mdb.BacaDataPlayer();
//        if (cursor.getCount() == 0) {
//            Toast.makeText(this, "Tidak ada data", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            while (cursor.moveToNext()) {
//                alFName.add(cursor.getString(1));
//                alUsername.add(cursor.getString(2));
//                alEmail.add(cursor.getString(3));
//                alPassword.add(cursor.getString(4));
//                alPName.add(cursor.getString(5));
//                alBio.add(cursor.getString(6));
//            }
//        }
//    }

    private void performLogin(String username, String password) {
        TheApi ardData = Retrofit.getRetrofit().create(TheApi.class);
        Call<Root> call = ardData.loginAccount(username, password);
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getPesan().equals("Data Tersedia")) {
                        if (cbLogin.isChecked()) {
                            @SuppressLint("CommitPrefEdits")
                            SharedPreferences.Editor editorA = spA.edit();
                            editorA.putString("usernameA", etUsername.getText().toString());
                            editorA.putString("passwordA", etPassword.getText().toString());
                            editorA.apply();
                        }
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("username", etUsername.getText().toString());
                        editor.putString("password", etPassword.getText().toString());

                        alAccount = response.body().getData();
                        for (Datum account : alAccount) {
                            editor.putString("id", account.getId());
                            editor.putString("bio", account.getBio());
                            editor.putString("email", account.getEmail());
                        }

                        editor.apply();

                        Intent intent = new Intent(TheLogin.this, MainActivity.class);
//                        intent.putExtra("xUsername", username);
                        startActivity(intent);
                    } else {
                        Toast.makeText(TheLogin.this, "Hmm something went wrong", Toast.LENGTH_SHORT).show();
                        clAccessDenied.setVisibility(View.VISIBLE);
                    }
                } else {
                    Toast.makeText(TheLogin.this, "Failed to login", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(TheLogin.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}