package com.eakcay.watchit.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
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
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchFragment extends Fragment {

    private MovieAPI movieAPI;
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
        movieAdapter = new MovieAdapter(getActivity(), filteredList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        RecyclerView searchRv = view.findViewById(R.id.searchRv);
        SearchView searchView = view.findViewById(R.id.searchView);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Fetch movies from API with search query
                getMovies(query);
                return true;
            }

            @SuppressLint("NotifyDataSetChanged")
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

        Call<MovieResponse> call = movieAPI.searchMovies(text);
        call.enqueue(new Callback<MovieResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    // Clear movieList and add the new search results to it
                    movieList.clear();
                    movieList.addAll(Objects.requireNonNull(response.body()).getResults());

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
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}