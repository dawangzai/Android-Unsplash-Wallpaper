package com.wangzai.lovesy.core.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wangzai.lovesy.core.net.rx.RxHttpClient;
import com.wangzai.lovesy.core.net.rx.observer.ResultObserver;
import com.wangzai.lovesy.core.ui.recycler.DataConverter;
import com.wangzai.lovesy.core.ui.recycler.MultipleRecyclerAdapter;

import io.reactivex.annotations.NonNull;

/**
 * Created by wangzai on 2017/9/2
 */

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    private final SwipeRefreshLayout mRefreshLayout;
    private final RecyclerView mRecyclerView;
    private final PagingBean mPagingBean;
    private final DataConverter mConvert;
    private MultipleRecyclerAdapter mAdapter;

    private RefreshHandler(SwipeRefreshLayout refreshLayout,
                           RecyclerView recyclerView,
                           PagingBean pagingBean,
                           DataConverter convert) {
        this.mRefreshLayout = refreshLayout;
        this.mRecyclerView = recyclerView;
        this.mPagingBean = pagingBean;
        this.mConvert = convert;
        init();
    }

    private void init() {
        mRefreshLayout.setOnRefreshListener(this);
        mAdapter = MultipleRecyclerAdapter.create();
        mRecyclerView.setAdapter(mAdapter);
    }

    public static RefreshHandler create(SwipeRefreshLayout refreshLayout,
                                        RecyclerView recyclerView,
                                        DataConverter convert) {
        return new RefreshHandler(refreshLayout, recyclerView, new PagingBean(), convert);
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onLoadMoreRequested() {
        paging();
    }

    public void refresh() {
        mRefreshLayout.setRefreshing(true);
        firstPage();
    }

    private void firstPage() {
        RxHttpClient.builder()
                .url("photos")
                .params("page", 1)
                .build()
                .get()
                .subscribe(new ResultObserver() {
                    @Override
                    public void onSuccess(@NonNull String result) {
                        mRefreshLayout.setRefreshing(false);
                        mConvert.clearData();
                        mPagingBean.reset();
                        mAdapter.replaceData(mConvert.setJsonData(result).convert());
                        mPagingBean.addIndex();
                        mAdapter.loadMoreComplete();
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this, mRecyclerView);
                        mPagingBean.setCurrentCount(mAdapter.getData().size());
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        mRefreshLayout.setRefreshing(false);
                    }
                });
    }

    private void paging() {
        final int pageIndex = mPagingBean.getPageIndex();
        RxHttpClient.builder()
                .url("photos")
                .params("page", pageIndex)
                .build()
                .get()
                .subscribe(new ResultObserver() {
                    @Override
                    public void onSuccess(@NonNull String result) {
                        mConvert.clearData();
                        mAdapter.addData(mConvert.setJsonData(result).convert());
                        mAdapter.loadMoreComplete();
                        mPagingBean.addIndex();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        mAdapter.loadMoreComplete();
                    }
                });
    }
}
