package com.example.movies.detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movies.R;
import com.example.movies.accountmodels.Root;
import com.example.movies.activities.ProfileActivity;
import com.example.movies.apivia.Retrofit;
import com.example.movies.apivia.TheApi;
import com.example.movies.login.TheLogin;
import com.example.movies.login.TheSignUp;
import com.github.dhaval2404.imagepicker.ImagePicker;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity {

    private ImageView ivEdit, ivProfilPicture, ivEditProfilePicture;
    private EditText etUsername, etEmail, etBio;
    private Button btnSimpan;
    private int id;
    private String username, email, bio, Nusername, Nemail, Nbio, imagePath, theProfilePic, Nimagepath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ivProfilPicture.setImageURI(data.getData());
        Log.d("imageURI", String.valueOf(data.getData()));

        imagePath = data.getData().toString();

        SharedPreferences sp = getApplicationContext().getSharedPreferences("theacc", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("profilepic", imagePath);
        editor.apply();

        Log.d("insideeditprofile", imagePath);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ivEditProfilePicture = findViewById(R.id.iv_editprofile_changeprofilepicture);
        ivEditProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(EditProfile.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        SharedPreferences sp = getApplicationContext().getSharedPreferences("theacc", MODE_PRIVATE);
        id = Integer.parseInt(sp.getString("id", ""));
        username = sp.getString("username", "");
        email = sp.getString("email", "");
        bio = sp.getString("bio", "");
        theProfilePic = sp.getString("profilepic", "");

        ivEdit = findViewById(R.id.iv_editprofile_changeprofilepicture);
        ivProfilPicture = findViewById(R.id.civ_editprofile_profilepicture);
        etUsername = findViewById(R.id.et_editprofile_username);
        etEmail = findViewById(R.id.et_editprofile_email);
        etBio = findViewById(R.id.et_editprofile_bio);
        btnSimpan = findViewById(R.id.btn_editprofile_simpan);

        if (theProfilePic == null || theProfilePic.isEmpty()) {
            ivProfilPicture.setImageResource(R.drawable.via);
        } else {
            ivProfilPicture.setImageURI(Uri.parse(theProfilePic));
        }

        etUsername.setText(username);
        etEmail.setText(email);
        etBio.setText(bio);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Nusername = etUsername.getText().toString();
                Nemail = etEmail.getText().toString();
                Nbio = etBio.getText().toString();

                if(username.trim().equalsIgnoreCase("")) {
                    etUsername.setError("Masukkan Username");
                }
                if(email.trim().equalsIgnoreCase("")) {
                    etEmail.setError("Masukkan Email");
                }
                if(bio.trim().equalsIgnoreCase("")) {
                    etBio.setError("Masukkan Bio");
                }
                else {
                    SharedPreferences sp = getApplicationContext().getSharedPreferences("theacc", MODE_PRIVATE);
                    theProfilePic = sp.getString("profilepic", "");

                    TheApi ardData = Retrofit.getRetrofit().create(TheApi.class);
                    Call<Root> call = ardData.updateAcc(id, Nusername, Nemail, Nbio, theProfilePic);
                    Log.d("editprofile", id + Nusername + Nemail + Nbio + theProfilePic);
                    call.enqueue(new Callback<Root>() {
                        @Override
                        public void onResponse(Call<Root> call, Response<Root> response) {
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("username", Nusername);
                            editor.putString("email", Nemail);
                            editor.putString("bio", Nbio);
                            editor.putString("profilepic", theProfilePic);
                            editor.apply();

                            int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                            boolean isDarkTheme = currentNightMode == Configuration.UI_MODE_NIGHT_YES;

                            AlertDialog.Builder dialog = new AlertDialog.Builder(EditProfile.this);
                            View abView = getLayoutInflater().inflate(R.layout.alert_box_editprofile, null);
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

                                    Intent intentProfile = new Intent(EditProfile.this, ProfileActivity.class);
                                    startActivity(intentProfile);
                                    finish();
                                }
                            });
                            theAlertDialog.show();
                        }

                        @Override
                        public void onFailure(Call<Root> call, Throwable t) {
                            Toast.makeText(EditProfile.this, "Something went wrong, please contact us so we can fix it ASAP", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}