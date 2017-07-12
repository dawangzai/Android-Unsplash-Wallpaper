package com.cleverzheng.wallpaper.ui.collectiondetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cleverzheng.wallpaper.R;
import com.cleverzheng.wallpaper.base.BaseFragmentFragment;
import com.cleverzheng.wallpaper.bean.CollectionBean;
import com.cleverzheng.wallpaper.bean.CoverPhotoBean;
import com.cleverzheng.wallpaper.bean.PhotoBean;
import com.cleverzheng.wallpaper.bean.ProfileImageBean;
import com.cleverzheng.wallpaper.bean.UrlsBean;
import com.cleverzheng.wallpaper.bean.UserBean;
import com.cleverzheng.wallpaper.global.Constant;
import com.cleverzheng.wallpaper.listener.AppBarLayoutStateChangeListener;
import com.cleverzheng.wallpaper.operator.ImageLoaderOp;
import com.cleverzheng.wallpaper.ui.adapter.NewestListAdapter;
import com.cleverzheng.wallpaper.utils.LogUtil;
import com.cleverzheng.wallpaper.utils.StringUtil;
import com.cleverzheng.wallpaper.view.widget.DraweeImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wangzai on 2017/7/10.
 */

public class CollectionDetailFragment extends BaseFragmentFragment implements CollectionDetailContract.View {
    @BindView(R.id.dvBg)
    DraweeImageView dvBg;
    @BindView(R.id.tvCollectionTitle)
    TextView tvCollectionTitle;
    @BindView(R.id.dvUserHead)
    DraweeImageView dvUserHead;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvCollectionDeso)
    TextView tvCollectionDeso;

    private CollectionDetailContract.Presenter mPresenter;
    private NewestListAdapter adapter;

    public static CollectionDetailFragment getInstance() {
        CollectionDetailFragment collectionDetailFragment = new CollectionDetailFragment();
        return collectionDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setPresent(CollectionDetailContract.Presenter present) {
        if (present != null) {
            this.mPresenter = present;
            mPresenter.start();
        }
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
    }

    @Override
    public void initListener() {
        super.initListener();
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
    }

    @Override
    public void loadDataSuccess() {

    }

    @Override
    public void loadDataFailed(String message) {

    }

    @Override
    public void setCollectionInfo(CollectionBean collectionBean) {
        String title = collectionBean.getTitle();
        if (!StringUtil.isEmpty(title)) {
            tvCollectionTitle.setText(title);
            collapsingToolbar.setTitle(title);
        }

        String description = collectionBean.getDescription();
        if (!StringUtil.isEmpty(description)) {
            tvCollectionDeso.setVisibility(View.VISIBLE);
            tvCollectionDeso.setText(description);
        } else {
            tvCollectionDeso.setVisibility(View.GONE);
        }
        UserBean user = collectionBean.getUser();
        if (user != null) {
            ProfileImageBean profile_image = user.getProfile_image();
            String name = user.getName();
            String large = profile_image.getLarge();
            if (!StringUtil.isEmpty(large)) {
                ImageLoaderOp.setRoundImage(dvUserHead, large);
            }
            if (!StringUtil.isEmpty(name)) {
                tvUserName.setText(name);
            }
        }

        CoverPhotoBean cover_photo = collectionBean.getCover_photo();
        UrlsBean urls = cover_photo.getUrls();
        if (urls != null) {
            String regular = urls.getRegular();
            if (regular != null) {
                ImageLoaderOp.setImage(dvBg, regular);
            }
        }
    }

    @Override
    public void refreshCollectionPhotos(List<PhotoBean> collectionBeanList) {
        if (adapter == null) {
            adapter = new NewestListAdapter(Constant.PhotoListAdapterType.COLLECTION_DETAIL, this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
        adapter.refreshData(collectionBeanList);
    }
}
