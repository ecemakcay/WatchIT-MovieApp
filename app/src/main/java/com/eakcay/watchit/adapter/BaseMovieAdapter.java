package com.eakcay.watchit.adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.eakcay.watchit.R;
import com.eakcay.watchit.model.MovieModel;

import java.util.List;


public abstract class BaseMovieAdapter<T extends MovieModel, VH
        extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {
    protected List<T> itemList;
    protected final Context context;

    public BaseMovieAdapter(Context context, List<T> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public void setItemList(List<T> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}


