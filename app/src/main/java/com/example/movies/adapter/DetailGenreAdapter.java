package com.example.movies.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;
import com.example.movies.detailmodel.Genre;

import java.util.ArrayList;

public class DetailGenreAdapter extends RecyclerView.Adapter<DetailGenreAdapter.ViewHolder> {
    private ArrayList<Genre> alResult;

    public DetailGenreAdapter(ArrayList<Genre> alResult) {
        this.alResult = alResult;
    }

    @NonNull
    @Override
    public DetailGenreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_detail_genre, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailGenreAdapter.ViewHolder holder, int position) {
        Genre genre = alResult.get(position);
        holder.etGenre.setText(genre.getName());
    }

    @Override
    public int getItemCount() {
        return alResult.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        
        EditText etGenre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            etGenre = itemView.findViewById(R.id.et_viewholderdetailgenre_genre);
        }
    }
}
