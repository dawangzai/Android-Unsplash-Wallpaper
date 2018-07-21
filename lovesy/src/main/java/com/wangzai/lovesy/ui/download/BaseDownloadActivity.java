package com.wangzai.lovesy.ui.download;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.wangzai.lovesy.R;
import com.wangzai.lovesy.core.activity.LoveSyActivity;
import com.wangzai.lovesy.core.fragment.LoveSyFragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangzai on 2018/2/1
 */

public abstract class BaseDownloadActivity extends LoveSyActivity {

    private final LinkedHashMap<String, LoveSyFragment> mItems = new LinkedHashMap<>();
    private final ArrayList<String> mTabs = new ArrayList<>();
    private final ArrayList<LoveSyFragment> mFragments = new ArrayList<>();
    private int mIndexPage = 0;
    private int mClickedColor = R.color.colorAccent;

    @BindView(R.id.tab_Layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @ColorRes
    public abstract int setClickedColor();

    public abstract int setIndexPage();

    public abstract LinkedHashMap<String, LoveSyFragment> setData(DataBuilder<String, LoveSyFragment> builder);

    @Override
    public int setLayout() {
        return R.layout.activity_base_download;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        setTitle("下载管理");

        // 构造数据
        mIndexPage = setIndexPage();
        if (setClickedColor() != 0) {
            mClickedColor = setClickedColor();
        }
        final DataBuilder<String, LoveSyFragment> builder = new DataBuilder<>();
        final LinkedHashMap data = setData(builder);
        mItems.putAll(data);

        for (Map.Entry<String, LoveSyFragment> map : mItems.entrySet()) {
            final String key = map.getKey();
            final LoveSyFragment value = map.getValue();
            mTabs.add(key);
            mFragments.add(value);
        }

        // 初始化 ViewPager
        DownloadPagerAdapter adapter = new DownloadPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(mIndexPage);

        // 初始化 TabLayout
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setSelectedTabIndicatorHeight(7);
        mTabLayout.setTabTextColors(ContextCompat.getColor(this, android.R.color.tertiary_text_light), ContextCompat.getColor(this, mClickedColor));
    }

}
