package com.cleverzheng.wallpaper.ui.collection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.cleverzheng.wallpaper.R;
import com.cleverzheng.wallpaper.base.BaseFragment;
import com.cleverzheng.wallpaper.base.ViewPagerFragment;
import com.cleverzheng.wallpaper.bean.CollectionBean;
import com.cleverzheng.wallpaper.bean.PhotoBean;
import com.cleverzheng.wallpaper.global.Constant;
import com.cleverzheng.wallpaper.ui.adapter.CollectionListAdapter;
import com.cleverzheng.wallpaper.utils.LogUtil;
import com.cleverzheng.wallpaper.utils.StringUtil;
import com.cleverzheng.wallpaper.view.RecyclerOnScrollListener;
import com.cleverzheng.wallpaper.view.layout.MyRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author：cleverzheng
 * @date：2017/2/21:17:59
 * @email：zhengwang043@gmail.com
 * @description：
 */

public class CollectionFragment extends ViewPagerFragment implements CollectionContract.View {
    @BindView(R.id.rvCollection)
    RecyclerView rvCollection;
    @BindView(R.id.refreshLayout)
    MyRefreshLayout refreshLayout;
    private CollectionContract.Presenter mPresenter;

    private CollectionListAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private RecyclerOnScrollListener recyclerOnScrollListener;
    private int firstRefreshCollectionId = -1;
    private int page = 1;

    public static CollectionFragment getInstance() {
        CollectionFragment collectionFragment = new CollectionFragment();
        return collectionFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_collection);
        ButterKnife.bind(this, getContentView());
    }

    @Override
    public void setPresent(CollectionContract.Presenter present) {
        if (present != null) {
            this.mPresenter = present;
//            mPresenter.start();
        }
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        mPresenter.start();
    }

    @Override
    public void initData() {
        super.initData();
        adapter = new CollectionListAdapter(this);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvCollection.setLayoutManager(gridLayoutManager);
        rvCollection.setAdapter(adapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshData(1, Constant.PER_PAGE);
            }
        });

        recyclerOnScrollListener = new RecyclerOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore() {
                page = page + 1;
                mPresenter.loadMoreData(page, Constant.PER_PAGE);
                LogUtil.i("cd", "wai");
                setLoading(true);
            }
        };

        rvCollection.addOnScrollListener(recyclerOnScrollListener);
    }

    @Override
    public void refresh(List<CollectionBean> collectionBeanList) {
        refreshLayout.setRefreshing(false);
        CollectionBean collectionBean = collectionBeanList.get(0);
        int id = collectionBean.getId();
        if (firstRefreshCollectionId >= 0 && id == firstRefreshCollectionId) {
            firstRefreshCollectionId = id;
            return;
        } else {
            if (adapter != null) {
                adapter.refreshData(collectionBeanList);
            }
            firstRefreshCollectionId = id;
        }
    }

    @Override
    public void loadMore(List<CollectionBean> collectionBeanList) {
        if (collectionBeanList != null && collectionBeanList.size() > 0) {
            recyclerOnScrollListener.setLoading(false);
            adapter.loadMoreData(collectionBeanList);
        }
    }
}
