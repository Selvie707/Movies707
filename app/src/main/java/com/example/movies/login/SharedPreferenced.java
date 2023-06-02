package com.example.movies.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferenced {
    private SharedPreferences sp;
    private SharedPreferences.Editor spe;

    public void setPref (Context ctx, String key, String value) {
        sp = PreferenceManager.getDefaultSharedPreferences(ctx);
        spe = sp.edit();
        spe.putString(key, value);
        spe.commit();
    }

    public String getPref (Context ctx, String key) {
        sp = PreferenceManager.getDefaultSharedPreferences(ctx);
        String tampung = sp.getString(key, null);
        return tampung;
    }

    public Boolean isLogin(Context ctx, String key) {
        sp = PreferenceManager.getDefaultSharedPreferences(ctx);
        String tampung = sp.getString(key, null);

        if (tampung != null) {
            return true;
        }
        else {
            return false;
        }
    }
}
