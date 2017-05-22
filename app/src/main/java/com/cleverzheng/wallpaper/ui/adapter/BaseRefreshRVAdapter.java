package com.cleverzheng.wallpaper.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wangzai on 2017/5/22.
 */

public class BaseRefreshRVAdapter extends RecyclerView.Adapter<BaseRefreshRVAdapter.SimpleViewHolder> {

    private static final int TYPE_ITEM_NORMAL = 0;  //普通Item View
    private static final int TYPE_ITEM_FOOTER = 1;  //底部加载更多动画

    private int itemCount;


    public BaseRefreshRVAdapter() {

    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_ITEM_FOOTER;
        } else {
            return TYPE_ITEM_NORMAL;
        }
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {

        public SimpleViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void loadingMore() {
        addFootLoadingView();
    }

    private void addFootLoadingView() {

    }
}
