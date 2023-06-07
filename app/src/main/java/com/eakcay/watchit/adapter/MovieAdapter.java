package com.eakcay.watchit.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.eakcay.watchit.R;
import com.eakcay.watchit.model.MovieModel;
import com.eakcay.watchit.view.fragments.MovieDetailFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends BaseMovieAdapter<MovieModel, MovieAdapter.MovieViewHolder> {

    private AppCompatActivity activity;

    public MovieAdapter(Context context, List<MovieModel> movieList) {
        super(context, movieList);
        if (context instanceof AppCompatActivity) {
            activity = (AppCompatActivity) context;
        }
        this.context = context;
    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.movie_item_layout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        MovieModel movie = itemList.get(position);
        holder.titleTextView.setText(movie.getTitle());
        String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
        Picasso.get().load(imageUrl).into(holder.posterImageView);


    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImageView;
        TextView titleTextView;
        Context context;

        public MovieViewHolder(View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.person_image_view);
            titleTextView = itemView.findViewById(R.id.name);
            context = itemView.getContext();

            itemView.setOnClickListener(view -> {
                if (activity == null) {
                    Toast.makeText(context, "Activity is null", Toast.LENGTH_SHORT).show();
                    return;
                }

                MovieModel movie = itemList.get(getAbsoluteAdapterPosition());
                Bundle bundle = new Bundle();
                bundle.putInt("movie", movie.getId());
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
                movieDetailFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_container, movieDetailFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            });

        }

    }

}

