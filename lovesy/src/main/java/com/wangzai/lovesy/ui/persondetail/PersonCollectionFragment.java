package com.wangzai.lovesy.ui.persondetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wangzai.lovesy.R;
import com.wangzai.lovesy.mvp.base.ViewPagerFragment;
import com.wangzai.lovesy.bean.CollectionBean;
import com.wangzai.lovesy.ui.adapter.PersonCollectionsAdapter;
import com.wangzai.lovesy.utils.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangzai on 2017/3/7.
 */

public class PersonCollectionFragment extends ViewPagerFragment {
    private PersonDetailContract.Presenter mPresent;
    @BindView(R.id.rvPersonCollections)
    RecyclerView rvPersonCollections;
    private PersonCollectionsAdapter mAdapter;
    private LinearLayoutManager layoutManager;

    public static PersonCollectionFragment getInstances() {
        PersonCollectionFragment fragment = new PersonCollectionFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setPresent(PersonDetailContract.Presenter present) {
        this.mPresent = present;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_collection, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        LogUtil.i("viewpagerfragment", "------PersonCollectionFragment------onFragmentVisibleChange------" + isVisible);
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            if (mPresent != null) {
                mPresent.getPersonCollections();
            }
        }
    }

    @Override
    public void initData() {
        super.initData();
        mAdapter = new PersonCollectionsAdapter(this);
        layoutManager = new LinearLayoutManager(getActivity());
        rvPersonCollections.setLayoutManager(layoutManager);
        rvPersonCollections.setAdapter(mAdapter);
    }

    public void refreshViewPager(List<CollectionBean> collectionBeanList) {
        mAdapter.refreshData(collectionBeanList);
    }

    @Override
    public void initListener() {
        super.initListener();
    }
}
