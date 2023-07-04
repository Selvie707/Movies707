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
import com.example.movies.activities.TrailerActivity;
import com.example.movies.detail.DetailDownloadMovie;
import com.example.movies.myfavmodels.Datum;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    private Context ctx;
    private ArrayList<Datum> daftarFavorit;

    public TrailerAdapter(ArrayList<Datum> daftarFavorit) {
        this.daftarFavorit = daftarFavorit;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_trailer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Datum rwm = daftarFavorit.get(position);
        holder.tvTitle.setText(rwm.getTitle());
        holder.tvYear.setText(rwm.getReleasedate());
        holder.tvDescription.setText(rwm.getSinopsis());
        Glide.with(holder.itemView.getContext())
                .load(rwm.getPosterpath())
                .apply(new RequestOptions().override(350, 550))
                .into(holder.ivPhoto);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = String.valueOf(rwm.getId());
                String trailerid = rwm.getTrailerid();

                Intent intent = new Intent(holder.itemView.getContext(), TrailerActivity.class);
                intent.putExtra("varIdd", id);
                intent.putExtra("varTrailerId", trailerid);

                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (daftarFavorit.isEmpty()) {
            Toast.makeText(ctx, "Your Favorite List is still empty, let's fill 'em up!", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return daftarFavorit.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;
        TextView tvTitle, tvYear, tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPhoto = itemView.findViewById(R.id.iv_recently_watched);
            tvTitle = itemView.findViewById(R.id.tv_recent_title);
            tvYear = itemView.findViewById(R.id.tv_recent_year);
            tvDescription = itemView.findViewById(R.id.tv_recent_description);
        }
    }
}
