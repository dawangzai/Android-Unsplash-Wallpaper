package com.wangzai.lovesy.user;

import android.os.Bundle;

import com.wangzai.lovesy.Constant;
import com.wangzai.lovesy.R;
import com.wangzai.lovesy.core.fragment.BaseRefreshFragment;
import com.wangzai.lovesy.core.fragment.user.BaseTabPageFragment;
import com.wangzai.lovesy.core.fragment.user.TabBuilder;
import com.wangzai.lovesy.user.collection.UserCollectionFragment;
import com.wangzai.lovesy.user.photo.UserPhotoFragment;
import com.wangzai.lovesy.user.like.UserLikeFragment;

import java.util.LinkedHashMap;

/**
 * Created by wangzai on 2017/10/10
 */

public class UserProfileFragment extends BaseTabPageFragment {

    @Override
    protected int setIndexPage() {
        return getArguments().getInt(Constant.BUNDLE.TWO);
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
    protected LinkedHashMap<String, BaseRefreshFragment> setTabs(TabBuilder builder) {
        final Bundle bundle = getArguments();

        final UserPhotoFragment userPhotoFragment = new UserPhotoFragment();
        final UserLikeFragment userLikeFragment = new UserLikeFragment();
        final UserCollectionFragment userCollectionFragment = new UserCollectionFragment();
        userPhotoFragment.setArguments(bundle);
        userLikeFragment.setArguments(bundle);
        userCollectionFragment.setArguments(bundle);

        builder.addTab(getString(R.string.text_photo), userPhotoFragment);
        builder.addTab(getString(R.string.text_like), userLikeFragment);
        builder.addTab(getString(R.string.text_album), userCollectionFragment);
        return builder.build();
    }
}
