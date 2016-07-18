package com.smth.androidware.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.smth.androidware.R;
import com.smth.androidware.model.ViewPagerModel;

import java.util.List;

/**
 * Class for illustrating the functionality of the View pager.As it can be seen by the logging statements,
 * the view pager automatically instantiates/destroys the vies based on the position in the list where the view is.
 * The ViewPager will instantiate all adjacent views, and will destroy all views that are becoming non adjacent when the user is swiping.
 * 3 views will be maximally allowed to be instantiated at once.
 * .
 */
public class SimplePagerAdapter extends PagerAdapter {
    private static final String TAG = SimplePagerAdapter.class.getName();

    private final List<ViewPagerModel> mData;
    private LayoutInflater mInflater;

    public SimplePagerAdapter(Context ctx, List<ViewPagerModel> viewPagerModels) {
        this.mData = viewPagerModels;
        mInflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.d(TAG,"Creating item at position="+position);
        View inflatedView = mInflater.inflate(R.layout.component_view_pager_item, container, false);

        ImageView imageView = (ImageView) inflatedView.findViewById(R.id.imageItem);
        TextView textView = (TextView) inflatedView.findViewById(R.id.textItem);

        ViewPagerModel viewPagerModel = mData.get(position);

        imageView.setImageResource(viewPagerModel.getImageId());
        textView.setText(viewPagerModel.getTitle());

        container.addView(inflatedView);

        return inflatedView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
         Log.d(TAG,"Destroying item at position="+position);
        //Remove the item described in the component_view_pager_item.xml
         container.removeView((FrameLayout) object);
    }
}
