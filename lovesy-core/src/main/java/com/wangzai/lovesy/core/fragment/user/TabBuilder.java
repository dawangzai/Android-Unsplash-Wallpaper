package com.wangzai.lovesy.core.fragment.user;

import com.wangzai.lovesy.core.fragment.BaseRefreshFragment;

import java.util.LinkedHashMap;

/**
 * Created by wangzai on 2017/10/10
 */

public class TabBuilder {

    private final LinkedHashMap<String, BaseRefreshFragment> mTabs = new LinkedHashMap<>();

    static TabBuilder builder() {
        return new TabBuilder();
    }

    public final TabBuilder addTab(String tabItemTitle, BaseRefreshFragment tabItemFragment) {
        mTabs.put(tabItemTitle, tabItemFragment);
        return this;
    }

    public final LinkedHashMap<String, BaseRefreshFragment> build() {
        return mTabs;
    }
}
