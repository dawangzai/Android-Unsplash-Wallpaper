package com.wangzai.lovesy.core.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wangzai.lovesy.core.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wangzai on 2017/9/16
 */

public abstract class BaseFragment extends Fragment {

    protected View rootView;
    private boolean isAlreadyVisible = false;

    private Unbinder mUnBinder;

    @Nullable
    @BindView(R2.id.toolbar_fragment)
    Toolbar toolbar;

    public abstract Object setLayout();

    public abstract void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (setLayout() instanceof Integer) {
            rootView = inflater.inflate((int) setLayout(), container, false);
        } else if (setLayout() instanceof View) {
            rootView = (View) setLayout();
        } else {
            throw new ClassCastException("type of setLayout() must be int or View!");
        }
        mUnBinder = ButterKnife.bind(this, rootView);
        initToolbar();
        onBindView(savedInstanceState, rootView);

        return rootView;
    }

    private void initToolbar() {
        if (toolbar != null) {
            AppCompatActivity mActivity = (AppCompatActivity) getActivity();
            mActivity.setSupportActionBar(toolbar);
            final ActionBar actionBar = mActivity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
            }
        }
    }

    public void setTitle(@StringRes int title) {
        setTitle(getString(title));
    }

    public void setTitle(String title) {
        if (toolbar != null) {
            toolbar.setTitle(title);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getUserVisibleHint()) {
            onLazyLoad();
            isAlreadyVisible = true;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (rootView == null) {
            return;
        }
        if (!isAlreadyVisible) {
            if (isVisibleToUser) {
                onLazyLoad();
            }
            isAlreadyVisible = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
    }

    protected void onLazyLoad() {
    }

}
