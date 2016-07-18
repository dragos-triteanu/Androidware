package com.smth.androidware.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.smth.androidware.fragment.Fragment1;
import com.smth.androidware.fragment.Fragment2;
import com.smth.androidware.fragment.Fragment3;
import com.smth.androidware.fragment.Fragment4;

/**
 * Illustrative implementation of a {@link FragmentStatePagerAdapter} that destroys the views uppon scrolling.
 * This adapter will destroy any non adjacent views, and recreate them once they become adjacent.
 */
public class CustomFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = CustomFragmentStatePagerAdapter.class.getName();
    private final int ITEMS = 4;


    public CustomFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(TAG,"Retrieving item at position="+position);
        switch (position){
            case 0:
                return new Fragment1();
            case 1:
                return new Fragment2();
            case 2:
                return new Fragment3();
            case 3:
                return new Fragment4();
            default:
                return new Fragment1();

        }
    }

    @Override
    public int getCount() {
        return ITEMS;
    }
}
