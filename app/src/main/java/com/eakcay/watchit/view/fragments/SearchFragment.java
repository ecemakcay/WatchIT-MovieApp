package com.eakcay.watchit.view.fragments;

import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.eakcay.watchit.R;
import com.eakcay.watchit.adapter.MovieAdapter;
import com.eakcay.watchit.model.MovieModel;
import com.eakcay.watchit.service.MovieAPI;
import com.eakcay.watchit.service.MovieResponse;
import com.eakcay.watchit.service.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchFragment extends Fragment {

    private RecyclerView searchRv;
    private SearchView searchView;
    private MovieAPI movieAPI;
    private static final String API_KEY = "9fc75e7de261e9b79cf9aea98daf509f";
    private List<MovieModel> movieList;
    private List<MovieModel> filteredList;
    private MovieAdapter movieAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // RetrofitClient to create an instance of the MovieAPI interface
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        movieAPI = retrofit.create(MovieAPI.class);

        movieList = new ArrayList<>();
        filteredList = new ArrayList<>();
        movieAdapter = new MovieAdapter(getContext(), filteredList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchRv = view.findViewById(R.id.searchRv);
        searchView = view.findViewById(R.id.searchView);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Fetch movies from API with search query
                getMovies(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                // Filter movies based on search query
                filteredList.clear();
                for (MovieModel movie : movieList) {
                    if (movie.getTitle().toLowerCase().contains(text.toLowerCase())) {
                        filteredList.add(movie);
                    }
                }
                movieAdapter.notifyDataSetChanged();
                return true;
            }
        });

        // Set layout manager and adapter for RecyclerView
        searchRv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        searchRv.setAdapter(movieAdapter);



        return view;
    }


    // Method to fetch movies from API

    private void getMovies(String text) {

        Call<MovieResponse> call = movieAPI.searchMovies(API_KEY, text);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    // Clear movieList and add the new search results to it
                    movieList.clear();
                    movieList.addAll(response.body().getResults());

                    // Clear filteredList and add the new search results to it
                    filteredList.clear();
                    filteredList.addAll(response.body().getResults());

                    // Notify the adapter that the data has changed
                    movieAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}