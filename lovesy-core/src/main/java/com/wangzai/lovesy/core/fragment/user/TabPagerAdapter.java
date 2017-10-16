package com.wangzai.lovesy.core.fragment.user;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wangzai.lovesy.core.fragment.BaseRefreshFragment;

import java.util.ArrayList;

/**
 * Created by wangzai on 2017/9/21
 */

class TabPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<BaseRefreshFragment> mTabItemFragment = new ArrayList<>();
    private ArrayList<String> mTabTitle = new ArrayList<>();

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    TabPagerAdapter(ArrayList<String> tabTitle, ArrayList<BaseRefreshFragment> homeFragments, FragmentManager fm) {
        super(fm);
        this.mTabItemFragment = homeFragments;
        this.mTabTitle = tabTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return mTabItemFragment.get(position);
    }

    @Override
    public int getCount() {
        return mTabItemFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitle.get(position);
    }
}
