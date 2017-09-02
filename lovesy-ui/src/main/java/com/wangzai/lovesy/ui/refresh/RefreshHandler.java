package com.wangzai.lovesy.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by wangzai on 2017/9/2
 */

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener {

    private final SwipeRefreshLayout mRefreshLayout;

    public RefreshHandler(SwipeRefreshLayout refreshLayout) {
        this.mRefreshLayout = refreshLayout;
    }

    private void refresh() {
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        refresh();
    }
}
