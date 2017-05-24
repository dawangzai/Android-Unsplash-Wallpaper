package com.cleverzheng.wallpaper.ui.newest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cleverzheng.wallpaper.R;
import com.cleverzheng.wallpaper.base.ViewPagerFragment;
import com.cleverzheng.wallpaper.listener.RVOnScrollListener;
import com.cleverzheng.wallpaper.ui.adapter.NewestListAdapter;
import com.cleverzheng.wallpaper.base.BaseFragment;
import com.cleverzheng.wallpaper.bean.PhotoBean;
import com.cleverzheng.wallpaper.global.Constant;
import com.cleverzheng.wallpaper.ui.test.TestActivity;
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
 * Created by wangzai on 2017/2/12.
 */
public class NewestFragment extends ViewPagerFragment implements NewestContract.View {

    @BindView(R.id.rvNewest)
    RecyclerView rvNewest;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    private NewestContract.Presenter mPresenter;

    private NewestListAdapter mAdapter;
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.i(getTAG(), "------fragment------onCreateView------");
        View view = inflater.inflate(R.layout.fragment_newest, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setPresent(NewestContract.Presenter present) {
        if (present != null) {
            this.mPresenter = present;
        }
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        LogUtil.i(getTAG(), "------newestfragment------onFragmentVisibleChange------" + isVisible);
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            showLoadingView();
//            mPresenter.start();
            Intent intent = new Intent(getActivity(), TestActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
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
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPresenter.refreshData(1, Constant.PER_PAGE);
            }
        });
    }

    @Override
    public void refresh(List<PhotoBean> photoList) {
        hideLoadingView();
        refreshLayout.refreshComplete();
        PhotoBean photoBean = photoList.get(0);
        String id = photoBean.getId();
        if (!StringUtil.isEmpty(firstRefreshPhotoId)
                && StringUtil.equals(id, firstRefreshPhotoId)) {
            firstRefreshPhotoId = id;
            return;
        } else {
            if (mAdapter == null) {
                mAdapter = new NewestListAdapter(this);
                layoutManager = new LinearLayoutManager(getActivity());
                rvNewest.setLayoutManager(layoutManager);
                rvNewest.setAdapter(mAdapter);
            }
            mAdapter.refreshData(photoList);
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
