package com.smth.androidware.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smth.androidware.R;

/**
 * Created by Dragos on 7/18/2016.
 */
public class Fragment2 extends Fragment {
    private static final String TAG = Fragment2.class.getName();

    public Fragment2(){
        Log.i(TAG,"Fragment TWO created");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment2,container,false);
    }
}
