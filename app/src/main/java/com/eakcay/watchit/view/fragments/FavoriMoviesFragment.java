package com.eakcay.watchit.view.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.eakcay.watchit.R;
import com.eakcay.watchit.adapter.MovieAdapter;
import com.eakcay.watchit.auth.FirebaseAuthHelper;
import com.eakcay.watchit.data.FirestoreHelper;
import com.eakcay.watchit.model.MovieModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;


public class FavoriMoviesFragment extends Fragment {
    private MovieAdapter movieAdapter;
    private List<MovieModel> movieList;
    private AppCompatActivity activity;

    @SuppressLint({"NotifyDataSetChanged", "NonConstantResourceId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favori_movies, container, false);
        RecyclerView favoriRV = view.findViewById(R.id.favori_movie_RV);

        // Get reference to the BottomNavigationView
        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottomNav);

        // Set the listener for item clicks
        bottomNavigationView.setOnItemSelectedListener(item -> {

            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {

                case R.id.homeFragment:
                    HomeFragment homeFragment = new HomeFragment();
                    transaction.replace(R.id.nav_host_fragment, homeFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;

                case R.id.searchFragment:
                    SearchFragment searchFragment = new SearchFragment();
                    transaction.replace(R.id.nav_host_fragment, searchFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;

                case R.id.myListsFragment:
                    MyListsFragment myListsFragment = new MyListsFragment();
                    transaction.replace(R.id.nav_host_fragment, myListsFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;

                case R.id.profileFragment:
                    ProfileFragment profileFragment = new ProfileFragment();
                    transaction.replace(R.id.nav_host_fragment, profileFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;
            }

            return true;
        });


        movieList = new ArrayList<>();
        getFavoriteMovies();
        movieAdapter = new MovieAdapter(getActivity(), movieList);

        // Set layout manager and adapter for RecyclerView
        favoriRV.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        favoriRV.setAdapter(movieAdapter);

        return view;
    }

    public void getFavoriteMovies(){
        FirebaseAuthHelper firebaseAuthHelper = new FirebaseAuthHelper(getContext());
        String userId = firebaseAuthHelper.getUserId();
        FirestoreHelper firestoreHelper = new FirestoreHelper();

        firestoreHelper.getMovies(userId, "FavoriteMovies", new FirestoreHelper.GetMoviesListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onMoviesLoaded(List<MovieModel> favoriteMovies) {
                movieList.clear();
                movieList.addAll(favoriteMovies);
                movieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception e) {

            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AppCompatActivity) {
            activity = (AppCompatActivity) context;
        }
    }


}