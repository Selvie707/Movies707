package com.example.movies.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.movies.R;
import com.example.movies.detail.DetailFavoriteMovie;
import com.example.movies.detail.DetailRecentlyWatched;
import com.example.movies.myfavmodels.Datum;

import java.util.ArrayList;

public class FavoritAdapter extends RecyclerView.Adapter<FavoritAdapter.ViewHolder> {
    private Context ctx;
    private ArrayList<Datum> daftarFavorit;

    public FavoritAdapter(ArrayList<Datum> daftarFavorit) {
        this.daftarFavorit = daftarFavorit;
    }

    public void setFilteredList(ArrayList<Datum> filteredList) {
        this.daftarFavorit = filteredList;
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
        Datum rwm = daftarFavorit.get(position);
        holder.tvTitle.setText(rwm.getTitle());
        holder.tvYear.setText(rwm.getReleasedate());
        Glide.with(holder.itemView.getContext())
                .load(rwm.getPosterpath())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.ivPhoto);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String title = rwm.getTitle();
//                String year = rwm.getRelease_date();
//                String photo = rwm.getPoster_path();
//
//                Intent intent = new Intent(holder.itemView.getContext(), DetailRecentlyWatched.class);
//                intent.putExtra("varTitle", title);
//                intent.putExtra("varYear", year);
//                intent.putExtra("varPhoto", photo);
//                holder.itemView.getContext().startActivity(intent);

                String id = String.valueOf(rwm.getId());

                Intent intent = new Intent(holder.itemView.getContext(), DetailFavoriteMovie.class);
                intent.putExtra("varId", id);

                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (daftarFavorit == null) {
            Toast.makeText(ctx, "Your Favorite List is still empty, let's fill 'em up!", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return daftarFavorit.size();
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
