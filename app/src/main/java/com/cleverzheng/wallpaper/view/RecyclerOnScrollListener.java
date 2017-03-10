package com.cleverzheng.wallpaper.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.cleverzheng.wallpaper.utils.LogUtil;

/**
 * @author：cleverzheng
 * @date：2017/2/28:11:46
 * @email：zhengwang043@gmail.com
 * @description：
 */

public abstract class RecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean isLoading = false;

    public RecyclerOnScrollListener(RecyclerView.LayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (mLayoutManager != null) {
            LogUtil.i("cd", "onScrollStateChanged");
            //得到当前显示的最后一个item的view
            View lastChildView = mLayoutManager.getChildAt(mLayoutManager.getChildCount() - 1);
//        View lastChildView = recyclerView.getLayoutManager().getChildAt(recyclerView.getLayoutManager().getChildCount() - 1);
            //得到lastChildView的bottom坐标值
            if (lastChildView != null) {
                int lastChildBottom = lastChildView.getBottom();
                //得到Recyclerview的底部坐标减去底部padding值，也就是显示内容最底部的坐标
                int recyclerBottom = recyclerView.getBottom() - recyclerView.getPaddingBottom();
                //通过这个lastChildView得到这个view当前的position值
//        int lastPosition = recyclerView.getLayoutManager().getPosition(lastChildView);
                int lastPosition = mLayoutManager.getPosition(lastChildView);

                //判断lastChildView的bottom值跟recyclerBottom
                //判断lastPosition是不是最后一个position
                //如果两个条件都满足则说明是真正的滑动到了底部
                LogUtil.i("cd", "onScrolled");
                if (/*lastChildBottom == recyclerBottom
                && */lastPosition == mLayoutManager.getItemCount() - 1) {
                    LogUtil.i("cd", "滑动到底了");
                    if (isLoading) {
                        return;
                    } else {
                        onLoadMore();
                    }
                }
            }
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

    }

    public abstract void onLoadMore();

    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }
}
