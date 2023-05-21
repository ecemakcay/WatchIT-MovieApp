package com.eakcay.watchit.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eakcay.watchit.R;
import com.eakcay.watchit.model.Genre;

import java.util.ArrayList;
import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {
    private final Context context;
    private final List<Genre> genreList;

    public GenreAdapter(Context context) {
        this.context = context;
        this.genreList = new ArrayList<>();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setGenreList(List<Genre> genreList) {
        this.genreList.clear();
        this.genreList.addAll(genreList);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.genre_item_layout, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        Genre genre = genreList.get(position);
        holder.genreTextView.setText(genre.getName());
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    public static class GenreViewHolder extends RecyclerView.ViewHolder {
        private final TextView genreTextView;

        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            genreTextView = itemView.findViewById(R.id.genreTextView);
        }
    }
}
