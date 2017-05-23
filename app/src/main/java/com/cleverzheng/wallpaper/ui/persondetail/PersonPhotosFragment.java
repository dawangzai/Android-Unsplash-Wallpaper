package com.cleverzheng.wallpaper.ui.persondetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cleverzheng.wallpaper.R;
import com.cleverzheng.wallpaper.base.BaseFragment;
import com.cleverzheng.wallpaper.bean.PhotoBean;
import com.cleverzheng.wallpaper.network.Network;
import com.cleverzheng.wallpaper.ui.adapter.PersonPhotosAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author：cleverzheng
 * @date：2017/3/7:11:42
 * @email：zhengwang043@gmail.com
 * @description：个人照片
 */

public class PersonPhotosFragment extends BaseFragment {
    private PersonDetailContract.Presenter mPresent;
    @BindView(R.id.rvPersonPhoto)
    RecyclerView rvPersonPhoto;
    private LinearLayoutManager layoutManager;
    private PersonPhotosAdapter mAdapter;

    public static PersonPhotosFragment getInstances() {
        PersonPhotosFragment fragment = new PersonPhotosFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_person_photos);
//        ButterKnife.bind(this, getContentView());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_photos, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        mAdapter = new PersonPhotosAdapter(this);
        layoutManager = new LinearLayoutManager(getActivity());
        rvPersonPhoto.setLayoutManager(layoutManager);
        rvPersonPhoto.setAdapter(mAdapter);
    }

    public void refreshViewPager(List<PhotoBean> personPhotos) {
        mAdapter.refreshData(personPhotos);
    }

    @Override
    public void initListener() {
        super.initListener();
    }
}
