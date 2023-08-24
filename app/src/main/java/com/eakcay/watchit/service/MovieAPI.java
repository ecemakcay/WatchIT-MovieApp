package com.eakcay.watchit.service;

import com.eakcay.watchit.model.MovieModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieAPI {
    String API_KEY = "9fc75e7de261e9b79cf9aea98daf509f";
    @GET("movie/popular?api_key="+API_KEY)
    Call<MovieResponse> getPopularMovies();

    @GET("movie/upcoming?api_key="+API_KEY)
    Call<MovieResponse> getUpcomingMovies();

    @GET("movie/top_rated?api_key="+API_KEY)
    Call<MovieResponse> getTopRatedMovies();

    @GET("movie/now_playing?api_key="+API_KEY)
    Call<MovieResponse> getNowPlayingMovies();

    @GET("search/movie?api_key="+API_KEY)
    Call<MovieResponse> searchMovies( @Query("query") String query);

   @GET("movie/{movie_id}?api_key="+API_KEY)
   Call<MovieModel> getMovieDetails(@Path("movie_id") int movieId);

    @GET("movie/{movie_id}/credits?api_key="+API_KEY)
    Call<CreditsResponse> getCredits(@Path("movie_id") int movieId);

    @GET("movie/{movie_id}/videos?api_key="+API_KEY)
    Call<VideoResponse> getVideos(@Path("movie_id") int movieId);

    @GET("discover/movie?api_key="+API_KEY)
    Call<MovieResponse> getMoviesByGenre(@Query("with_genres") int genreId);
}
