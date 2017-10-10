package com.wangzai.lovesy.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.wangzai.lovesy.R;
import com.wangzai.lovesy.core.activity.LoveSyActivity;

import butterknife.BindView;

/**
 * Created by wangzai on 2017/10/10
 */

public class UserProfileActivity extends LoveSyActivity {

    @Override
    public int setLayout() {
        return R.layout.activity_user_profile;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        loadRootFragment(savedInstanceState, R.id.fragment_container, new UserProfileFragment());
    }
}
