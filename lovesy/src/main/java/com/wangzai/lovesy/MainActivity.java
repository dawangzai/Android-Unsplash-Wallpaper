package com.wangzai.lovesy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import com.wangzai.lovesy.mvp.base.BaseActivity;
import com.wangzai.lovesy.ui.collection.CollectionFragment;
import com.wangzai.lovesy.ui.collection.CollectionPresenter;
import com.wangzai.lovesy.ui.me.MeFragment;
import com.wangzai.lovesy.ui.me.MePresenter;
import com.wangzai.lovesy.ui.newest.NewestFragment;
import com.wangzai.lovesy.ui.newest.NewestPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.vpContent)
    ViewPager vpContent;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private static final int NUM_ITEMS = 3;

    private NewestPresenter newestPresenter;
    private NewestFragment newestFragment;
    private MeFragment meFragment;
    private CollectionFragment collectionFragment;
    private CollectionPresenter collectionPresenter;
    private MePresenter mePresenter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    vpContent.setCurrentItem(0);
                    return true;
                case R.id.navigation_collections:
                    vpContent.setCurrentItem(1);
                    return true;
                case R.id.navigation_me:
                    vpContent.setCurrentItem(2);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        setMainToolbar(getString(R.string.app_name));
        initBottomTab();
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        vpContent.setAdapter(adapter);
        vpContent.setCurrentItem(0);
        vpContent.setOffscreenPageLimit(NUM_ITEMS);
    }

    private void initBottomTab() {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void initListener() {
        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MenuItem item = navigation.getMenu().getItem(position);
                item.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return getNewestFragment();
                case 1:
                    return getCollectionFragment();
                case 2:
                    return getMeFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        private Fragment getNewestFragment() {
            newestFragment = NewestFragment.getInstance();
            newestPresenter = new NewestPresenter(newestFragment, MainActivity.this);
            return newestFragment;
        }

        private Fragment getCollectionFragment() {
            collectionFragment = CollectionFragment.getInstance();
            collectionPresenter = new CollectionPresenter(collectionFragment, MainActivity.this);
            return collectionFragment;
        }

        private Fragment getMeFragment() {
            meFragment = MeFragment.getInstances();
            mePresenter = new MePresenter(meFragment);
            return meFragment;
        }
    }
}
