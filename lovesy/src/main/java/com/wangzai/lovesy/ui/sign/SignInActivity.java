package com.wangzai.lovesy.ui.sign;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wangzai.lovesy.R;
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
        String authUrl = "https://unsplash.com/oauth/authorize?client_id=b05bfc46a0de4842346cb5ce7c766b3a8c9da071ec77f3b5f719406829c2fb31&redirect_uri=http://dawangzai.com&response_type=code&scope=public+read_user+write_user+read_collections+write_collections";
        loadRootFragment(savedInstanceState, R.id.fragment_container, SignInFragment.create(authUrl));
    }
}
