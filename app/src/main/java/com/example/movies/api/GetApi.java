package com.example.movies.api;

import com.example.movies.models.Root;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetApi {
    @GET("movie/popular")
    Call<Root> getMovie(@Query("api_key") String token);

    @GET("trending/all/day")
    Call<Root> getTrending(@Query("api_key") String token);

    @GET("genre/movie/list")
    Call<Root> getGenre(@Query("api_key") String token);

    @GET("movie/{movie_id}")
    Call<com.example.movies.detailmodel.Root> getDetail(@Path("movie_id") int id, @Query("api_key") String token);
}
