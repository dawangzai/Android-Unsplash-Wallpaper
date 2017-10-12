package com.wangzai.lovesy.core.activity.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.joanzapata.iconify.widget.IconTextView;
import com.wangzai.lovesy.core.R;
import com.wangzai.lovesy.core.R2;
import com.wangzai.lovesy.core.activity.LoveSyActivity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;

import static android.R.attr.tag;

/**
 * Created by wangzai on 2017/9/21
 */

public abstract class BaseHomeActivity extends LoveSyActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private final ArrayList<BottomTabBean> mTabBeans = new ArrayList<>();
    private final ArrayList<BaseHomeFragment> mHomeFragments = new ArrayList<>();
    private final LinkedHashMap<BottomTabBean, BaseHomeFragment> mItems = new LinkedHashMap<>();

    private int mCurrentPage = 0;
    private int mClickedColor = R.color.colorPrimary;

    @BindView(R2.id.vp_container)
    ViewPager mVpContainer;
    @BindView(R2.id.bottom_bar)
    LinearLayoutCompat mBottomBar;

    protected abstract LinkedHashMap<BottomTabBean, BaseHomeFragment> setItems(ItemBuilder builder);

    protected abstract int setIndexFragment();

    @ColorRes
    protected abstract int setClickedColor();

    @Override
    public int setLayout() {
        return R.layout.activity_base_home;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        int mIndexPage = setIndexFragment();
        if (setClickedColor() != 0) {
            mClickedColor = setClickedColor();
        }

        //初始化数据
        final ItemBuilder builder = new ItemBuilder();
        final LinkedHashMap<BottomTabBean, BaseHomeFragment> items = setItems(builder);
        mItems.putAll(items);
        for (Map.Entry<BottomTabBean, BaseHomeFragment> item : mItems.entrySet()) {
            final BottomTabBean key = item.getKey();
            final BaseHomeFragment value = item.getValue();
            mTabBeans.add(key);
            mHomeFragments.add(value);
        }

        //初始化底部导航
        int size = mTabBeans.size();
        for (int i = 0; i < size; i++) {
            LayoutInflater.from(this).inflate(R.layout.item_bottom_tab, mBottomBar);
            RelativeLayout bottomItem = (RelativeLayout) mBottomBar.getChildAt(i);
            bottomItem.setTag(i);
            final IconTextView itvBottomIcon = (IconTextView) bottomItem.getChildAt(0);
            final AppCompatTextView tvBottomTitle = (AppCompatTextView) bottomItem.getChildAt(1);
            final BottomTabBean bean = mTabBeans.get(i);
            itvBottomIcon.setText(bean.getIcon());
            tvBottomTitle.setText(bean.getTitle());
            bottomItem.setOnClickListener(this);
            if (i == mIndexPage) {
                itvBottomIcon.setTextColor(ContextCompat.getColor(this, mClickedColor));
                tvBottomTitle.setTextColor(ContextCompat.getColor(this, mClickedColor));
            }
        }

        //初始化 ViewPager
        HomePagerAdapter adapter = new HomePagerAdapter(mHomeFragments, getSupportFragmentManager());
        mVpContainer.setAdapter(adapter);
        mVpContainer.setCurrentItem(mIndexPage);
        mVpContainer.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPage = position;
        bottomTabClick(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        final int tag = (int) v.getTag();
        bottomTabClick(tag);
    }

    private void bottomTabClick(int position) {
        resetColor();
        final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(position);
        final IconTextView itvBottomIcon = (IconTextView) item.getChildAt(0);
        final AppCompatTextView tvBottomTitle = (AppCompatTextView) item.getChildAt(1);
        itvBottomIcon.setTextColor(ContextCompat.getColor(this, mClickedColor));
        tvBottomTitle.setTextColor(ContextCompat.getColor(this, mClickedColor));
        mVpContainer.setCurrentItem(position);
        mCurrentPage = position;
    }

    private void resetColor() {
        final int count = mBottomBar.getChildCount();
        for (int i = 0; i < count; i++) {
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            itemIcon.setTextColor(Color.GRAY);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            itemTitle.setTextColor(Color.GRAY);
        }
    }
}
