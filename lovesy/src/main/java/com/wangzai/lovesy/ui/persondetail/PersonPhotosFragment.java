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
import com.wangzai.lovesy.bean.PhotoBean;
import com.wangzai.lovesy.mvp.global.Constant;
import com.wangzai.lovesy.ui.adapter.NewestListAdapter;
import com.wangzai.lovesy.utils.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangzai on 2017/3/7.
 */

public class PersonPhotosFragment extends ViewPagerFragment {
    private PersonDetailContract.Presenter mPresent;
    @BindView(R.id.rvPersonPhoto)
    RecyclerView rvPersonPhoto;
    private LinearLayoutManager layoutManager;
    private NewestListAdapter mAdapter;

    public static PersonPhotosFragment getInstances() {
        PersonPhotosFragment fragment = new PersonPhotosFragment();
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
        View view = inflater.inflate(R.layout.fragment_person_photos, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        LogUtil.i("PersonPhotosFragment", "------PersonCollectionFragment------onFragmentVisibleChange------" + isVisible);
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            if (mPresent != null) {
                mPresent.getPersonPhotos();
            }
        }
    }

    @Override
    public void initData() {
        super.initData();

        mAdapter = new NewestListAdapter(Constant.PhotoListAdapterType.PHOTO_DETAIL,this);
//        mAdapter = new PersonPhotosAdapter(this);
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
