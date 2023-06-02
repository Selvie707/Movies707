package com.example.movies.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {
    private Context ctx;
    private static final String DATABASE_NAME = "dbMovieAcc";
    private static final String TABLE_NAME = "tbAccount";
    private static final String FIELD_ID = "id";
    private static final String FIELD_NAMA_DEPAN = "namaDepan";
    private static final String FIELD_USERNAME = "username";
    private static final String FIELD_EMAIL = "email";
    private static final String FIELD_PASSWORD = "password";
    private static final String FIELD_PROFILE_NAME = "profileName";
    private static final String FIELD_BIO = "bio";
    private static final int DATABASE_VERSION = 1;

    public MyDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FIELD_NAMA_DEPAN + " VARCHAR (22)," + FIELD_USERNAME + " VARCHAR(22)," + FIELD_EMAIL +
                " VARCHAR(22)," + FIELD_PASSWORD + " VARCHAR(22)," + FIELD_PROFILE_NAME +
                " VARCHAR(22)," + FIELD_BIO + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbAccount");
        onCreate(db);
    }

    public long TambahPlayer (String namaDepan, String username, String email, String password, String profileName, String bio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(FIELD_NAMA_DEPAN, namaDepan);
        cv.put(FIELD_USERNAME, username);
        cv.put(FIELD_EMAIL, email);
        cv.put(FIELD_PASSWORD, password);
        cv.put(FIELD_PROFILE_NAME, profileName);
        cv.put(FIELD_BIO, bio);

        long eksekusi = db.insert(TABLE_NAME, null, cv);
        return eksekusi;
    }

    public Cursor BacaDataPlayer () {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public long HapusPlayer (String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        long eksekusi = db.delete(TABLE_NAME, "id = ?", new String[]{id});
        return eksekusi;
    }

    public long UbahPlayer (String id, String namaDepan, String namaBelakang, String username, String email, String password, String profileName, String bio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(FIELD_NAMA_DEPAN, namaDepan);
        cv.put(FIELD_USERNAME, username);
        cv.put(FIELD_EMAIL, email);
        cv.put(FIELD_PASSWORD, password);
        cv.put(FIELD_PROFILE_NAME, profileName);
        cv.put(FIELD_BIO, bio);

        long eksekusi = db.update(TABLE_NAME, cv, "id = ?", new String[]{id});

        return eksekusi;
    }
}
