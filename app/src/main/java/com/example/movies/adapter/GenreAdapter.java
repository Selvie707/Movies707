package com.example.movies.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.movies.R;
import com.example.movies.activities.MovieActivity;
import com.example.movies.models.Genre;
import com.example.movies.models.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {
    private ArrayList<Genre> daftarMovie;

    public GenreAdapter(ArrayList<Genre> daftarMovie) {
        this.daftarMovie = daftarMovie;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Genre rwm = daftarMovie.get(position);
        holder.tvGenre.setText(rwm.getName());

        int iconResId = getIconResourceId(rwm.getName());
        if (iconResId != 0) {
            holder.ivGenre.setImageResource(iconResId);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), MovieActivity.class);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    private int getIconResourceId(String genreName) {
        // Define a mapping between genre names and icon resource IDs
        Map<String, Integer> genreIconMap = new HashMap<>();
        genreIconMap.put("Action", R.drawable.ic_genre_action);
        genreIconMap.put("Adventure", R.drawable.ic_genre_adventure);
        genreIconMap.put("Animation", R.drawable.ic_genre_animation);
        genreIconMap.put("Comedy", R.drawable.ic_genre_comedy);
        genreIconMap.put("Crime", R.drawable.ic_genre_crime);
        genreIconMap.put("Documentary", R.drawable.ic_genre_documentary);
        genreIconMap.put("Drama", R.drawable.ic_genre_drama);
        genreIconMap.put("Family", R.drawable.ic_genre_family);
        genreIconMap.put("Fantasy", R.drawable.ic_genre_fantasy);
        genreIconMap.put("History", R.drawable.ic_genre_history);
        genreIconMap.put("Horror", R.drawable.ic_android);
        genreIconMap.put("Music", R.drawable.ic_android);
        genreIconMap.put("Mystery", R.drawable.ic_android);
        genreIconMap.put("Romance", R.drawable.ic_android);
        genreIconMap.put("Science Fiction", R.drawable.ic_android);
        genreIconMap.put("Tv Movie", R.drawable.ic_android);
        genreIconMap.put("Thriller", R.drawable.ic_android);
        genreIconMap.put("War", R.drawable.ic_android);
        genreIconMap.put("Western", R.drawable.ic_android);

        // Add more genre-icon mappings as needed

        // Look up the icon resource ID for the given genre name
        Integer iconResId = genreIconMap.get(genreName);
        if (iconResId != null) {
            return iconResId;
        } else {
            // If no specific icon is found for the genre, you can provide a default icon
            return R.drawable.ic_android;
        }
    }

    @Override
    public int getItemCount() {
        return daftarMovie.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout clGenre;
        TextView tvGenre;
        ImageView ivGenre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clGenre = itemView.findViewById(R.id.cl_category);
            tvGenre = itemView.findViewById(R.id.tv_category);
            ivGenre = itemView.findViewById(R.id.iv_category);
        }
    }
}
