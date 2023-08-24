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
import com.eakcay.watchit.auth.FirebaseAuthHelper;
import com.eakcay.watchit.data.FirestoreHelper;
import com.eakcay.watchit.model.Genre;
import com.eakcay.watchit.service.MovieAPI;
import com.eakcay.watchit.service.MovieResponse;
import com.eakcay.watchit.R;
import com.eakcay.watchit.model.MovieModel;
import com.eakcay.watchit.service.RetrofitClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment implements FirestoreHelper.GetFavoriteGenresCountListener {

    private MovieAdapter popularAdapter, topRatedAdapter, nowPlayingAdapter, upComingAdapter, recommendationAdapter;
    private List<MovieModel> popularList, topRatedList, nowPlayingList, upComingList, recommendationList;
    private MovieAPI movieAPI;
    private FirestoreHelper firestoreHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // RetrofitClient to create an instance of the MovieAPI interface
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        movieAPI = retrofit.create(MovieAPI.class);

        // Initialize FirestoreHelper
        firestoreHelper = new FirestoreHelper();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView popularRV = view.findViewById(R.id.popularRV);
        RecyclerView topRatedRV = view.findViewById(R.id.topRatedRV);
        RecyclerView nowPlayingRV = view.findViewById(R.id.nowPlayingRV);
        RecyclerView upComingRV = view.findViewById(R.id.upComingRV);
        RecyclerView recommendationRV = view.findViewById(R.id.recommendationRV);

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

        recommendationList = new ArrayList<>();
        recommendationAdapter = new MovieAdapter(getContext(), recommendationList);
        recommendationRV.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        recommendationRV.setAdapter(recommendationAdapter);

        // Call methods to fetch data from API
        getPopularMovies();
        getTopRatedMovies();
        getNowPlayingMovies();
        getUpcomingMovies();

        // Fetch user's favorite genre counts from Firestore
        FirebaseAuthHelper firebaseAuthHelper = new FirebaseAuthHelper(getContext());
        String userId = firebaseAuthHelper.getUserId();
        firestoreHelper.getFavoriteGenresCount(userId, this);

        return view;
    }

    // Method to fetch popular movies from API
    private void getPopularMovies() {
        Call<MovieResponse> call = movieAPI.getPopularMovies();

        call.enqueue(new Callback<MovieResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call,
                                   @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    popularList.addAll(response.body().getResults());
                    popularAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to fetch data!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to fetch top rated movies from API
    private void getTopRatedMovies() {
        Call<MovieResponse> call = movieAPI.getTopRatedMovies();

        call.enqueue(new Callback<MovieResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call,
                                   @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    topRatedList.addAll(response.body().getResults());
                    topRatedAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to fetch data!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to fetch now playing movies from API
    private void getNowPlayingMovies() {
        Call<MovieResponse> call = movieAPI.getNowPlayingMovies();

        call.enqueue(new Callback<MovieResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call,
                                   @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    nowPlayingList.addAll(response.body().getResults());
                    nowPlayingAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to fetch data!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to fetch upcoming movies from API
    private void getUpcomingMovies() {
        Call<MovieResponse> call = movieAPI.getUpcomingMovies();
        call.enqueue(new Callback<MovieResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call,
                                   @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    upComingList.addAll(response.body().getResults());
                    upComingAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to fetch data!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call,
                                  @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onFavoriteGenresCounted(Map<Genre, Integer> genreCounts) {
        // Get the most frequent genre
        Genre mostFrequentGenre = getMostFrequentGenre(genreCounts);
        // Fetch movies of the most frequent genre from API
        getMoviesByGenreFromAPI(mostFrequentGenre);
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(getContext(), "Error counting favorite genres: " + e.getMessage(),
                Toast.LENGTH_SHORT).show();
    }
    private void getMoviesByGenreFromAPI(Genre genre) {
        int genreId = 0;
        if (genre != null) {
            genreId = genre.getId();
        } else {
            Toast.makeText(getContext(), "Genre is null", Toast.LENGTH_SHORT).show();
        }
        Call<MovieResponse> call = movieAPI.getMoviesByGenre(genreId);
        call.enqueue(new Callback<MovieResponse>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call,
                                   @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    recommendationList.addAll(response.body().getResults());
                    recommendationAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to fetch data!",
                            Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Genre getMostFrequentGenre(Map<Genre, Integer> genreCounts) {
        Genre mostFrequentGenre = null;
        int maxCount = 0;
        for (Map.Entry<Genre, Integer> entry : genreCounts.entrySet()) {
            Genre genre = entry.getKey();
            int count = entry.getValue();
            if (count > maxCount) {
                maxCount = count;
                mostFrequentGenre = genre;
            }
        }
        return mostFrequentGenre;
    }

}
