package com.cleverzheng.wallpaper.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author：cleverzheng
 * @date：2017/3/7:11:36
 * @email：zhengwang043@gmail.com
 * @description：
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public SimpleFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
