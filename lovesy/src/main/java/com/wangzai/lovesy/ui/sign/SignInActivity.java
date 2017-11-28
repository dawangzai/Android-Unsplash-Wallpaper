package com.wangzai.lovesy.ui.sign;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wangzai.lovesy.R;
import com.wangzai.lovesy.api.ApiService;
import com.wangzai.lovesy.core.activity.LoveSyActivity;

/**
 * Created by wangzai on 2017/7/11
 */

public class SignInActivity extends LoveSyActivity {

    @Override
    public int setLayout() {
        return R.layout.activity_web;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        loadRootFragment(savedInstanceState, R.id.fragment_container, SignInFragment.create(ApiService.Oauth.OAUTH_AUTHORIZE));
    }
}
