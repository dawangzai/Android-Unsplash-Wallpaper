package com.wangzai.lovesy.detail.collection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wangzai.lovesy.Constant;
import com.wangzai.lovesy.R;
import com.wangzai.lovesy.api.ApiService;
import com.wangzai.lovesy.bean.CollectionBean;
import com.wangzai.lovesy.bean.ProfileImageBean;
import com.wangzai.lovesy.bean.UserBean;
import com.wangzai.lovesy.core.activity.LoveSyActivity;
import com.wangzai.lovesy.core.net.rx.RxHttpClient;
import com.wangzai.lovesy.core.net.rx.observer.ResultObserver;
import com.wangzai.lovesy.core.ui.image.loader.ImageLoader;
import com.wangzai.lovesy.core.ui.recycler.DataConverter;
import com.wangzai.lovesy.core.ui.recycler.MultipleRecyclerAdapter;
import com.wangzai.lovesy.core.ui.refresh.PagingBean;
import com.wangzai.lovesy.core.widget.SimpleImageView;
import com.wangzai.lovesy.detail.AppBarLayoutStateChangeListener;
import com.wangzai.lovesy.home.index.PhotoDataConvert;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;

/**
 * Created by wangzai on 2017/10/24
 */

public class CollectionDetailActivity extends LoveSyActivity implements
        BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_collection_name)
    TextView mTvCollectionName;
    @BindView(R.id.tv_collection_desc)
    TextView mTvCollectionDesc;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.siv_avatar)
    SimpleImageView mSivAvatar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.appBarLayout)
    AppBarLayout mAppBarLayout;

    private CollectionBean collectionBean;
    private int collectionId;

    private PagingBean mPagingBean;
    private DataConverter mConvert;
    private MultipleRecyclerAdapter mAdapter;
    private LinearLayout mErrorView;
    private LinearLayout mItemTitle;
    private RelativeLayout mEmptyView;

    @Override
    public int setLayout() {
        return R.layout.activity_collection_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        final Bundle bundle = getIntent().getExtras();
        String extra = bundle.getString(Constant.BUNDLE.ONE);
        if (extra != null) {
            collectionBean = JSON.parseObject(extra, CollectionBean.class);
            setCollectionData();
            mAppBarLayout.addOnOffsetChangedListener(new AppBarLayoutStateChangeListener(mCollapsingToolbarLayout));
        }
        initRecyclerView();
    }

    private void setCollectionData() {
        collectionId = collectionBean.getId();
        final UserBean user = collectionBean.getUser();
        final String username = user.getUsername();
        final ProfileImageBean profile_image = user.getProfile_image();

        mCollapsingToolbarLayout.setTitle(collectionBean.getTitle());
        mTvCollectionName.setText(collectionBean.getTitle());
        final String description = collectionBean.getDescription();
        if (description != null) {
            mTvCollectionDesc.setText(description);
        }
        mTvUserName.setText(username);
        ImageLoader.loaderImage(mSivAvatar, profile_image.getLarge());
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mConvert = new PhotoDataConvert();
        mAdapter = MultipleRecyclerAdapter.create();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.disableLoadMoreIfNotFullPage();
        mErrorView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.view_error, null);
        mEmptyView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.view_empty, null);
        mItemTitle = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.view_item_title, null);
        mAdapter.setHeaderView(mItemTitle);
        mErrorView.setOnClickListener(this);
        mPagingBean = new PagingBean();
        loadData();
    }

    @Override
    public void onLoadMoreRequested() {
        loadData();
    }

    @Override
    public void onClick(View v) {

    }

    public void loadData() {
        final int pageIndex = mPagingBean.getPageIndex();
        RxHttpClient.builder()
                .url(ApiService.Collections.COLLECTIONS + collectionId + "/" + ApiService.Photos.PHOTOS)
                .params("page", pageIndex)
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
}
