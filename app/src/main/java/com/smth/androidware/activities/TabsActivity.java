package com.smth.androidware.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.smth.androidware.R;
import com.smth.androidware.adapter.TabsAdapter;
import com.smth.androidware.fragment.Fragment1;
import com.smth.androidware.fragment.Fragment2;
import com.smth.androidware.fragment.Fragment3;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TabsActivity extends AppCompatActivity {

    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.tabs)
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        ButterKnife.bind(this);

        toolbar.setTitle("Tabs");

        prepareData();

        TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager(),fragmentList,titleList);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        setTabIcons();
    }

    private void setTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.thumb_01);
        tabLayout.getTabAt(0).setText("X");
        tabLayout.getTabAt(1).setIcon(R.drawable.thumb_02);
        tabLayout.getTabAt(1).setText("Y");
        tabLayout.getTabAt(2).setIcon(R.drawable.thumb_03);
        tabLayout.getTabAt(2).setText("Z");
    }


    public void prepareData(){
        fragmentList.add(new Fragment1());
        fragmentList.add(new Fragment2());
        fragmentList.add(new Fragment3());
    }
}
