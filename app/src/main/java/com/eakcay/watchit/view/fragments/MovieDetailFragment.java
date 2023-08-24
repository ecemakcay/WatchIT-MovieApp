package com.eakcay.watchit.view.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.eakcay.watchit.R;
import com.eakcay.watchit.adapter.CastAdapter;
import com.eakcay.watchit.adapter.GenreAdapter;
import com.eakcay.watchit.data.FirestoreHelper;
import com.eakcay.watchit.model.CastModel;
import com.eakcay.watchit.model.MovieModel;
import com.eakcay.watchit.model.VideoModel;
import com.eakcay.watchit.service.CreditsResponse;
import com.eakcay.watchit.service.MovieAPI;
import com.eakcay.watchit.service.RetrofitClient;
import com.eakcay.watchit.service.VideoResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailFragment extends Fragment {
    private ImageView movieDetailImg, movieCoverImg;
    private TextView tv_title, tv_description, runTime, rating, releaseDate;
    private List<CastModel> castList;
    private CastAdapter castAdapter;
    private GenreAdapter genreAdapter;
    private FloatingActionButton play_fab;
    private MovieAPI movieAPI;
    private ImageButton btn_favori, btn_watched, btn_addList;
    private boolean isFavorite = false;
    private boolean isWatched = false;
    private boolean isInList = false;
    private final String favoriteMovies = "FavoriteMovies";
    private final String watchedMovies = "WatchedMovies";
    private final String userList = "UserList";
    private String userId;
    FirestoreHelper firestoreHelper = new FirestoreHelper();

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNav);
        bottomNavigationView.setVisibility(View.GONE);

        userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        // Cast recyclerView
        RecyclerView castRV = view.findViewById(R.id.castRV);
        castRV.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        castList = new ArrayList<>();
        castAdapter = new CastAdapter(getContext(), castList);
        castRV.setAdapter(castAdapter);

        // Genre recyclerView
        RecyclerView genreRV = view.findViewById(R.id.genreRV);
        genreRV.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        genreAdapter = new GenreAdapter(getContext());
        genreRV.setAdapter(genreAdapter);

        movieDetailImg = view.findViewById(R.id.detail_movie_img);
        movieCoverImg = view.findViewById(R.id.detail_movie_cover);
        tv_title = view.findViewById(R.id.detail_movie_title);
        tv_description = view.findViewById(R.id.detail_movie_desc);
        play_fab = view.findViewById(R.id.fab);
        rating = view.findViewById(R.id.rating);
        runTime = view.findViewById(R.id.runTime);
        releaseDate = view.findViewById(R.id.release_date);
        btn_favori = view.findViewById(R.id.btn_favorite);
        btn_watched = view.findViewById(R.id.btn_watched);
        btn_addList = view.findViewById(R.id.btn_add_list);

        movieAPI = RetrofitClient.getRetrofitInstance().create(MovieAPI.class);
        btn_addList.setBackgroundResource(R.drawable.add_white);
        btn_watched.setBackgroundResource(R.drawable.check_white);
        btn_favori.setBackgroundResource(R.drawable.favorite_default);

        Bundle bundle = getArguments();
        if (bundle != null) {
            int movieId = bundle.getInt("movie");

            Call<MovieModel> call = movieAPI.getMovieDetails(movieId);
            call.enqueue(new Callback<MovieModel>() {
                @Override
                public void onResponse(@NonNull Call<MovieModel> call, @NonNull Response<MovieModel> response) {
                    MovieModel movie = response.body();
                    if (movie != null) {
                        tv_title.setText(movie.getTitle());
                        tv_description.setText(movie.getOverview());
                        double voteAverage = Double.parseDouble(movie.getVoteAverage());
                        @SuppressLint("DefaultLocale") String formattedVoteAverage = String.format("%.1f", voteAverage);
                        rating.setText(formattedVoteAverage);
                        String release = movie.getReleaseDate();
                        if (release != null){
                            releaseDate.setText(release.substring(0, 4));
                        }else
                            releaseDate.setText("N/A");
                        if (movie.getRuntime() != 0) {
                            runTime.setText(movie.getRuntime() + " min");
                        } else {
                            runTime.setText("Run time:  N/A");
                        }
                        String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
                        String coverUrl = "https://image.tmdb.org/t/p/w500" + movie.getBackdropPath();
                        Picasso.get().load(imageUrl).into(movieDetailImg);
                        Picasso.get().load(coverUrl).into(movieCoverImg);
                        play_fab.setOnClickListener(view1 -> {
                            // Movie trailer
                            getVideo();
                        });
                        // Cast
                        getCast();
                        // Genre
                        genreAdapter.setGenreList(movie.getGenres());
                        checkFavoriteStatus(userId,movie);
                        checkWatchedStatus(userId,movie);
                        checkListStatus(userId,movie);
                        btn_favori.setOnClickListener(view12 -> {
                            if (!isFavorite) {
                                addMovieToFavorites(userId,movie);
                            } else {
                                removeMovieFromFavorites(userId,movie);
                            }
                        });
                        btn_watched.setOnClickListener(view12 -> {
                            if (!isWatched) {
                                addMovieToWatched(userId,movie);
                            } else {
                                removeMovieFromWatched(userId,movie);
                            }
                        });
                        btn_addList.setOnClickListener(view12 -> {
                            if (!isInList) {
                                addMovieToList(userId,movie);
                            } else {
                                removeMovieFromList(userId,movie);
                            }
                        });

                    }
                }

                @Override
                public void onFailure(@NonNull Call<MovieModel> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        return view;
    }

    private void addMovieToWatched(String userId, MovieModel movie) {
        firestoreHelper.addMovie(userId, movie,watchedMovies);
        // Update the watched status and button color after adding the movie
        isWatched = true;
        btn_watched.setBackgroundResource(R.drawable.check_yellow);
    }

    private void checkWatchedStatus(String userId, MovieModel movie) {
        firestoreHelper.checkMovie(userId,movie.getId(),watchedMovies,
                new FirestoreHelper.CheckMovieListener() {
                    @Override
                    public void onMovieFound() {
                        // movie added to Watched
                        isWatched = true;
                        Log.d("Watched true", String.valueOf(isWatched));
                        btn_watched.setBackgroundResource(R.drawable.check_yellow);
                    }
                    @Override
                    public void onMovieNotFound() {
                        isWatched = false;
                        Log.d("Watched false", String.valueOf(isWatched));
                        btn_watched.setBackgroundResource(R.drawable.check_white);
                    }
                });
    }

    private void removeMovieFromWatched(String userId, MovieModel movieModel) {
        firestoreHelper.removeMovie(userId,movieModel.getId(),watchedMovies);
        // Update the watched status and button color after removing the movie
        isWatched = false;
        btn_watched.setBackgroundResource(R.drawable.check_white);
    }

    private void addMovieToFavorites(String userId, MovieModel movie) {
        firestoreHelper.addMovie(userId, movie,favoriteMovies);
        // Update the watched status and button color after adding the movie
        isFavorite = true;
        btn_favori.setBackgroundResource(R.drawable.favorite_red);
    }

    private void checkFavoriteStatus(String userId, MovieModel movie) {
        FirestoreHelper firestoreHelper = new FirestoreHelper();
        firestoreHelper.checkMovie(userId,movie.getId(),favoriteMovies,
                new FirestoreHelper.CheckMovieListener() {
            @Override
            public void onMovieFound() {
                // movie added to favorities
                isFavorite = true;
                Log.d("Favori true", String.valueOf(isFavorite));
                btn_favori.setBackgroundResource(R.drawable.favorite_red);
            }
            @Override
            public void onMovieNotFound() {
                isFavorite = false;
                Log.d("Favori false", String.valueOf(isFavorite));
                btn_favori.setBackgroundResource(R.drawable.favorite_default);
            }
        });
    }

    private void removeMovieFromFavorites(String userId, MovieModel movieModel) {
        FirestoreHelper firestoreHelper = new FirestoreHelper();
        firestoreHelper.removeMovie(userId,movieModel.getId(),favoriteMovies);
        // Update the favorite status and button color after removing the movie
        isFavorite = false;
        btn_favori.setBackgroundResource(R.drawable.favorite_default);

    }

    private void addMovieToList(String userId, MovieModel movie) {
        firestoreHelper.addMovie(userId, movie,userList);
        // Update the userList status and button color after adding the movie
        isInList = true;
        btn_addList.setBackgroundResource(R.drawable.save_icon);
    }

    private void checkListStatus(String userId, MovieModel movie) {
        firestoreHelper.checkMovie(userId,movie.getId(),userList,
                new FirestoreHelper.CheckMovieListener() {
                    @Override
                    public void onMovieFound() {
                        // movie added to userList
                        isInList = true;
                        Log.d("User List true", String.valueOf(isInList));
                        btn_addList.setBackgroundResource(R.drawable.save_icon);
                    }
                    @Override
                    public void onMovieNotFound() {
                        isInList = false;
                        Log.d("User List false", String.valueOf(isInList));
                        btn_addList.setBackgroundResource(R.drawable.add_white);
                    }
                });
    }

    private void removeMovieFromList(String userId, MovieModel movieModel) {
        firestoreHelper.removeMovie(userId,movieModel.getId(),userList);
        // Update the userList status and button color after removing the movie
        isInList = false;
        btn_addList.setBackgroundResource(R.drawable.add_white);

    }

    public void getVideo() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            int movieId = bundle.getInt("movie");
            Call<VideoResponse> call = movieAPI.getVideos(movieId);
            call.enqueue(new Callback<VideoResponse>() {
                @Override
                public void onResponse(@NonNull Call<VideoResponse> call,
                                       @NonNull Response<VideoResponse> response) {
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
                public void onFailure(@NonNull Call<VideoResponse> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void getCast() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            int movieId = bundle.getInt("movie");
            Call<CreditsResponse> castCall = movieAPI.getCredits(movieId);
            castCall.enqueue(new Callback<CreditsResponse>() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onResponse(@NonNull Call<CreditsResponse> call,
                                       @NonNull Response<CreditsResponse> response) {
                    if (response.body() != null) {
                        CreditsResponse creditsResponse = response.body();
                        List<CastModel> castModels = creditsResponse.getCastList();
                        castList.addAll(castModels);
                        castAdapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(@NonNull Call<CreditsResponse> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Error: " + t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNav);
        bottomNavigationView.setVisibility(View.VISIBLE);
        super.onDestroyView();
    }
}

