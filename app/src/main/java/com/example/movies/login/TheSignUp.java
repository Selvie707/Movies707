package com.example.movies.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movies.R;
import com.example.movies.accountmodels.Root;
import com.example.movies.activities.ProfileActivity;
import com.example.movies.apivia.Retrofit;
import com.example.movies.apivia.TheApi;
import com.example.movies.data.MyDBHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TheSignUp extends AppCompatActivity {
    private View vwBarMin, vwBarMid, vwBarMax;
    private LinearLayout llPassAll, llPassBox;
    private CheckBox cbTerm;

    private Button btn_signup;
    private EditText etName, etUsername, etEmail, etPassword, etConfirmPassword;
    private TextView tvLogin, tvAgree, tvPassWords, tvUsernameAlreadyInUse;

    private String name, username, email, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_sign_up);

        btn_signup = findViewById(R.id.btn_signup_signup);
        etName = findViewById(R.id.et_signup_firstname);
        etUsername = findViewById(R.id.et_signup_username);
        etEmail = findViewById(R.id.et_signup_email);
        etPassword = findViewById(R.id.et_signup_password);
        etConfirmPassword = findViewById(R.id.et_signup_confirmpassword);
        tvLogin = findViewById(R.id.tv_signup_login);
        cbTerm = findViewById(R.id.cb_signup_agree);
        tvAgree = findViewById(R.id.tv_signup_rememberme);
        llPassAll = findViewById(R.id.ll_signup_passwordall);
        llPassBox = findViewById(R.id.ll_signup_passwordbar);
        tvPassWords = findViewById(R.id.tv_signup_passwordwords);
        vwBarMin = findViewById(R.id.vw_signup_barmin);
        vwBarMid = findViewById(R.id.vw_signup_barmid);
        vwBarMax = findViewById(R.id.vw_signup_barmax);
        tvUsernameAlreadyInUse = findViewById(R.id.tv_signup_usernamealreadyinuse);

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String passworda = etPassword.getText().toString().trim(); // Retrieve the text from EditText
                llPassAll.setVisibility(View.VISIBLE);
                llPassBox.setVisibility(View.VISIBLE);
                tvPassWords.setVisibility(View.VISIBLE);
                vwBarMin.setVisibility(View.VISIBLE);


                String a = String.valueOf(vwBarMax.getVisibility());
                String b = String.valueOf(vwBarMid.getVisibility());
                String c = String.valueOf(vwBarMin.getVisibility());




                if (passworda.length() < 5) {
                    tvPassWords.setText("Your password is too weak!");
                    vwBarMid.setVisibility(View.GONE);
                    vwBarMax.setVisibility(View.GONE);

                    btn_signup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(TheSignUp.this, "Your password is too weak!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (passworda.length() >= 5 && passworda.length() <= 7) {
                    tvPassWords.setText("Just a little bit more!");
                    vwBarMid.setVisibility(View.VISIBLE);
                    vwBarMax.setVisibility(View.GONE);

                    btn_signup.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(TheSignUp.this, "Your password is too weak!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (passworda.length() > 7) {
                    tvPassWords.setText("It's perfect!");
                    vwBarMax.setVisibility(View.VISIBLE);

                    btn_signup.setOnClickListener(view -> {
                        name = etName.getText().toString();
                        username = etUsername.getText().toString();
                        email = etEmail.getText().toString();
                        password = etPassword.getText().toString();
                        confirmPassword = etConfirmPassword.getText().toString();

                        if(name.trim().equalsIgnoreCase("")) {
                            etName.setError("Input First Name");
                        }
                        if(username.trim().equalsIgnoreCase("")) {
                            etUsername.setError("Input Username");
                        }
                        if(email.trim().equalsIgnoreCase("")) {
                            etEmail.setError("Input Email");
                        }
                        if(password.trim().equalsIgnoreCase("")) {
                            etPassword.setError("Input Password");
                        }
                        if(confirmPassword.trim().equalsIgnoreCase("")) {
                            etConfirmPassword.setError("Input Confirm Password");
                        }
                        if(!cbTerm.isChecked()){
                            Toast.makeText(TheSignUp.this, "You need to accept our privacy and policy", Toast.LENGTH_SHORT).show();
                        }
                        else if(password.trim().equalsIgnoreCase(confirmPassword)) {
                            ////                MyDBHelper mdb = new MyDBHelper(TheSignUp.this);
////                long eksekusi = mdb.TambahPlayer(name, username, email, password, username, null);
//
//                if (eksekusi == -1) {
//                    Toast.makeText(TheSignUp.this, "Gagal Menambah Akun", Toast.LENGTH_SHORT).show();
//                    etUsername.requestFocus();
//                }
//                else {
//                    Toast.makeText(TheSignUp.this, "Sukses Menambah Akun", Toast.LENGTH_SHORT).show();
//                    finish();
//                }

                            if (!email.trim().contains("@")) {
                                etEmail.setError("Input the correct email address");
                            } else {
                                TheApi ardData = Retrofit.getRetrofit().create(TheApi.class);
                                Call<Root> call = ardData.createAccount(name, username, email, password, "");
                                call.enqueue(new Callback<Root>() {
                                    @Override
                                    public void onResponse(Call<Root> call, Response<Root> response) {
                                        if (response.isSuccessful()) {
                                            if (response.body().getPesan().equals("Username sudah ada")) {
                                                // Get the current layout parameters of the view
                                                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) etUsername.getLayoutParams();

// Set the new margins (in pixels)
                                                int leftMargin = 0; // Change this value to your desired left margin
                                                int topMargin = 2; // Change this value to your desired top margin
                                                int rightMargin = 0; // Change this value to your desired right margin
                                                int bottomMargin = 0; // Change this value to your desired bottom margin

                                                params.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);

// Apply the updated layout parameters to the view
                                                etUsername.setLayoutParams(params);
                                                tvUsernameAlreadyInUse.setVisibility(View.VISIBLE);
                                            } else {
                                                int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                                                boolean isDarkTheme = currentNightMode == Configuration.UI_MODE_NIGHT_YES;

                                                AlertDialog.Builder dialog = new AlertDialog.Builder(TheSignUp.this);
                                                View abView = getLayoutInflater().inflate(R.layout.alert_box_signup, null);
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

                                                        Intent intent = new Intent(TheSignUp.this, TheLogin.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                                theAlertDialog.show();
                                            }
                                        } else {
                                            Toast.makeText(TheSignUp.this, "Failed", Toast.LENGTH_SHORT).show();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<Root> call, Throwable t) {
                                        Toast.makeText(TheSignUp.this, "There is something wrong while registering your account", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                        else {
                            etConfirmPassword.setError("There is inconsistency in the password you inputted");
                        }
                    });
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TheSignUp.this, TheLogin.class);
                startActivity(intent);
                finish();
            }
        });
    }
}