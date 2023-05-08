package com.eakcay.watchit.service;

import com.eakcay.watchit.model.MovieModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


//This class satisfies the response from the API.
// It contains a list, and that list contains instances of the MovieModel class.
public class MovieResponse {

    @SerializedName("results")
    @Expose
    private List<MovieModel> results;

    public List<MovieModel> getResults() {
        return results;
    }

    public void setResults(List<MovieModel> results) {
        this.results = results;
    }

}
