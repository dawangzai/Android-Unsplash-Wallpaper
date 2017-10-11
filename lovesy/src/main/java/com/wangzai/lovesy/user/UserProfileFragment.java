package com.wangzai.lovesy.user;

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
        return 0;
    }

    @Override
    protected LinkedHashMap<String, BaseTabItemFragment> setTabs(TabBuilder builder) {
        builder.addTab("下载", new DownloadFragment());
        builder.addTab("喜欢", new LikeFragment());
        builder.addTab("影集", new CollectionFragment());
        return builder.build();
    }
}
