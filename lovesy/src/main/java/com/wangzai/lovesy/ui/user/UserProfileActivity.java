package com.wangzai.lovesy.ui.user;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wangzai.lovesy.R;
import com.wangzai.lovesy.core.activity.LoveSyActivity;

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

        setTitle(getString(R.string.text_user));

        final UserProfileFragment userProfileFragment = new UserProfileFragment();
        userProfileFragment.setArguments(getIntent().getExtras());
        loadRootFragment(savedInstanceState, R.id.fragment_container, userProfileFragment);
    }

}
