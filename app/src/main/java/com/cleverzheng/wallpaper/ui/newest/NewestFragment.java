package com.cleverzheng.wallpaper.ui.newest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cleverzheng.wallpaper.R;
import com.cleverzheng.wallpaper.base.ViewPagerFragment;
import com.cleverzheng.wallpaper.listener.RVOnScrollListener;
import com.cleverzheng.wallpaper.ui.adapter.NewestListAdapter;
import com.cleverzheng.wallpaper.base.BaseFragment;
import com.cleverzheng.wallpaper.bean.PhotoBean;
import com.cleverzheng.wallpaper.global.Constant;
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
 * @author：cleverzheng
 * @date：2017/2/12:11:00
 * @email：zhengwang043@gmail.com
 * @description：最新页面View的实现类
 */

public class NewestFragment extends ViewPagerFragment implements NewestContract.View {

    @BindView(R.id.rvNewest)
    RecyclerView rvNewest;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
//    MyRefreshLayout refreshLayout;

    private NewestContract.Presenter mPresenter;

    private NewestListAdapter mAdapter;
    private RecyclerOnScrollListener scrollListener;
    private LinearLayoutManager layoutManager;
    private int page = 1; //记录页数
    private String firstRefreshPhotoId;

    public static NewestFragment getInstance() {
        NewestFragment fragment = new NewestFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_newest);
        ButterKnife.bind(this, getContentView());
    }

    @Override
    public void setPresent(NewestContract.Presenter present) {
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
    public void onResume() {
        super.onResume();
    }

    @Override
    public void initData() {
        super.initData();
        mAdapter = new NewestListAdapter(this);
        layoutManager = new LinearLayoutManager(getActivity());
        rvNewest.setLayoutManager(layoutManager);
        rvNewest.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        refreshLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                page = page + 1;
                mPresenter.loadMoreData(page, Constant.PER_PAGE);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPresenter.refreshData(1, Constant.PER_PAGE);
            }
        });
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshLayout.setRefreshing(true);
//                mPresenter.refreshData(1, Constant.PER_PAGE);
//            }
//        });
//
//        scrollListener = new RecyclerOnScrollListener(layoutManager) {
//            @Override
//            public void onLoadMore() {
//                page = page + 1;
//                mPresenter.loadMoreData(page, Constant.PER_PAGE);
//                LogUtil.i("cd", "wai");
//                setLoading(true);
//            }
//        };
//        rvNewest.addOnScrollListener(scrollListener);
    }

    @Override
    public void refresh(List<PhotoBean> photoList) {
        refreshLayout.refreshComplete();
        dismissLoading();
        PhotoBean photoBean = photoList.get(0);
        String id = photoBean.getId();
        if (!StringUtil.isEmpty(firstRefreshPhotoId)
                && StringUtil.equals(id, firstRefreshPhotoId)) {
            firstRefreshPhotoId = id;
            return;
        } else {
            if (mAdapter != null) {
                mAdapter.refreshData(photoList);
            }
            firstRefreshPhotoId = id;
        }
    }

    @Override
    public void loadMore(List<PhotoBean> photoList) {
        refreshLayout.refreshComplete();
        if (mAdapter != null) {
            mAdapter.loadMoreData(photoList);
        }
    }

    @Override
    public void clickPhotoDetail(String photoId) {
        mPresenter.openPhotoDetail(photoId);
    }

    @Override
    public void clickUserDetail(String username) {
        mPresenter.openUserDetail(username);
    }
}
