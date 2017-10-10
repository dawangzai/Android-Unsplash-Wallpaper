package com.wangzai.lovesy.core.fragment.user;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.wangzai.lovesy.core.R;
import com.wangzai.lovesy.core.R2;
import com.wangzai.lovesy.core.fragment.LoveSyFragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by wangzai on 2017/10/10
 */

public abstract class BaseTabPageFragment extends LoveSyFragment {

    //页面数据集合
    private final LinkedHashMap<String, BaseTabItemFragment> mTabs = new LinkedHashMap<>();
    private final ArrayList<String> mTabTitle = new ArrayList<>();
    private final ArrayList<BaseTabItemFragment> mTabFragments = new ArrayList<>();

    private int mIndexPage = 0;
    private int mCurrentPge = 0;
    private int mIndicatorColor = Color.RED;

    protected abstract int setIndexPage();

    @ColorInt
    protected abstract int setIndicatorColor();

    @BindView(R2.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R2.id.view_pager)
    ViewPager mViewPager;

    protected abstract LinkedHashMap<String, BaseTabItemFragment> setTabs(TabBuilder builder);

    @Override
    public Object setLayout() {
        return R.layout.fragment_base_tab_page;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mIndexPage = setIndexPage();
        if (setIndicatorColor() != 0) {
            mIndicatorColor = setIndicatorColor();
        }

        //初始化数据
        TabBuilder builder = new TabBuilder();
        final LinkedHashMap<String, BaseTabItemFragment> tabs = setTabs(builder);
        mTabs.putAll(tabs);
        for (Map.Entry<String, BaseTabItemFragment> tab : tabs.entrySet()) {
            final String key = tab.getKey();
            final BaseTabItemFragment value = tab.getValue();

            mTabTitle.add(key);
            mTabFragments.add(value);
        }

        //初始化 ViewPager
        TabPagerAdapter adapter = new TabPagerAdapter(mTabTitle, mTabFragments, getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(mIndexPage);
//        mViewPager.addOnPageChangeListener(this);

        //初始化 TabLayout
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setSelectedTabIndicatorColor(mIndicatorColor);
        mTabLayout.setSelectedTabIndicatorHeight(10);

        //关联 ViewPager和TabLayout
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
