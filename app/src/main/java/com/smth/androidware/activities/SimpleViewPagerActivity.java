package com.smth.androidware.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.smth.androidware.R;
import com.smth.androidware.adapter.SimplePagerAdapter;
import com.smth.androidware.model.ViewPagerModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimpleViewPagerActivity extends AppCompatActivity {

    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_view_pager);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new SimplePagerAdapter(this,getDataList()));
    }

    public List<ViewPagerModel> getDataList() {
        List<ViewPagerModel> viewPagerModels = new ArrayList<>();
        viewPagerModels.add(new ViewPagerModel(R.drawable.image1, "Large image 1"));
        viewPagerModels.add(new ViewPagerModel(R.drawable.image2, "Large image 2"));
        viewPagerModels.add(new ViewPagerModel(R.drawable.image3, "Large image 3"));

        return viewPagerModels;
    }


}
