package com.wangzai.lovesy.core.fragment.user;

import java.util.LinkedHashMap;

/**
 * Created by wangzai on 2017/10/10
 */

public class TabBuilder {

    private final LinkedHashMap<String, BaseTabItemFragment> mTabs = new LinkedHashMap<>();

    static TabBuilder builder() {
        return new TabBuilder();
    }

    public final TabBuilder addTab(String tabItemTitle, BaseTabItemFragment tabItemFragment) {
        mTabs.put(tabItemTitle, tabItemFragment);
        return this;
    }

    public final LinkedHashMap<String, BaseTabItemFragment> build() {
        return mTabs;
    }
}
