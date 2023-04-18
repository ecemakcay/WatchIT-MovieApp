package com.eakcay.watchit.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.eakcay.watchit.adapter.NowPlayingMovieAdapter;
import com.eakcay.watchit.adapter.TopRatedMovieAdapter;
import com.eakcay.watchit.adapter.UpComingMovieAdapter;
import com.eakcay.watchit.service.MovieResponse;
import com.eakcay.watchit.R;
import com.eakcay.watchit.adapter.PopularMovieAdapter;
import com.eakcay.watchit.model.MovieModel;
import com.eakcay.watchit.service.MovieAPI;
import com.eakcay.watchit.service.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private static final String API_KEY = "9fc75e7de261e9b79cf9aea98daf509f";

    private RecyclerView popularRV, topRatedRV, nowPlayingRV, upComingRV;
    private PopularMovieAdapter popularMovieAdapter;
    private TopRatedMovieAdapter topRatedMovieAdapter;
    private NowPlayingMovieAdapter nowPlayingMovieAdapter;
    private UpComingMovieAdapter upComingMovieAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //popular movie RecyclerView
        popularRV = view.findViewById(R.id.popularRV);
        popularRV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        popularMovieAdapter = new PopularMovieAdapter(getActivity(), new ArrayList<MovieModel>());
        popularRV.setAdapter(popularMovieAdapter);


        //top rated movie RecyclerView
        topRatedRV = view.findViewById(R.id.topRatedRV);
        topRatedRV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        topRatedMovieAdapter = new TopRatedMovieAdapter(getActivity(), new ArrayList<MovieModel>());
        topRatedRV.setAdapter(topRatedMovieAdapter);



        //latest movie RecyclerView
        nowPlayingRV = view.findViewById(R.id.nowPlayingRV);
        nowPlayingRV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        nowPlayingMovieAdapter = new NowPlayingMovieAdapter(getActivity(), new ArrayList<MovieModel>());
        nowPlayingRV.setAdapter(nowPlayingMovieAdapter);


        //up coming movie RecyclerView
        upComingRV = view.findViewById(R.id.upComingRV);
        upComingRV.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        upComingMovieAdapter = new UpComingMovieAdapter(getActivity(), new ArrayList<MovieModel>());
        upComingRV.setAdapter(upComingMovieAdapter);




        loadPopularMovie();
        loadTopRatedMovie();
        loadNowPlayingMovie();
        loadUpComingMovie();

        return view;
    }


    // Get data from TMDB API
    public void loadPopularMovie(){
        MovieAPI movieAPI = RetrofitClient.getRetrofitInstance().create(MovieAPI.class);
        Call<MovieResponse> call = movieAPI.getPopularMovies(API_KEY);

        // Handle the response from API
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    List<MovieModel> movieList = response.body().getMovieList();
                    popularMovieAdapter.setPopularMovieList(movieList); // Update adapter with movieList data
                } else {
                    Toast.makeText(getActivity(), "Response failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void loadNowPlayingMovie(){
        MovieAPI movieAPI = RetrofitClient.getRetrofitInstance().create(MovieAPI.class);
        Call<MovieResponse> call = movieAPI.getNowPlayingMovies(API_KEY);

        // Handle the response from API
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    List<MovieModel> movieList = response.body().getMovieList();
                    nowPlayingMovieAdapter.setNowPlayingMovieList(movieList); // Update adapter with movieList data
                } else {
                    Toast.makeText(getActivity(), "Response failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadTopRatedMovie(){
        MovieAPI movieAPI = RetrofitClient.getRetrofitInstance().create(MovieAPI.class);
        Call<MovieResponse> call = movieAPI.getTopRatedMovies(API_KEY);

        // Handle the response from API
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    List<MovieModel> movieList = response.body().getMovieList();
                    topRatedMovieAdapter.setTopRatedMovieList(movieList); // Update adapter with movieList data
                } else {
                    Toast.makeText(getActivity(), "Response failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void loadUpComingMovie(){
        MovieAPI movieAPI = RetrofitClient.getRetrofitInstance().create(MovieAPI.class);
        Call<MovieResponse> call = movieAPI.getUpcomingMovies(API_KEY);

        // Handle the response from API
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    List<MovieModel> movieList = response.body().getMovieList();
                    upComingMovieAdapter.setUpcomingMovieList(movieList); // Update adapter with movieList data
                } else {
                    Toast.makeText(getActivity(), "Response failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
