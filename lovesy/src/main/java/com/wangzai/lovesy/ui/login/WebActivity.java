package com.wangzai.lovesy.ui.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wangzai.lovesy.R;
import com.wangzai.lovesy.core.activity.LoveSyActivity;

/**
 * Created by wangzai on 2017/9/18
 */

public class WebActivity extends LoveSyActivity {

    @Override
    public int setLayout() {
        return R.layout.activity_web;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        loadRootFragment(savedInstanceState, R.id.fragment_container, LoginFragment.create("http://dawangzai.com"));
    }
}
