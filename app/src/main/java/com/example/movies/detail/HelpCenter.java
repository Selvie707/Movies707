package com.example.movies.detail;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;
import com.example.movies.adapter.HelpCenterAdapter;
import com.example.movies.adapter.HelpDetailAdapter;
import com.example.movies.data.DataHelpCenter;
import com.example.movies.data.DataHelpDetail;
import com.example.movies.domain.HelpCenterDomain;
import com.example.movies.domain.HelpDetailDomain;

import java.util.ArrayList;

public class HelpCenter extends AppCompatActivity {
    private RecyclerView.Adapter rvAdapter, rvDetailAdapter;
    private RecyclerView rvCategory, rvCategoryDetail;

    private ArrayList<HelpCenterDomain> alHcd = new ArrayList<>();
    private ArrayList<HelpDetailDomain> alHdd = new ArrayList<>();

    private int mView = 0;

    static final String STATE_MODE = "MODE VIEW";

    // Card Recently Watched
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        outState.putInt(STATE_MODE, mView);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);

        rvCategory = findViewById(R.id.rv_helpcenter_category);
        rvCategoryDetail = findViewById(R.id.rv_helpcenter_categoryhelp);

        rvCategory.setHasFixedSize(true);
        rvCategoryDetail.setHasFixedSize(true);

        alHcd.addAll(DataHelpCenter.DataHelpCenter());
        alHdd.addAll(DataHelpDetail.DataGettingStarted());

        Intent intent = new Intent();
        String pos = intent.getStringExtra("varPos");

        RVCategory();
        RVCategoryDetail();
    }

    // Category
    private void RVCategory() {
        mView = 0;
        LinearLayoutManager llm = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
        rvCategory.setLayoutManager(llm);

        HelpCenterAdapter hca = new HelpCenterAdapter(alHcd);
        rvCategory.setAdapter(hca);
    }

    // More Help Detail
    private void RVCategoryDetail() {
        mView = 0;
        LinearLayoutManager llm = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        rvCategoryDetail.setLayoutManager(llm);

        HelpDetailAdapter hda = new HelpDetailAdapter(alHdd);
        rvCategoryDetail.setAdapter(hda);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        RVCategory();
        RVCategoryDetail();
        return super.onOptionsItemSelected(item);
    }
}