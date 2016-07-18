package com.smth.androidware.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.smth.androidware.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.goToToolbarBtn)
    Button goToToolbarBtn;

    @BindView(R.id.goToRecyclerViewBtn)
    Button goToRecyclerViewBtn;

    @BindView(R.id.goToNavigationDrawer)
    Button goToNavigationDrawer;

    @BindView(R.id.goToAnimations)
    Button goToAnimations;

    @BindView(R.id.goToSimpleViewPager)
    Button goToSimpleViewPager;

    @BindView(R.id.goToFragmentViewPager)
    Button goToFragmentViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        goToToolbarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ToolbarActivity.class);
                startActivity(intent);
            }
        });

        goToRecyclerViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RecyclerViewActivity.class);
                startActivity(intent);
            }
        });

        goToNavigationDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NavigationDrawerActivity.class);
                startActivity(intent);
            }
        });

        goToAnimations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AnimationsActivity.class);
                startActivity(intent);
            }
        });

        goToSimpleViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SimpleViewPagerActivity.class);
                startActivity(intent);
            }
        });

        goToFragmentViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FragmentViewPagerActivity.class);
                startActivity(intent);
            }
        });

    }
}
