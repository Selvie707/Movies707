package com.example.movies.apivia;

import com.example.movies.accountmodels.Datum;
import com.example.movies.myfavmodels.Root;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TheApi {
    @GET("movie-show-acc.php/{username}")
    Call<Datum> getAccount(@Path("username") String username);
    @FormUrlEncoded
    @POST("movie-show-favorite.php/{theid}")
    Call<com.example.movies.myfavmodels.Root> getTheMovie(@Path("theid") int theid,
                                                          @Field("sortBy") String sortBy,
                                                          @Field("selectedYear") String selectedYear,
                                                          @Field("selectedGenre") String selectedGenre);

    @FormUrlEncoded
    @POST("movie-show-download.php/{theid}")
    Call<com.example.movies.myfavmodels.Root> getDownload(@Path("theid") int theid,
                                                          @Field("sortBy") String sortBy,
                                                          @Field("selectedYear") String selectedYear,
                                                          @Field("selectedGenre") String selectedGenre);

    @GET("movie-login-acc.php/{username}/{password}")
    Call<com.example.movies.accountmodels.Root> loginAccount(@Path("username") String username, @Path("password") String password);

    @FormUrlEncoded
    @POST("movie-add-acc.php")
    Call<com.example.movies.accountmodels.Root> createAccount(@Field("nama") String nama,
                                                              @Field("username") String username,
                                                              @Field("email") String email,
                                                              @Field("password") String password,
                                                              @Field("bio") String bio);

    @FormUrlEncoded
    @POST("movie-add-favorite.php")
    Call<Root> createMovie(@Field("id") String id,
                           @Field("theid") int theid,
                           @Field("title") String title,
                           @Field("posterpath") String posterpath,
                           @Field("genre") String genre,
                           @Field("ratting") Double ratting,
                           @Field("releasedate") String releasedate,
                           @Field("sinopsis") String sinopsis);

    @FormUrlEncoded
    @POST("movie-add-download.php")
    Call<Root> createDownload(@Field("id") String id,
                              @Field("theid") int theid,
                              @Field("title") String title,
                              @Field("posterpath") String posterpath,
                              @Field("genre") String genre,
                              @Field("ratting") Double ratting,
                              @Field("releasedate") String releasedate,
                              @Field("sinopsis") String sinopsis);

    @FormUrlEncoded
    @POST("movie-delete-favorite.php")
    Call<Root> deleteFavorite(@Field("id") String id,
                              @Field("theid") int theid);

    @FormUrlEncoded
    @POST("movie-delete-download.php")
    Call<Root> deleteDownload(@Field("id") String id,
                              @Field("theid") int theid);

//    @FormUrlEncoded
//    @POST("movie-delete-acc.php/{id}")
//    Call<com.example.movies.accountmodels.Root> deleteAcc(@Field("id") int id);

    @FormUrlEncoded
    @POST("movie-delete-acc.php")
    Call<com.example.movies.accountmodels.Root> deleteAcc(@Field("id") int id);

    @FormUrlEncoded
    @POST("movie-update-acc.php/{id}")
    Call<com.example.movies.accountmodels.Root> updateAcc(@Path("id") int id,
                                                          @Field("username") String username,
                                                          @Field("email") String email,
                                                          @Field("bio") String bio,
                                                          @Field("photoprofile") String photoprofile);
}