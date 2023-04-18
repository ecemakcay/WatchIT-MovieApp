package com.eakcay.watchit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.eakcay.watchit.R;
import com.eakcay.watchit.model.MovieModel;

import java.util.List;

import io.reactivex.annotations.NonNull;

/*  This class manages the data to be displayed in the RecyclerView.
    Retrieves a list of MovieModel instances and
    uses that data to display each element in the RecyclerView.
    */
public class PopularMovieAdapter extends RecyclerView.Adapter<PopularMovieAdapter.PopularMovieViewHolder> {

    private List<MovieModel> popularMovieList;
    private final Context context;


    public PopularMovieAdapter(Context context, List<MovieModel> movieList) {
        this.context = context;
        this.popularMovieList = movieList;
    }
    public void setPopularMovieList(List<MovieModel> popularMovieList) {
        this.popularMovieList = popularMovieList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public PopularMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item_layout, parent, false);
        return new PopularMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularMovieViewHolder holder, int position) {
        MovieModel movie = popularMovieList.get(position);
        holder.titleTextView.setText(movie.getTitle());
        String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
        Picasso.get().load(imageUrl).into(holder.posterImageView);

    }

    @Override
    public int getItemCount() {
        return popularMovieList.size();
    }

    public static class PopularMovieViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImageView;
        TextView titleTextView;

        public PopularMovieViewHolder(@NonNull View itemView) {
            super(itemView);

            posterImageView = itemView.findViewById(R.id.poster_image_view);
            titleTextView = itemView.findViewById(R.id.title_text_view);
          }
    }
}
