package com.eakcay.watchit.service;



import com.eakcay.watchit.model.MovieModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieAPI {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingMovies(@Query("api_key") String apiKey);

    @GET("search/movie")
    Call<MovieResponse> searchMovies(@Query("api_key") String apiKey, @Query("query") String query);

   @GET("movie/{movie_id}")
   Call<MovieModel> getMovieDetails(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/credits")
    Call<CreditsResponse> getCredits(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/videos")
    Call<VideoResponse> getVideos(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

/*
    @GET("movie/{movie_id}/credits")
    Call<MovieCredits> getMovieCredits(@Path("movie_id") int movieId, @Query("api_key") String apiKey);

    @GET("person/{person_id}")
    Call<PersonModel> getPersonDetails(@Path("person_id") int personId, @Query("api_key") String apiKey);

    @GET("person/{person_id}/movie_credits")
    Call<PersonCredits> getPersonMovieCredits(@Path("person_id") int personId, @Query("api_key") String apiKey);

 */
}
