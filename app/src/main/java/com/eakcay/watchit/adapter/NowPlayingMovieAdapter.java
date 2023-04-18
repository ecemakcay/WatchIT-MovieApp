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
public class NowPlayingMovieAdapter extends RecyclerView.Adapter<NowPlayingMovieAdapter.NowPlayingMovieViewHolder> {

    private List<MovieModel> nowPlayingMovieList;
    private final Context context;


    public NowPlayingMovieAdapter(Context context, List<MovieModel> movieList) {
        this.context = context;
        this.nowPlayingMovieList = movieList;
    }
    public void setNowPlayingMovieList(List<MovieModel> nowPlayingMovieList) {
        this.nowPlayingMovieList = nowPlayingMovieList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public NowPlayingMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item_layout, parent, false);
        return new NowPlayingMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NowPlayingMovieViewHolder holder, int position) {
        MovieModel movie = nowPlayingMovieList.get(position);
        holder.titleTextView.setText(movie.getTitle());
        String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
        Picasso.get().load(imageUrl).into(holder.posterImageView);

    }

    @Override
    public int getItemCount() {
        return nowPlayingMovieList.size();
    }

    public static class NowPlayingMovieViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImageView;
        TextView titleTextView;

        public NowPlayingMovieViewHolder(@NonNull View itemView) {
            super(itemView);

            posterImageView = itemView.findViewById(R.id.poster_image_view);
            titleTextView = itemView.findViewById(R.id.title_text_view);
        }
    }
}
