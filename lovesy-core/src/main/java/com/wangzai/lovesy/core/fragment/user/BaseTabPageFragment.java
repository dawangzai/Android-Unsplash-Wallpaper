package com.wangzai.lovesy.core.fragment.user;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.wangzai.lovesy.core.R;
import com.wangzai.lovesy.core.R2;
import com.wangzai.lovesy.core.fragment.BaseRefreshFragment;
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
    private final LinkedHashMap<String, BaseRefreshFragment> mTabs = new LinkedHashMap<>();
    private final ArrayList<String> mTabTitle = new ArrayList<>();
    private final ArrayList<BaseRefreshFragment> mTabFragments = new ArrayList<>();

    private int mIndexPage = 0;
    private int mCurrentPge = 0;

    @ColorRes
    private int mIndicatorColor = R.color.colorAccent;

    protected abstract int setIndexPage();

    @ColorRes
    protected abstract int setIndicatorColor();

    @ColorRes
    protected abstract int setTabBackgroundColor();

    @BindView(R2.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R2.id.view_pager)
    ViewPager mViewPager;

    protected abstract LinkedHashMap<String, BaseRefreshFragment> setTabs(TabBuilder builder);

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
        final LinkedHashMap<String, BaseRefreshFragment> tabs = setTabs(builder);
        mTabs.putAll(tabs);
        for (Map.Entry<String, BaseRefreshFragment> tab : tabs.entrySet()) {
            final String key = tab.getKey();
            final BaseRefreshFragment value = tab.getValue();

            mTabTitle.add(key);
            mTabFragments.add(value);
        }

        //初始化 ViewPager
        TabPagerAdapter adapter = new TabPagerAdapter(mTabTitle, mTabFragments, getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(mIndexPage);

        //初始化 TabLayout
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setSelectedTabIndicatorHeight(7);
        mTabLayout.setBackgroundResource(setTabBackgroundColor());
        mTabLayout.setTabTextColors(ContextCompat.getColor(getActivity(), android.R.color.tertiary_text_light), ContextCompat.getColor(getActivity(), mIndicatorColor));

        //关联 ViewPager和TabLayout
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
