package com.cleverzheng.wallpaper.ui.persondetail;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.cleverzheng.wallpaper.R;
import com.cleverzheng.wallpaper.base.BaseFragment;
import com.cleverzheng.wallpaper.bean.CollectionBean;
import com.cleverzheng.wallpaper.bean.PhotoBean;
import com.cleverzheng.wallpaper.bean.ProfileImageBean;
import com.cleverzheng.wallpaper.bean.UrlsBean;
import com.cleverzheng.wallpaper.bean.UserBean;
import com.cleverzheng.wallpaper.operator.ImageLoaderOp;
import com.cleverzheng.wallpaper.ui.adapter.SimpleFragmentPagerAdapter;
import com.cleverzheng.wallpaper.utils.StringUtil;
import com.cleverzheng.wallpaper.view.widget.MyDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author：cleverzheng
 * @date：2017/2/24:22:21
 * @email：zhengwang043@gmail.com
 * @description：用户详情
 */

public class PersonDetailFragment extends BaseFragment implements PersonDetailContract.View {
    private static final String TAB_PHOTO = "个人照片";
    private static final String TAB_COLLECTION = "照片合集";

    @BindView(R.id.dvUserHead)
    MyDraweeView dvUserHead;
    @BindView(R.id.dvBg)
    MyDraweeView dvBg;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.vpContent)
    ViewPager vpContent;
    @BindView(R.id.tlIndicator)
    TabLayout tlIndicator;
    private PersonDetailContract.Presenter mPresent;
    private SimpleFragmentPagerAdapter adapter;
    private int currentPage = 0; //vp的当前页
    private PersonPhotosFragment personPhotosFragment;
    PersonCollectionFragment personCollectionFragment;

    public static PersonDetailFragment getInstance() {
        PersonDetailFragment personDetailFragment = new PersonDetailFragment();
        return personDetailFragment;
    }

    @Override
    public void setPresent(PersonDetailContract.Presenter present) {
        if (present != null) {
            this.mPresent = present;
            mPresent.start();
        }
    }

    public PersonDetailContract.Presenter getPresent() {
        if (mPresent != null) {
            return mPresent;
        }
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_persondetail);
//        ButterKnife.bind(this, getContentView());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_persondetail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        if (collapsingToolbar != null) {
            toolbar.setNavigationIcon(R.mipmap.ic_action_return);
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        }
        initTabLayout();
        initViewPager();
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        personPhotosFragment = PersonPhotosFragment.getInstances();
        personCollectionFragment = PersonCollectionFragment.getInstances();
        fragmentList.add(personPhotosFragment);
        fragmentList.add(personCollectionFragment);
        FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
        adapter = new SimpleFragmentPagerAdapter(supportFragmentManager, fragmentList);
        vpContent.setAdapter(adapter);
        getPersonPhotos();
        getPersonCollections();
    }

    /**
     * 初始化TabLayout
     */
    private void initTabLayout() {
        tlIndicator.addTab(tlIndicator.newTab().setText(TAB_PHOTO));
        tlIndicator.addTab(tlIndicator.newTab().setText(TAB_COLLECTION));
    }

    @Override
    public void initListener() {
        super.initListener();
        tlIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpContent.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPage = position;
                tlIndicator.setScrollPosition(position, positionOffset, true);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void setUserInfo(UserBean userBean) {
        if (userBean != null) {
            List<PhotoBean> photos = userBean.getPhotos();
            if (photos != null && photos.size() > 0) {
                PhotoBean photoBean = photos.get(0);
                UrlsBean urls = photoBean.getUrls();
                if (urls != null) {
                    String regular = urls.getRegular();
                    if (!StringUtil.isEmpty(regular)) {
                        ImageLoaderOp.setImage(dvBg, regular);
                    }
                }
            }
            ProfileImageBean profile_image = userBean.getProfile_image();
            if (profile_image != null) {
                String large = profile_image.getLarge();
                if (!StringUtil.isEmpty(large)) {
                    ImageLoaderOp.setRoundImage(dvUserHead, large);
                }
            }
            String name = userBean.getName();
            if (!StringUtil.isEmpty(name)) {
                collapsingToolbar.setTitle(name);
            }
        }
    }

    public void setPersonPhotos(List<PhotoBean> photoBeanList) {
        if (photoBeanList != null && photoBeanList.size() > 0) {
            personPhotosFragment.refreshViewPager(photoBeanList);
        }
    }

    public void getPersonPhotos() {
        mPresent.getPersonPhotos();
    }

    public void getPersonCollections() {
        mPresent.getPersonCollections();
    }

    public void setPersonCollections(List<CollectionBean> collectionBeanList) {
        if (collectionBeanList != null && collectionBeanList.size() > 0) {
            personCollectionFragment.refreshViewPager(collectionBeanList);
        }
    }
}
