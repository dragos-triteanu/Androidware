package com.smth.androidware.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smth.androidware.R;
import com.smth.androidware.adapter.NavigationDrawerAdapter;
import com.smth.androidware.model.NavigationDrawerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dragos on 7/14/2016.
 */
public class NavigationDrawerFragment extends Fragment {


    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer,container,false);
        setupRecyclerView(view);
        return view;
    }

    private void setupRecyclerView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.drawerRecyclerView);
        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(getActivity(),spamData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private List<NavigationDrawerItem> spamData() {
        List<NavigationDrawerItem> someList = new ArrayList<>();
        someList.add(new NavigationDrawerItem(R.drawable.thumb_01,"One"));
        someList.add(new NavigationDrawerItem(R.drawable.thumb_02,"Two"));
        someList.add(new NavigationDrawerItem(R.drawable.thumb_03,"Three"));
        someList.add(new NavigationDrawerItem(R.drawable.thumb_04,"Four"));
        return someList;
    }

    public void setupDrawer(int id,DrawerLayout drawerLayout,Toolbar toolbar){

       mDrawerLayout = drawerLayout;
       mDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose){

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
       };

        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

}
