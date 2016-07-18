package com.smth.androidware.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.smth.androidware.R;
import com.smth.androidware.adapter.CustomFragmentAdapter;
import com.smth.androidware.adapter.CustomFragmentStatePagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentViewPagerActivity extends AppCompatActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_view_pager);
        ButterKnife.bind(this);


        viewPager.setAdapter(new CustomFragmentStatePagerAdapter(getSupportFragmentManager()));
    }
}
