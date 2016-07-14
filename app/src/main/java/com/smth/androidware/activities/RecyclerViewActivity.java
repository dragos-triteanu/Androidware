package com.smth.androidware.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.smth.androidware.R;
import com.smth.androidware.adapter.RecyclerViewAdapter;
import com.smth.androidware.model.ModelForCardList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);

        setupToolbar();
        setupRecyclerView();
    }

    private List<ModelForCardList> spamData() {
        List<ModelForCardList> list = new ArrayList<>();
        list.add(new ModelForCardList(R.drawable.thumb_01,"Smth","Desc"));
        list.add(new ModelForCardList(R.drawable.thumb_02,"Github","Desc"));
        list.add(new ModelForCardList(R.drawable.thumb_03,"XSmth","Desc"));
        list.add(new ModelForCardList(R.drawable.thumb_01,"Smth","Desc"));
        list.add(new ModelForCardList(R.drawable.thumb_02,"Github","Desc"));
        list.add(new ModelForCardList(R.drawable.thumb_03,"XSmth","Desc"));
        list.add(new ModelForCardList(R.drawable.thumb_01,"Smth","Desc"));
        list.add(new ModelForCardList(R.drawable.thumb_02,"Github","Desc"));
        list.add(new ModelForCardList(R.drawable.thumb_03,"XSmth","Desc"));
        list.add(new ModelForCardList(R.drawable.thumb_01,"Smth","Desc"));
        list.add(new ModelForCardList(R.drawable.thumb_02,"Github","Desc"));
        list.add(new ModelForCardList(R.drawable.thumb_03,"XSmth","Desc"));
        list.add(new ModelForCardList(R.drawable.thumb_01,"Smth","Desc"));
        list.add(new ModelForCardList(R.drawable.thumb_02,"Github","Desc"));
        list.add(new ModelForCardList(R.drawable.thumb_03,"XSmth","Desc"));
        return list;
    }

    private void setupToolbar() {
        toolbar.setTitle("RecyclerView");
        toolbar.setLogo(R.mipmap.ic_logo);
        toolbar.inflateMenu(R.menu.menu_recycler_view);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.linearViewHorizontal:
                        LinearLayoutManager mLinearLayoutManagerHorizontal = new LinearLayoutManager(RecyclerViewActivity.this);
                        mLinearLayoutManagerHorizontal.setOrientation(LinearLayoutManager.HORIZONTAL);
                        recyclerView.setLayoutManager(mLinearLayoutManagerHorizontal);
                        break;
                    case R.id.linearViewVertical:
                        LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(RecyclerViewActivity.this);
                        mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
                        break;
                    case R.id.staggeredViewHorizontal:
                        StaggeredGridLayoutManager mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL);
                        recyclerView.setLayoutManager(mStaggeredGridLayoutManager);
                        break;
                    case R.id.staggeredViewVertical:
                        StaggeredGridLayoutManager staggeredViewVertical = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(staggeredViewVertical);
                        break;
                    case R.id.gridView:
                        GridLayoutManager gridViewManager = new GridLayoutManager(RecyclerViewActivity.this,3);
                        recyclerView.setLayoutManager(gridViewManager);
                        break;
                }

                return true;
            }
        });
    }


    private void setupRecyclerView() {
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,spamData(),fab);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL); //Can also be horizontal
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


}
