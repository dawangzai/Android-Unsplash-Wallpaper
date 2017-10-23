package com.wangzai.lovesy.core.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wangzai.lovesy.core.R;
import com.wangzai.lovesy.core.net.rx.RxHttpClient;
import com.wangzai.lovesy.core.net.rx.observer.ResultObserver;
import com.wangzai.lovesy.core.ui.recycler.DataConverter;
import com.wangzai.lovesy.core.ui.recycler.MultipleRecyclerAdapter;

import java.util.HashMap;
import java.util.WeakHashMap;

import io.reactivex.annotations.NonNull;

/**
 * Created by wangzai on 2017/9/2
 */

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {

    private static final WeakHashMap<String, Object> mParams = new WeakHashMap<>();
    private static String mUrl = null;

    private final SwipeRefreshLayout mRefreshLayout;
    private final RecyclerView mRecyclerView;
    private final PagingBean mPagingBean;
    private final DataConverter mConvert;
    private MultipleRecyclerAdapter mAdapter;
    private OnRequestListener mListener;
    private LinearLayout mErrorView;
    private RelativeLayout mEmptyView;


    private RefreshHandler(SwipeRefreshLayout refreshLayout,
                           RecyclerView recyclerView,
                           PagingBean pagingBean,
                           DataConverter convert,
                           OnRequestListener listener) {
        this.mRefreshLayout = refreshLayout;
        this.mRecyclerView = recyclerView;
        this.mPagingBean = pagingBean;
        this.mConvert = convert;
        this.mListener = listener;
        init();
    }

    private void init() {
        mRefreshLayout.setOnRefreshListener(this);
        mAdapter = MultipleRecyclerAdapter.create();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(RefreshHandler.this, mRecyclerView);
        mAdapter.disableLoadMoreIfNotFullPage();
        mErrorView = (LinearLayout) LayoutInflater.from(mRecyclerView.getContext()).inflate(R.layout.view_error, null);
        mEmptyView = (RelativeLayout) LayoutInflater.from(mRecyclerView.getContext()).inflate(R.layout.view_empty, null);
        mErrorView.setOnClickListener(this);
    }

    public static RefreshHandler create(SwipeRefreshLayout refreshLayout,
                                        RecyclerView recyclerView,
                                        DataConverter convert, OnRequestListener listener) {
        return new RefreshHandler(refreshLayout, recyclerView, new PagingBean(), convert, listener);
    }

    @Override
    public void onRefresh() {
        mListener.onRefresh();
    }

    @Override
    public void onLoadMoreRequested() {
        mListener.onLoadMore();
    }

    public void refresh(String url, WeakHashMap<String, Object> params) {
        mUrl = url;
        if (!mParams.isEmpty()) {
            mParams.clear();
        }
        params.put("page", 1);
        mParams.putAll(params);
        mRefreshLayout.setRefreshing(true);
        RxHttpClient.builder()
                .url(mUrl)
                .params(mParams)
                .build()
                .get()
                .subscribe(new ResultObserver() {
                    @Override
                    public void onSuccess(@NonNull String result) {
                        mRefreshLayout.setRefreshing(false);
                        if (result.equals("[]")) {
                            mAdapter.setEmptyView(mEmptyView);
                        } else {
                            mConvert.clearData();
                            mPagingBean.reset();
                            mAdapter.replaceData(mConvert.setJsonData(result).convert());
                            mPagingBean.addIndex();
                            mAdapter.loadMoreComplete();
//                        mAdapter.setOnLoadMoreListener(RefreshHandler.this, mRecyclerView);
                            mPagingBean.setCurrentCount(mAdapter.getData().size());
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        mRefreshLayout.setRefreshing(false);
                        if (mAdapter.getData().isEmpty()) {
                            final TextView tvErrorMsg = (TextView) mErrorView.getChildAt(0);
                            tvErrorMsg.setText(msg);
                            mAdapter.setEmptyView(mErrorView);
                        }
                    }
                });
    }

    public void loadMore() {
        final int pageIndex = mPagingBean.getPageIndex();
        mParams.put("page", pageIndex);
        RxHttpClient.builder()
                .url(mUrl)
                .params(mParams)
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

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.ll_error_layout) {
            onRefresh();
        }
    }
}
