package com.eakcay.watchit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.eakcay.watchit.R;
import com.eakcay.watchit.model.CastModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.annotations.NonNull;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {
    private Context context;
    private List<CastModel> castList;

    public CastAdapter(Context context, List<CastModel> castList) {
        this.context = context;
        this.castList = castList;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cast_item_layout, parent, false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        CastModel cast = castList.get(position);
        holder.nameTextView.setText(cast.getName());
        holder.characterTextView.setText(cast.getCharacter());

        String imageUrl = "https://image.tmdb.org/t/p/w500" + cast.getProfilePath();
        Picasso.get().load(imageUrl).into(holder.profileImageView);
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    public static class CastViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImageView;
        TextView nameTextView;
        TextView characterTextView;

        public CastViewHolder(View itemView) {
            super(itemView);
            profileImageView = itemView.findViewById(R.id.person_image_view);
            nameTextView = itemView.findViewById(R.id.name);
            characterTextView = itemView.findViewById(R.id.character);
        }
    }
}
