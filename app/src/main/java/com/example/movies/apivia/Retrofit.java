package com.example.movies.apivia;

import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {
    private static final String BASE_URL = "https://themovieapi.000webhostapp.com/";
    private static retrofit2.Retrofit retrofit;

    public static retrofit2.Retrofit getRetrofit() {
        if(retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
