package com.eakcay.watchit.model;

public class FavoriMovieModel {
    private MovieModel movie;


    public FavoriMovieModel() {
    }

    public FavoriMovieModel(MovieModel movie) {
        this.movie = movie;
    }

    public MovieModel getMovie() {
        return movie;
    }

    public void setMovie(MovieModel movie) {
        this.movie = movie;
    }


}
