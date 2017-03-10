package com.cleverzheng.wallpaper.ui.persondetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cleverzheng.wallpaper.R;
import com.cleverzheng.wallpaper.base.BaseFragment;
import com.cleverzheng.wallpaper.bean.CollectionBean;
import com.cleverzheng.wallpaper.bean.PhotoBean;
import com.cleverzheng.wallpaper.ui.adapter.PersonCollectionsAdapter;
import com.cleverzheng.wallpaper.ui.adapter.PersonPhotosAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author：cleverzheng
 * @date：2017/3/7:11:43
 * @email：zhengwang043@gmail.com
 * @description：
 */

public class PersonCollectionFragment extends BaseFragment {
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
        setContentView(R.layout.fragment_person_collection);
        ButterKnife.bind(this, getContentView());
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
