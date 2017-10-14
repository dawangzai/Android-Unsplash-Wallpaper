package com.wangzai.lovesy.home.index;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class IndexFragment extends BaseHomeFragment implements OnRequestListener {

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
        super.onBindView(savedInstanceState, rootView);
        refreshLayout.setProgressViewOffset(false, 0, 300);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.addOnItemTouchListener(new IndexItemClickListener(this));
        refreshHandler = RefreshHandler.create(refreshLayout, recyclerView, new PhotoDataConvert(), this);
    }

    @Override
    protected void onFragmentVisible() {
//        onRefresh();
    }

    @Override
    public void onRefresh() {
        String mUrl = "photos";
        refreshHandler.refresh(mUrl, mParams);
    }

    @Override
    public void onLoadMore() {
        refreshHandler.loadMore();
    }
}
