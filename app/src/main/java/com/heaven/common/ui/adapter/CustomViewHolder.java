package com.heaven.common.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by neusoft on 2016/4/29.
 */
public class CustomViewHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> views;

    public CustomViewHolder(View itemView) {
        super(itemView);
        views = new SparseArray<>();
    }

    public <T extends View> T getView(int id) {
        View view = views.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            views.put(id, view);
        }
        return (T) view;
    }

    public CustomViewHolder setText(int viewId, String value) {
        TextView view = getView(viewId);
        view.setText(value);
        return CustomViewHolder.this;
    }

    public CustomViewHolder setTextColor(int viewId, int textColor) {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return CustomViewHolder.this;
    }

    public CustomViewHolder setImageResource(int viewId, int imageResId) {
        ImageView view = getView(viewId);
        view.setImageResource(imageResId);
        return CustomViewHolder.this;
    }

    public CustomViewHolder setBackgroundColor(int viewId, int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return CustomViewHolder.this;
    }

    public CustomViewHolder setBackgroundResource(int viewId, int backgroundRes) {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return CustomViewHolder.this;
    }

    public CustomViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return CustomViewHolder.this;
    }

    public CustomViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return CustomViewHolder.this;
    }

    public CustomViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return CustomViewHolder.this;
    }

    public CustomViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return CustomViewHolder.this;
    }

    public CustomViewHolder setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return CustomViewHolder.this;
    }
}
