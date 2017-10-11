package com.wangzai.lovesy.user.collection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wangzai.lovesy.R;
import com.wangzai.lovesy.core.fragment.user.BaseTabItemFragment;
import com.wangzai.lovesy.core.ui.refresh.OnRequestListener;
import com.wangzai.lovesy.core.ui.refresh.RefreshHandler;
import com.wangzai.lovesy.home.collection.CollectionDataConvert;

import butterknife.BindView;

/**
 * Created by wangzai on 2017/10/11
 */

public class CollectionFragment extends BaseTabItemFragment implements OnRequestListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private RefreshHandler refreshHandler;

    @Override
    public Object setLayout() {
        return R.layout.fragment_collection;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        refreshLayout.setProgressViewOffset(true, 0, 300);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.addOnItemTouchListener(new CollectionItemClickListener(this));
        refreshHandler = RefreshHandler.create(refreshLayout, recyclerView, new CollectionDataConvert(), this);
    }

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();
        onRefresh();
    }

    @Override
    public void onRefresh() {
        refreshHandler.refresh("https://api.unsplash.com/users/wangzai/collections");
    }

    @Override
    public void onLoadMore() {
        refreshHandler.loadMore("https://api.unsplash.com/users/wangzai/collections");
    }
}
