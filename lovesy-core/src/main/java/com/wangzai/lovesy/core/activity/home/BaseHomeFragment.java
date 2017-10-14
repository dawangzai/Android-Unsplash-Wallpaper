package com.wangzai.lovesy.core.activity.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wangzai.lovesy.core.R;
import com.wangzai.lovesy.core.fragment.LoveSyFragment;

/**
 * Created by wangzai on 2017/9/21
 */

public abstract class BaseHomeFragment extends LoveSyFragment {

    protected ActionBar mActionBar;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initTitle();
        setHasOptionsMenu(true);
    }

    private void initTitle() {
        AppCompatActivity mActivity = (AppCompatActivity) getActivity();
        mActionBar = mActivity.getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(false);
            mActionBar.setDisplayShowTitleEnabled(true);
            mActionBar.setTitle(getString(R.string.app_name));
        }
    }
}
