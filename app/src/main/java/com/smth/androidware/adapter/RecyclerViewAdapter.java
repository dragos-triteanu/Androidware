package com.smth.androidware.adapter;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smth.androidware.R;
import com.smth.androidware.model.ModelForCardList;

import java.util.List;

/**
 * Illustration of a RecyclerViewAdapter class that manages the adaptation of the model to the {@link RecyclerView}.
 *
 * The RecyclerView only loads enough view holders to fit the screen. When the list is swiped, the cards that become unseen,
 * are destroyed, and the newly shown cards are freshly created. This can be seen in the logs.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private static final String TAG = RecyclerViewAdapter.class.getSimpleName();

    private List<ModelForCardList> mData;
    private LayoutInflater mInflater;
    private FloatingActionButton fab;

    public RecyclerViewAdapter(Context ctx, List<ModelForCardList> data, FloatingActionButton fab){
        this.mData = data;
        this.mInflater = LayoutInflater.from(ctx);
        this.fab = fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(mData.size(),new ModelForCardList(R.drawable.thumb_01,"SomeTitle","SomeDesk"));
            }
        });

    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG,"onCreateViewHolder");
        View view = mInflater.inflate(R.layout.component_list_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d(TAG,"onBindViewHolder position="+ position);

        ModelForCardList currentObj = mData.get(position);
        holder.setData(currentObj,position);
        holder.setListeners();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    /**
     * This is used when needing to have a different layout applied to each different row, based on an attribute.
     */
//    @Override
////    public int getItemViewType(int position) {
//          ModelForCardList model = mData.get(position);
//            if(model.title.startsWith("x")){
//                return  SOME_ITEM_TYPE_INT;
//            }else{
//                return  SOME_OTHER_ITEM_TYPE_INT;
//            }
//        return super.getItemViewType(position);
//    }

    /**
     * Inner class with the role of holding the view's internals and the current object.
     */
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title,description;
        ImageView imgThumb,imgDelete;
        int position;
        ModelForCardList current;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.rowTitle);
            description = (TextView) itemView.findViewById(R.id.rowDescription);
            imgThumb = (ImageView) itemView.findViewById(R.id.rowImg);
            imgDelete = (ImageView) itemView.findViewById(R.id.rowDelete);

        }

        public void setData(ModelForCardList currentObj, int position) {
            this.title.setText(currentObj.getTitle());
            this.imgThumb.setImageResource(currentObj.getImageId());
            this.position = position;
            this.current = currentObj;
        }

        public void setListeners() {
            imgDelete.setOnClickListener(MyViewHolder.this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.rowDelete:
                    removeItem(position);
                    break;
            }
        }
    }

    public void removeItem(int position){
        mData.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,mData.size());
    }

    public void addItem(int position,ModelForCardList model){
        mData.add(model);
        notifyItemInserted(position);
        notifyItemRangeChanged(position,mData.size());
    }

}
