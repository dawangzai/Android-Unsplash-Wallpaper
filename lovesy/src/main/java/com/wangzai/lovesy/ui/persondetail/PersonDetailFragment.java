package com.wangzai.lovesy.ui.persondetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wangzai.lovesy.R;
import com.wangzai.lovesy.base.BaseFragmentFragment;
import com.wangzai.lovesy.bean.CollectionBean;
import com.wangzai.lovesy.bean.PhotoBean;
import com.wangzai.lovesy.bean.ProfileImageBean;
import com.wangzai.lovesy.bean.UserBean;
import com.wangzai.lovesy.listener.AppBarLayoutStateChangeListener;
import com.wangzai.lovesy.operator.ImageLoaderOp;
import com.wangzai.lovesy.ui.adapter.SimpleFragmentPagerAdapter;
import com.wangzai.lovesy.utils.LogUtil;
import com.wangzai.lovesy.utils.StringUtil;
import com.wangzai.lovesy.view.widget.DraweeImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangzai on 2017/2/24.
 */
public class PersonDetailFragment extends BaseFragmentFragment implements PersonDetailContract.View {
    private static final String TAB_PHOTO = "个人照片";
    private static final String TAB_COLLECTION = "照片合集";

    @BindView(R.id.dvUserHead)
    DraweeImageView dvUserHead;
    @BindView(R.id.dvBg)
    DraweeImageView dvBg;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.vpContent)
    ViewPager vpContent;
    @BindView(R.id.tlIndicator)
    TabLayout tlIndicator;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.llLocationAndPage)
    LinearLayout llLocationAndPage;
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.tvPersonalPage)
    TextView tvPersonalPage;
    @BindView(R.id.tvBio)
    TextView tvBio;

    private PersonDetailContract.Presenter mPresent;
    private SimpleFragmentPagerAdapter adapter;
    private int currentPage = 0; //vp的当前页
    private PersonPhotosFragment personPhotosFragment;
    private PersonCollectionFragment personCollectionFragment;

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

    @Override
    public void loadDataSuccess() {

    }

    @Override
    public void loadDataFailed(String message) {

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
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        }

        appBarLayout.addOnOffsetChangedListener(new AppBarLayoutStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, int state) {
                LogUtil.i("collapsingToolbar", "" + state);
                if (state == AppBarLayoutStateChangeListener.COLLAPSED) {
                    collapsingToolbar.setTitleEnabled(true);
                } else {
                    collapsingToolbar.setTitleEnabled(false);
                }
            }
        });
        initTabLayout();
        initViewPager();
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        personPhotosFragment = PersonPhotosFragment.getInstances();
        personPhotosFragment.setPresent(mPresent);
        personCollectionFragment = PersonCollectionFragment.getInstances();
        personCollectionFragment.setPresent(mPresent);
        fragmentList.add(personPhotosFragment);
        fragmentList.add(personCollectionFragment);
        FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
        adapter = new SimpleFragmentPagerAdapter(supportFragmentManager, fragmentList);
        vpContent.setAdapter(adapter);
//        getPersonPhotos();
//        getPersonCollections();
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
//            List<PhotoBean> photos = userBean.getPhotos();
//            if (photos != null && photos.size() > 0) {
//                PhotoBean photoBean = photos.get(0);
//                UrlsBean urls = photoBean.getUrls();
//                if (urls != null) {
//                    String regular = urls.getRegular();
//                    if (!StringUtil.isEmpty(regular)) {
//                        ImageLoaderOp.setImage(dvBg, regular);
//                    }
//                }
//            }
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
                tvName.setText(name);
            }
            String location = userBean.getLocation();
            if (!StringUtil.isEmpty(location)) {
                tvLocation.setVisibility(View.VISIBLE);
            } else {
                tvLocation.setVisibility(View.GONE);
            }
            String portfolio_url = userBean.getPortfolio_url();
            if (!StringUtil.isEmpty(portfolio_url)) {
                tvPersonalPage.setVisibility(View.VISIBLE);
            } else {
                tvPersonalPage.setVisibility(View.GONE);
            }
            String bio = userBean.getBio();
            if (!StringUtil.isEmpty(bio)) {
                tvBio.setVisibility(View.VISIBLE);
                tvBio.setText(bio);
            } else {
                tvBio.setVisibility(View.GONE);
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
