package com.wangzai.lovesy.user.like;

import android.os.Bundle;

import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.wangzai.lovesy.Constant;
import com.wangzai.lovesy.api.ApiService;
import com.wangzai.lovesy.core.fragment.BaseRefreshFragment;
import com.wangzai.lovesy.core.ui.recycler.DataConverter;
import com.wangzai.lovesy.home.index.PhotoDataConvert;

/**
 * Created by wangzai on 2017/10/10
 */

public class UserLikeFragment extends BaseRefreshFragment {

    @Override
    protected String setUrl() {
        final Bundle bundle = getArguments();
        final String username = bundle.getString(Constant.BUNDLE.ONE);
        return ApiService.User.USERS + username + "/" + ApiService.User.USERS_LIKES;
    }

    @Override
    protected DataConverter setDataConverter() {
        return new PhotoDataConvert();
    }

    @Override
    protected SimpleClickListener addItemClickListener() {
        return new UserLikeItemClickListener(this);
    }
}
