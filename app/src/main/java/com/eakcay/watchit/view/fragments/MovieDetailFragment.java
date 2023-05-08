package com.eakcay.watchit.view.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eakcay.watchit.R;
import com.eakcay.watchit.adapter.CastAdapter;
import com.eakcay.watchit.model.CastModel;
import com.eakcay.watchit.model.MovieModel;
import com.eakcay.watchit.model.VideoModel;
import com.eakcay.watchit.service.CreditsResponse;
import com.eakcay.watchit.service.MovieAPI;
import com.eakcay.watchit.service.RetrofitClient;
import com.eakcay.watchit.service.VideoResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailFragment extends Fragment {
    private static final String API_KEY = "9fc75e7de261e9b79cf9aea98daf509f";
    private ImageView movieDetailImg, movieCoverImg;
    private TextView tv_title, tv_description, runTime, rating, releaseDate;
    private RecyclerView castRV;
    private List<CastModel> castList;
    private CastAdapter castAdapter;
    private FloatingActionButton play_fab;
    private MovieAPI movieAPI;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        // get reference to bottom navigation bar
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNav);
        // hide bottom navigation bar
        bottomNavigationView.setVisibility(View.GONE);

        //cast recyclerView
        castRV= view.findViewById(R.id.castRV);
        castRV.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        castList = new ArrayList<>();
        castAdapter = new CastAdapter(getContext(), castList);
        castRV.setAdapter(castAdapter);

        movieDetailImg = view.findViewById(R.id.detail_movie_img);
        movieCoverImg = view.findViewById(R.id.detail_movie_cover);
        tv_title = view.findViewById(R.id.detail_movie_title);
        tv_description = view.findViewById(R.id.detail_movie_desc);
        play_fab = view.findViewById(R.id.fab);
        rating = view.findViewById(R.id.rating);
        runTime = view.findViewById(R.id.runTime);
        releaseDate = view.findViewById(R.id.release_date);

        movieAPI = RetrofitClient.getRetrofitInstance().create(MovieAPI.class);

        Bundle bundle = getArguments();
        if (bundle != null) {
            int movieId = bundle.getInt("movie");

            Call<MovieModel> call = movieAPI.getMovieDetails(movieId, API_KEY);
            call.enqueue(new Callback<MovieModel>() {
                @Override
                public void onResponse(@NonNull Call<MovieModel> call, @NonNull Response<MovieModel> response) {
                    MovieModel movie = response.body();
                    if (movie != null) {
                        tv_title.setText(movie.getTitle());
                        tv_description.setText(movie.getOverview());
                        rating.setText("Rating: " + movie.getVoteAverage());
                        releaseDate.setText("Release Date: " + movie.getReleaseDate());

                        if (movie.getRuntime() != 0) {
                            runTime.setText("Run time: " + movie.getRuntime() + " min");
                        } else {
                            runTime.setText("Run time: N/A");
                        }

                        String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
                        String coverUrl = "https://image.tmdb.org/t/p/w500" + movie.getBackdropPath();

                        Picasso.get().load(imageUrl).into(movieDetailImg);
                        Picasso.get().load(coverUrl).into(movieCoverImg);

                        play_fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //movie trailer
                                getVideo();
                            }
                        });

                        //cast
                        getCast();

                    }
                }

                @Override
                public void onFailure(Call<MovieModel> call, Throwable t) {
                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        return view;
    }

    public void getVideo() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            int movieId = bundle.getInt("movie");

            Call<VideoResponse> call = movieAPI.getVideos(movieId, API_KEY);
            call.enqueue(new Callback<VideoResponse>() {
                @Override
                public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                    if (response.body() != null) {
                        List<VideoModel> videoModels = response.body().getVideoList();
                        if (videoModels.size() > 0) {
                            String videoKey = videoModels.get(0).getKey();
                            String videoUrl = "https://www.youtube.com/watch?v=" + videoKey;

                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
                            startActivity(intent);
                        } else {
                            Toast.makeText(getContext(), "No video available", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<VideoResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public void getCast() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            int movieId = bundle.getInt("movie");

            Call<CreditsResponse> castCall = movieAPI.getCredits(movieId, API_KEY);
            castCall.enqueue(new Callback<CreditsResponse>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(@NonNull Call<CreditsResponse> call, @NonNull Response<CreditsResponse> response) {
                    if (response.body() != null) {
                        CreditsResponse creditsResponse = response.body();
                        List<CastModel> castModels = creditsResponse.getCastList();
                        castList.addAll(castModels);
                        castAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<CreditsResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    @Override
    public void onDestroyView() {
        // get reference to bottom navigation bar
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNav);
        // show bottom navigation bar
        bottomNavigationView.setVisibility(View.VISIBLE);

        super.onDestroyView();
    }
}