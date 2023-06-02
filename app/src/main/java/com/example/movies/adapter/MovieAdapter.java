package com.example.movies.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.example.movies.R;
import com.example.movies.data.Movie;
import com.example.movies.detail.DetailRecentlyWatched;
import com.example.movies.models.Result;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private ArrayList<Result> daftarMovie;

    public MovieAdapter(ArrayList<Result> daftarMovie) {
        this.daftarMovie = daftarMovie;
    }

    public void setFilteredList(ArrayList<Result> filteredList) {
        this.daftarMovie = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recently_watched_viewpager, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Result movie = daftarMovie.get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvYear.setText(movie.getRelease_date());
        Glide.with(holder.itemView.getContext())
                .load(movie.getPoster_path())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.ivPhoto);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = String.valueOf(movie.getId());

                Intent intent = new Intent(holder.itemView.getContext(), DetailRecentlyWatched.class);
                intent.putExtra("varId", id);

                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return daftarMovie.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivPhoto;
        TextView tvTitle, tvYear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPhoto = itemView.findViewById(R.id.iv_recently_watched);
            tvTitle = itemView.findViewById(R.id.tv_recent_title);
            tvYear = itemView.findViewById(R.id.tv_recent_year);
        }
    }
}
