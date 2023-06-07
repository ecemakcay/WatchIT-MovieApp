package com.eakcay.watchit.view.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
import java.util.ArrayList;
import java.util.List;

public class MyListsFragment extends Fragment {
    private MovieAdapter movieAdapter;
    private List<MovieModel> movieList;
    private AppCompatActivity activity;

    @SuppressLint({"NotifyDataSetChanged", "NonConstantResourceId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_lists, container, false);
        RecyclerView userListRV = view.findViewById(R.id.user_listRV);

        movieList = new ArrayList<>();
        getUserMovies();
        movieAdapter = new MovieAdapter(getActivity(), movieList);

        // Set layout manager and adapter for RecyclerView
        userListRV.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        userListRV.setAdapter(movieAdapter);

        return view;
    }

    public void getUserMovies(){
        FirebaseAuthHelper firebaseAuthHelper = new FirebaseAuthHelper(getContext());
        String userId = firebaseAuthHelper.getUserId();
        FirestoreHelper firestoreHelper = new FirestoreHelper();

        firestoreHelper.getMovies(userId, "UserList", new FirestoreHelper.GetMoviesListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onMoviesLoaded(List<MovieModel> userMovies) {
                movieList.clear();
                movieList.addAll(userMovies);
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
