package com.wangzai.lovesy.user;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wangzai.lovesy.Constant;
import com.wangzai.lovesy.R;
import com.wangzai.lovesy.core.fragment.user.BaseTabItemFragment;
import com.wangzai.lovesy.core.fragment.user.BaseTabPageFragment;
import com.wangzai.lovesy.core.fragment.user.TabBuilder;
import com.wangzai.lovesy.user.collection.CollectionFragment;
import com.wangzai.lovesy.user.download.DownloadFragment;
import com.wangzai.lovesy.user.like.LikeFragment;

import java.util.LinkedHashMap;

/**
 * Created by wangzai on 2017/10/10
 */

public class UserProfileFragment extends BaseTabPageFragment {

    @Override
    protected int setIndexPage() {
        return 0;
    }

    @Override
    protected int setIndicatorColor() {
        return R.color.colorAccent;
    }

    @Override
    protected int setTabBackgroundColor() {
        return R.color.bg_white;
    }

    @Override
    protected LinkedHashMap<String, BaseTabItemFragment> setTabs(TabBuilder builder) {
        builder.addTab("下载", new DownloadFragment());
        builder.addTab("喜欢", new LikeFragment());
        builder.addTab("影集", new CollectionFragment());
        return builder.build();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        super.onBindView(savedInstanceState, rootView);
        final Bundle bundle = getArguments();
        final String title = bundle.getString(Constant.INTENT_DATA.ONE);
        AppCompatActivity mActivity = (AppCompatActivity) getActivity();
        final ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }
}
