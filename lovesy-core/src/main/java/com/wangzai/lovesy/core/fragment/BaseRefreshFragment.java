package com.wangzai.lovesy.core.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.wangzai.lovesy.core.R;
import com.wangzai.lovesy.core.R2;
import com.wangzai.lovesy.core.ui.recycler.DataConverter;
import com.wangzai.lovesy.core.ui.refresh.OnRequestListener;
import com.wangzai.lovesy.core.ui.refresh.RefreshHandler;

import java.util.HashMap;
import java.util.WeakHashMap;

import butterknife.BindView;

/**
 * Created by wangzai on 2017/10/10
 */

public abstract class BaseRefreshFragment extends LoveSyFragment implements OnRequestListener {
    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R2.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private RefreshHandler refreshHandler;
    private String mUrl;
    protected WeakHashMap<String, Object> mParams = new WeakHashMap<>();

    protected abstract String setUrl();

    protected abstract DataConverter setDataConverter();

    protected abstract SimpleClickListener addItemClickListener();

    protected WeakHashMap<String, Object> setParams(WeakHashMap<String, Object> params) {
        return params;
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_refresh_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mUrl = setUrl();
        setParams(mParams);
        setHasOptionsMenu(true);
        refreshLayout.setProgressViewOffset(true, 0, 300);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.addOnItemTouchListener(addItemClickListener());
        refreshHandler = RefreshHandler.create(refreshLayout, recyclerView, setDataConverter(), this);
    }

    @Override
    protected void onFragmentVisible() {
        onRefresh();
    }

    @Override
    public void onRefresh() {
        refreshHandler.refresh(mUrl, mParams);
    }

    @Override
    public void onLoadMore() {
        refreshHandler.loadMore();
    }
}
