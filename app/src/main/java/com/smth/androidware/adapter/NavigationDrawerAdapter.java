package com.smth.androidware.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smth.androidware.R;
import com.smth.androidware.activities.NavigationDrawerActivity;
import com.smth.androidware.model.NavigationDrawerItem;

import java.util.List;

/**
 * Illustrative adapter class for a navigation drawer.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.NavigationItemHolder> {

    private List<NavigationDrawerItem> mData;
    private LayoutInflater mInflater;
    private Context ctx;

    public NavigationDrawerAdapter(Context ctx, List<NavigationDrawerItem> nanvigationDrawerItemList) {
        this.mData = nanvigationDrawerItemList;
        mInflater = LayoutInflater.from(ctx);
        this.ctx = ctx;
    }

    @Override
    public NavigationItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.component_navigation_drawer_list_item, parent, false);
        NavigationItemHolder holder = new NavigationItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final NavigationItemHolder holder, int position) {
        NavigationDrawerItem current = mData.get(position);

        holder.menuRowImg.setImageResource(current.getImageId());
        holder.title.setText(current.getTitle());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class NavigationItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView menuRowImg;
        TextView title;

        public NavigationItemHolder(View itemView) {
            super(itemView);
            menuRowImg = (ImageView) itemView.findViewById(R.id.menuRowImg);
            title = (TextView) itemView.findViewById(R.id.menuRowTxt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(ctx,"SMTH",Toast.LENGTH_SHORT).show();
        }
    }
}
