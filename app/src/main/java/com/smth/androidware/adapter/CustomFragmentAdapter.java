package com.smth.androidware.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.smth.androidware.fragment.Fragment1;
import com.smth.androidware.fragment.Fragment2;
import com.smth.androidware.fragment.Fragment3;
import com.smth.androidware.fragment.Fragment4;

/**
 * Fragment pager adapter keeps all fragments in memory, never destroys items. A new fragment is instantiated
 * upon swiping onto it.
 * Ex: when having 50 fragments, if the user keeps swiping, he will load all fragments to memory, with no destroy taking place.
 *
 * The optimal solution is using {@link CustomFragmentStatePagerAdapter}.
 */
public class CustomFragmentAdapter extends FragmentPagerAdapter {
    private static final String TAG = CustomFragmentAdapter.class.getName();
    private final int ITEMS = 4;

    public CustomFragmentAdapter(FragmentManager fm) {
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
