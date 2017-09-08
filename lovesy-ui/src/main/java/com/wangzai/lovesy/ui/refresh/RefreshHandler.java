package com.wangzai.lovesy.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.wangzai.lovesy.ui.recycler.DataConverter;
import com.wangzai.lovesy.ui.recycler.MultipleRecyclerAdapter;

/**
 * Created by wangzai on 2017/9/2
 */

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener {

    private final SwipeRefreshLayout mRefreshLayout;
    private final RecyclerView mRecyclerView;
    private final PagingBean mPagingBean;
    private final DataConverter mConvert;
    private MultipleRecyclerAdapter mAdapter;

    public RefreshHandler(SwipeRefreshLayout refreshLayout,
                          RecyclerView recyclerView,
                          PagingBean pagingBean,
                          DataConverter convert) {
        this.mRefreshLayout = refreshLayout;
        this.mRecyclerView = recyclerView;
        this.mPagingBean = pagingBean;
        this.mConvert = convert;
    }

    private static RefreshHandler create(SwipeRefreshLayout refreshLayout,
                                         RecyclerView recyclerView,
                                         PagingBean pagingBean,
                                         DataConverter convert) {
        return new RefreshHandler(refreshLayout, recyclerView, pagingBean, convert);
    }

    private void refresh() {
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        refresh();
    }
}
