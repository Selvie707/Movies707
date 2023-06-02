package com.example.movies.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;
import com.example.movies.domain.HelpCenterDomain;
import com.example.movies.domain.HelpDetailDomain;

import java.util.ArrayList;

public class HelpDetailAdapter extends RecyclerView.Adapter<HelpDetailAdapter.ViewHolder> {

    ArrayList<HelpDetailDomain> helpDetailDomains;

    public HelpDetailAdapter(ArrayList<HelpDetailDomain> helpDetailDomains) {
        this.helpDetailDomains = helpDetailDomains;
    }

    //    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.helpcenter_category, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HelpDetailDomain hdd = helpDetailDomains.get(position);

        holder.title.setText(hdd.getTitle());
        holder.words.setText(hdd.getWords());
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
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int visibility = holder.words.getVisibility();

                if(visibility == 0) {
                    holder.words.setVisibility(View.GONE);
                }
                else if(visibility == 8) {
                    holder.words.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return helpDetailDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText title, words;
        LinearLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.et_helpcenter_helpone);
            words = itemView.findViewById(R.id.et_helpcenter_helptwo);
            mainLayout = itemView.findViewById(R.id.ll_helpcenter_viewholder);
        }
    }
}
