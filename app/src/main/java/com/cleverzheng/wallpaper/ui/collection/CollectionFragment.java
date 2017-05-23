package com.cleverzheng.wallpaper.ui.collection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.cleverzheng.wallpaper.view.layout.RefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by wangzai on 2017/2/21.
 */
public class CollectionFragment extends ViewPagerFragment implements CollectionContract.View {
    @BindView(R.id.rvCollection)
    RecyclerView rvCollection;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    private CollectionContract.Presenter mPresenter;

    private CollectionListAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private int firstRefreshCollectionId = -1;
    private int page = 1;

    public static CollectionFragment getInstance() {
        CollectionFragment collectionFragment = new CollectionFragment();
        return collectionFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setPresent(CollectionContract.Presenter present) {
        if (present != null) {
            this.mPresenter = present;
        }
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        LogUtil.i(getTAG(), "------collectionfragment------onFragmentVisibleChange------"+isVisible);
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            showLoadingView();
            mPresenter.start();
        }
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initListener() {
        super.initListener();
        refreshLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                page = page + 1;
                mPresenter.loadMoreData(page, Constant.PER_PAGE);
                LogUtil.i("cd", "wai");
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPresenter.refreshData(1, Constant.PER_PAGE);
            }
        });
    }

    @Override
    public void refresh(List<CollectionBean> collectionBeanList) {
        hideLoadingView();
        refreshLayout.refreshComplete();
        CollectionBean collectionBean = collectionBeanList.get(0);
        int id = collectionBean.getId();
        if (firstRefreshCollectionId >= 0 && id == firstRefreshCollectionId) {
            firstRefreshCollectionId = id;
            return;
        } else {
            if (adapter == null) {
                adapter = new CollectionListAdapter(this);
                gridLayoutManager = new GridLayoutManager(getActivity(), 2);
                rvCollection.setLayoutManager(gridLayoutManager);
                rvCollection.setAdapter(adapter);
            }
            adapter.refreshData(collectionBeanList);
            firstRefreshCollectionId = id;
        }
    }

    @Override
    public void loadMore(List<CollectionBean> collectionBeanList) {
        if (collectionBeanList != null && collectionBeanList.size() > 0) {
            adapter.loadMoreData(collectionBeanList);
        }
    }
}
