package com.wangzai.lovesy.core.activity.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.wangzai.lovesy.core.fragment.LoveSyFragment;

import java.util.ArrayList;

/**
 * Created by wangzai on 2017/9/21
 */

class HomePagerAdapter extends FragmentPagerAdapter {

    private ArrayList<LoveSyFragment> mHomeFragments = new ArrayList<>();
    private FragmentManager mFragmentManager;

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    HomePagerAdapter(ArrayList<LoveSyFragment> homeFragments, FragmentManager fm) {
        super(fm);
        this.mFragmentManager = fm;
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

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        mFragmentManager.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = mHomeFragments.get(position);
        mFragmentManager.beginTransaction().hide(fragment).commit();
    }
}
