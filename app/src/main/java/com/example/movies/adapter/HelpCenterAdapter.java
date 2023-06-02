package com.example.movies.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;
import com.example.movies.detail.HelpCenter;
import com.example.movies.domain.HelpCenterDomain;

import java.util.ArrayList;

public class HelpCenterAdapter extends RecyclerView.Adapter<HelpCenterAdapter.ViewHolder> {

    ArrayList<HelpCenterDomain> helpCenterDomains;

    public HelpCenterAdapter(ArrayList<HelpCenterDomain> helpCenterDomains) {
        this.helpCenterDomains = helpCenterDomains;
    }

    //    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HelpCenterDomain hcd = helpCenterDomains.get(position);

        holder.categoryName.setText(hcd.getTitle());
//        String picUrl = "";
//        switch (position) {
//            case 0 : {
//                picUrl="@drawable/pokemon";
//                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.category_background));
//                break;
//            }
//            case 1 : {
//                picUrl="@drawable/pokemon";
//                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.category_background));
//                break;
//            }
//            case 2 : {
//                picUrl="@drawable/pokemon";
//                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.category_background));
//                break;
//            }
//            case 3 : {
//                picUrl="@drawable/pokemon";
//                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.category_background));
//                break;
//            }
//            case 4 : {
//                picUrl="@drawable/pokemon";
//                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.category_background));
//                break;
//            }
//            case 5 : {
//                picUrl="@drawable/pokemon";
//                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.category_background));
//                break;
//            }
//        }
//        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(picUrl, "drawable", holder.itemView.getContext().getPackageName());
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Toast.makeText(holder.itemView.getContext(), hcd.getPos(), Toast.LENGTH_SHORT).show();

                String pos = hcd.getPos();

                Intent intent = new Intent();
                intent.putExtra("varPos", pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return helpCenterDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageView categoryPic;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.tv_category);
            categoryPic = itemView.findViewById(R.id.iv_category);
            mainLayout = itemView.findViewById(R.id.cl_category);
        }
    }
}
