package com.eakcay.watchit.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//Used to make requests to the TMDB API.
public interface MovieAPI {
    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(
            @Query("api_key") String apiKey
    );

    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingMovies(
            @Query("api_key") String apiKey
    );

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(
            @Query("api_key") String apiKey
    );
    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingMovies(
            @Query("api_key") String apiKey
    );
}


