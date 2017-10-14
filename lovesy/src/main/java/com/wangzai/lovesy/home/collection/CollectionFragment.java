package com.wangzai.lovesy.home.collection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.wangzai.lovesy.R;
import com.wangzai.lovesy.core.activity.home.BaseHomeFragment;
import com.wangzai.lovesy.core.ui.refresh.OnRequestListener;
import com.wangzai.lovesy.core.ui.refresh.RefreshHandler;
import com.wangzai.lovesy.core.util.LogUtil;
import com.wangzai.lovesy.home.HomeActivity;

import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by wangzai on 2017/9/21
 */

public class CollectionFragment extends BaseHomeFragment implements OnRequestListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;

    private RefreshHandler refreshHandler;
    private HashMap<String, Object> mParams = new HashMap<>();

    @Override
    public Object setLayout() {
        return R.layout.fragment_index_collection;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        refreshLayout.setProgressViewOffset(true, 0, 300);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        refreshHandler = RefreshHandler.create(refreshLayout, recyclerView, new CollectionDataConvert(), this);
    }

    @Override
    protected void onFragmentVisible() {
//        onRefresh();
    }

    @Override
    public void onRefresh() {
        refreshHandler.refresh("collections/featured", mParams);
    }

    @Override
    public void onLoadMore() {
        refreshHandler.loadMore();
    }

}
