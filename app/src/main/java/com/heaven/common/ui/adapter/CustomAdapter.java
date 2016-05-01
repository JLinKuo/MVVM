package com.heaven.common.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

/**
 * Created by neusoft on 2016/4/29.
 */
public abstract class CustomAdapter<T> extends RecyclerView.Adapter<CustomViewHolder>{
    private LayoutInflater mInflater = null;
    private int mItemViewId;
    private ArrayList<T> mDataList = new ArrayList<>();

    public CustomAdapter(Context context, int itemViewId) {
        this.mInflater = LayoutInflater.from(context);
        this.mItemViewId = itemViewId;
    }

    public CustomAdapter(Context context, int itemViewId, ArrayList<T> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mItemViewId = itemViewId;
        this.mDataList = data;
    }
    /**
     * dynamic add data
     */
    public void appendData(ArrayList<T> appendData) {
        if (appendData != null && appendData.size() > 0) {
            mDataList.addAll(appendData);
            notifyDataSetChanged();
        }
    }

    /**
     * replace all data
     */
    public void replaceAllData(ArrayList<T> appendData) {
        if (appendData != null) {
            mDataList.addAll(appendData);
            notifyDataSetChanged();
        }
    }

    /**
     * remove the opposite position data
     * @param position position
     */
    public void remove(int position){
        if (position >= 0 && position < mDataList.size()) {
            mDataList.remove(position);
            notifyDataSetChanged();
        }
    }
    /**
     * remove all data
     */
    public void removeAll(){
        mDataList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(mItemViewId,parent);
        CustomViewHolder holder = new CustomViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        convert(holder,position);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param holder view holder
     * @param position   the holder position.
     */
    protected abstract void convert(CustomViewHolder holder, int position);

}
