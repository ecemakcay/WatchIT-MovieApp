package com.eakcay.watchit.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
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
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment {

    private MovieAdapter popularAdapter, topRatedAdapter, nowPlayingAdapter, upComingAdapter;
    private List<MovieModel> popularList, topRatedList, nowPlayingList, upComingList;
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

        RecyclerView popularRV = view.findViewById(R.id.popularRV);
        RecyclerView topRatedRV = view.findViewById(R.id.topRatedRV);
        RecyclerView nowPlayingRV = view.findViewById(R.id.nowPlayingRV);
        RecyclerView upComingRV = view.findViewById(R.id.upComingRV);

        // Set layout manager and adapter for each RecyclerView
        popularList = new ArrayList<>();
        popularAdapter = new MovieAdapter(getContext(), popularList);
        popularRV.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        popularRV.setAdapter(popularAdapter);

        topRatedList = new ArrayList<>();
        topRatedAdapter = new MovieAdapter(getContext(), topRatedList);
        topRatedRV.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        topRatedRV.setAdapter(topRatedAdapter);

        nowPlayingList = new ArrayList<>();
        nowPlayingAdapter = new MovieAdapter(getContext(), nowPlayingList);
        nowPlayingRV.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        nowPlayingRV.setAdapter(nowPlayingAdapter);

        upComingList = new ArrayList<>();
        upComingAdapter = new MovieAdapter(getContext(), upComingList);
        upComingRV.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
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
        Call<MovieResponse> call = movieAPI.getPopularMovies();

        call.enqueue(new Callback<MovieResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    popularList.addAll(Objects.requireNonNull(response.body()).getResults());
                    popularAdapter.notifyDataSetChanged();
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

    // Method to fetch top rated movies from API
    private void getTopRatedMovies() {
        Call<MovieResponse> call = movieAPI.getTopRatedMovies();

        call.enqueue(new Callback<MovieResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    topRatedList.addAll(Objects.requireNonNull(response.body()).getResults());
                    topRatedAdapter.notifyDataSetChanged();
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

    // Method to fetch now playing movies from API

    private void getNowPlayingMovies() {
        Call<MovieResponse> call = movieAPI.getNowPlayingMovies();

        call.enqueue(new Callback<MovieResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    nowPlayingList.addAll(Objects.requireNonNull(response.body()).getResults());
                    nowPlayingAdapter.notifyDataSetChanged();
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

    private void getUpcomingMovies() {
        Call<MovieResponse> call = movieAPI.getUpcomingMovies();

        call.enqueue(new Callback<MovieResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    upComingList.addAll(Objects.requireNonNull(response.body()).getResults());
                    upComingAdapter.notifyDataSetChanged();
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