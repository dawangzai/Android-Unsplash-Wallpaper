package com.wangzai.lovesy.core.ui.refresh;

import android.annotation.SuppressLint;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wangzai.lovesy.core.R;
import com.wangzai.lovesy.core.net.HttpClient;
import com.wangzai.lovesy.core.net.callback.IError;
import com.wangzai.lovesy.core.net.callback.ISuccess;
import com.wangzai.lovesy.core.ui.recycler.DataConverter;
import com.wangzai.lovesy.core.ui.recycler.MultipleRecyclerAdapter;

import java.util.WeakHashMap;

/**
 * Created by wangzai on 2017/9/2
 */

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {

    private static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
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

    @SuppressLint("InflateParams")
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
        if (!PARAMS.isEmpty()) {
            PARAMS.clear();
        }
        params.put("page", 1);
        PARAMS.putAll(params);
        mRefreshLayout.setRefreshing(true);

        HttpClient.builder()
                .url(mUrl)
                .params(PARAMS)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        mRefreshLayout.setRefreshing(false);
                        if (response.equals("[]")) {
                            mAdapter.setEmptyView(mEmptyView);
                        } else {
                            mConvert.clearData();
                            mPagingBean.reset();
                            mAdapter.replaceData(mConvert.setJsonData(response).convert());
                            mPagingBean.addIndex();
                            mAdapter.loadMoreComplete();
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this, mRecyclerView);
                            mPagingBean.setCurrentCount(mAdapter.getData().size());
                        }
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        mRefreshLayout.setRefreshing(false);
                        if (mAdapter.getData().isEmpty()) {
                            final TextView tvErrorMsg = (TextView) mErrorView.getChildAt(0);
                            tvErrorMsg.setText(msg);
                            mAdapter.setEmptyView(mErrorView);
                        }
                    }
                })
                .build()
                .get();
    }

    public void loadMore() {
        final int pageIndex = mPagingBean.getPageIndex();
        PARAMS.put("page", pageIndex);

        HttpClient.builder()
                .url(mUrl)
                .params(PARAMS)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        mConvert.clearData();
                        mAdapter.addData(mConvert.setJsonData(response).convert());
                        mAdapter.loadMoreComplete();
                        mPagingBean.addIndex();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        mAdapter.loadMoreComplete();
                    }
                })
                .build()
                .get();
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.ll_error_layout) {
            onRefresh();
        }
    }
}
