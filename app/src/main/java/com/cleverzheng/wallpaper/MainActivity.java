package com.cleverzheng.wallpaper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.cleverzheng.wallpaper.base.BaseActivity;
import com.cleverzheng.wallpaper.ui.collection.CollectionFragment;
import com.cleverzheng.wallpaper.ui.collection.CollectionPresenter;
import com.cleverzheng.wallpaper.ui.me.MeFragment;
import com.cleverzheng.wallpaper.ui.me.MePresenter;
import com.cleverzheng.wallpaper.ui.newest.NewestFragment;
import com.cleverzheng.wallpaper.ui.newest.NewestPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.vpContent)
    ViewPager vpContent;
    @BindView(R.id.bottomTab)
    PagerBottomTabLayout bottomTab;

    private int currentItem = 0;

    private NewestPresenter newestPresenter;
    private NewestFragment newestFragment;
    private MeFragment meFragment;
    private CollectionFragment collectionFragment;
    private CollectionPresenter collectionPresenter;
    private MePresenter mePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        setMainToolbar("美图");
        initBottomTab();
        List<Fragment> fragmentList = new ArrayList<>();
        newestFragment = NewestFragment.newInstance();
        collectionFragment = CollectionFragment.getInstance();
        meFragment = MeFragment.getInstances();
        fragmentList.add(newestFragment);
        fragmentList.add(collectionFragment);
        fragmentList.add(meFragment);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), fragmentList);
        vpContent.setAdapter(adapter);
        vpContent.setCurrentItem(currentItem);

        if (newestPresenter == null) {
            newestPresenter = new NewestPresenter(newestFragment, this);
        }

        if (collectionPresenter == null) {
            collectionPresenter = new CollectionPresenter(collectionFragment, this);
        }

        if (mePresenter == null) {
            mePresenter = new MePresenter(meFragment);
        }
    }

    private void initBottomTab() {
        Controller controller = bottomTab.builder()
                .addTabItem(android.R.drawable.ic_menu_camera, getString(R.string.bottom_tab_1))
                .addTabItem(android.R.drawable.ic_menu_compass, getString(R.string.bottom_tab_2))
                .addTabItem(android.R.drawable.ic_menu_search, getString(R.string.bottom_tab_3))
                .build();
        controller.addTabItemClickListener(new OnTabItemSelectListener() {
            @Override
            public void onSelected(int index, Object tag) {
                currentItem = index;
                vpContent.setCurrentItem(currentItem);
                Toast.makeText(MainActivity.this, index + "-----" + "onSelected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRepeatClick(int index, Object tag) {
                Toast.makeText(MainActivity.this, index + "-----" + "onRepeatClick", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
    }

    private class PagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragments;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
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
}
