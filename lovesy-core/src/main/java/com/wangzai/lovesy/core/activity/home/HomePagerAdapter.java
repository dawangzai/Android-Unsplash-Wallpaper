package com.wangzai.lovesy.core.activity.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by wangzai on 2017/9/21
 */

class HomePagerAdapter extends FragmentPagerAdapter {

    private ArrayList<BaseHomeFragment> mHomeFragments = new ArrayList<>();

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    HomePagerAdapter(ArrayList<BaseHomeFragment> homeFragments, FragmentManager fm) {
        super(fm);
        this.mHomeFragments = homeFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mHomeFragments.get(position);
    }

    @Override
    public int getCount() {
        return mHomeFragments.size();
    }
}
