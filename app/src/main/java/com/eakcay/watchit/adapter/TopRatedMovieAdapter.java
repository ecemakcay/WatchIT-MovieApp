package com.eakcay.watchit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.recyclerview.widget.RecyclerView;

import com.eakcay.watchit.R;
import com.eakcay.watchit.model.MovieModel;

import java.util.List;

import io.reactivex.annotations.NonNull;

/*  This class manages the data to be displayed in the RecyclerView.
    Retrieves a list of MovieModel instances and
    uses that data to display each element in the RecyclerView.
    */
public class TopRatedMovieAdapter extends RecyclerView.Adapter<TopRatedMovieAdapter.TopRatedMovieViewHolder> {

    private List<MovieModel> topRatedMovieList;
    private final Context context;


    public TopRatedMovieAdapter(Context context, List<MovieModel> topRatedMovieList) {
        this.context = context;
        this.topRatedMovieList = topRatedMovieList;
    }
    public void setTopRatedMovieList(List<MovieModel> topRatedMovieList) {
        this.topRatedMovieList = topRatedMovieList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public TopRatedMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item_layout, parent, false);
        return new TopRatedMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopRatedMovieViewHolder holder, int position) {
        MovieModel movie = topRatedMovieList.get(position);
        holder.titleTextView.setText(movie.getTitle());

        String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
        Picasso.get().load(imageUrl).into(holder.posterImageView);


    }

    @Override
    public int getItemCount() {
        return topRatedMovieList.size();
    }

    public static class TopRatedMovieViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImageView;
        TextView titleTextView;

        public TopRatedMovieViewHolder(@NonNull View itemView) {
            super(itemView);

            posterImageView = itemView.findViewById(R.id.poster_image_view);
            titleTextView = itemView.findViewById(R.id.title_text_view);
        }
    }
}

