package com.eakcay.watchit.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.eakcay.watchit.adapter.MovieAdapter;
import com.eakcay.watchit.service.MovieResponse;
import com.eakcay.watchit.R;
import com.eakcay.watchit.model.MovieModel;
import com.eakcay.watchit.service.MovieAPI;
import com.eakcay.watchit.service.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {

    private RecyclerView popularRV, topRatedRV, nowPlayingRV, upComingRV;
    private MovieAdapter popularAdapter, topRatedAdapter, nowPlayingAdapter, upComingAdapter;
    private List<MovieModel> popularList, topRatedList, nowPlayingList, upComingList;
    private static final String API_KEY = "9fc75e7de261e9b79cf9aea98daf509f";
    private MovieAPI movieAPI;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // RetrofitClient to create an instance of the MovieAPI interface
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        movieAPI = retrofit.create(MovieAPI.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        popularRV = view.findViewById(R.id.popularRV);
        topRatedRV = view.findViewById(R.id.topRatedRV);
        nowPlayingRV = view.findViewById(R.id.nowPlayingRV);
        upComingRV = view.findViewById(R.id.upComingRV);

        // Set layout manager and adapter for each RecyclerView
        popularList = new ArrayList<>();
        popularAdapter = new MovieAdapter(getContext(), popularList);
        popularRV.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        popularRV.setAdapter(popularAdapter);

        topRatedList = new ArrayList<>();
        topRatedAdapter = new MovieAdapter(getContext(), topRatedList);
        topRatedRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        topRatedRV.setAdapter(topRatedAdapter);

        nowPlayingList = new ArrayList<>();
        nowPlayingAdapter = new MovieAdapter(getContext(), nowPlayingList);
        nowPlayingRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        nowPlayingRV.setAdapter(nowPlayingAdapter);

        upComingList = new ArrayList<>();
        upComingAdapter = new MovieAdapter(getContext(), upComingList);
        upComingRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        upComingRV.setAdapter(upComingAdapter);

        // Call methods to fetch data from API
        getPopularMovies();
        getTopRatedMovies();
        getNowPlayingMovies();
        getUpcomingMovies();

        return view;
    }

    // Method to fetch popular movies from API
    private void getPopularMovies() {
        Call<MovieResponse> call = movieAPI.getPopularMovies(API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    popularList.addAll(response.body().getResults());
                    popularAdapter.notifyDataSetChanged();
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

    // Method to fetch top rated movies from API
    private void getTopRatedMovies() {
        Call<MovieResponse> call = movieAPI.getTopRatedMovies(API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    topRatedList.addAll(response.body().getResults());
                    topRatedAdapter.notifyDataSetChanged();
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

    // Method to fetch now playing movies from API

    private void getNowPlayingMovies() {
        Call<MovieResponse> call = movieAPI.getNowPlayingMovies(API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    nowPlayingList.addAll(response.body().getResults());
                    nowPlayingAdapter.notifyDataSetChanged();
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

    private void getUpcomingMovies() {
        Call<MovieResponse> call = movieAPI.getUpcomingMovies(API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    upComingList.addAll(response.body().getResults());
                    upComingAdapter.notifyDataSetChanged();
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