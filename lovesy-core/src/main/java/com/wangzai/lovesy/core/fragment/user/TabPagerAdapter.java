package com.wangzai.lovesy.core.fragment.user;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.wangzai.lovesy.core.fragment.BaseRefreshFragment;

import java.util.ArrayList;

/**
 * Created by wangzai on 2017/9/21
 */

class TabPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<BaseRefreshFragment> mTabItemFragment = new ArrayList<>();
    private ArrayList<String> mTabTitle = new ArrayList<>();
    private FragmentManager mFragmentManager;

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    TabPagerAdapter(ArrayList<String> tabTitle, ArrayList<BaseRefreshFragment> homeFragments, FragmentManager fm) {
        super(fm);
        this.mFragmentManager = fm;
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

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        mFragmentManager.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = mTabItemFragment.get(position);
        mFragmentManager.beginTransaction().hide(fragment).commit();
    }
}
