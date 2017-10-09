package com.wangzai.lovesy.home.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wangzai.lovesy.R;
import com.wangzai.lovesy.bean.ProfileImageBean;
import com.wangzai.lovesy.bean.UserBean;
import com.wangzai.lovesy.core.account.AccountManager;
import com.wangzai.lovesy.core.account.IUserChecker;
import com.wangzai.lovesy.core.activity.home.BaseHomeFragment;
import com.wangzai.lovesy.core.net.rx.RxHttpClient;
import com.wangzai.lovesy.core.net.rx.observer.ResultObserver;
import com.wangzai.lovesy.core.ui.imageloader.ImageLoader;
import com.wangzai.lovesy.core.widget.SimpleImageView;
import com.wangzai.lovesy.utils.activity.ActivityUtil;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * Created by wangzai on 2017/10/9
 */

public class PersonalFragment extends BaseHomeFragment implements IUserChecker, View.OnClickListener {

    @BindView(R.id.siv_avatar)
    SimpleImageView mSivAvatar;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_sign_in)
    TextView mTvSignIn;
    @BindView(R.id.ll_user_info)
    LinearLayout mLlUserInfo;
    @BindView(R.id.ll_location_site)
    LinearLayout mLlLocationSite;
    @BindView(R.id.tv_location)
    TextView mTvLocation;
    @BindView(R.id.tv_web_site)
    TextView mTvWebSite;
    @BindView(R.id.tv_bio)
    TextView mTvBio;

    @Override
    public Object setLayout() {
        return R.layout.fragment_personal;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        //检查登录状态
        AccountManager.checkAccount(this);

    }

    @Override
    public void onSignIn() {
        mLlUserInfo.setVisibility(View.VISIBLE);
        mTvSignIn.setVisibility(View.INVISIBLE);
        //设置用户信息
        RxHttpClient.builder()
                .url("me")
                .loader(getActivity())
                .build()
                .get()
                .subscribe(new ResultObserver() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull String result) {
                        setUserInfo(result);
                    }

                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });
    }

    @Override
    public void onNotSignIn() {
        //显示登录按钮
        mLlUserInfo.setVisibility(View.INVISIBLE);
        mTvSignIn.setVisibility(View.VISIBLE);
        mTvSignIn.setOnClickListener(this);
        String defaultAvatar = "res://" + getActivity().getPackageName() + "/" + R.mipmap.ic_avatar_default;
        ImageLoader.loaderImage(mSivAvatar, defaultAvatar);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.tv_sign_in:
                ActivityUtil.startSignInActivityResult(this);
                break;
            case R.id.ll_download:
                break;
            case R.id.ll_like:
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AccountManager.checkAccount(this);
    }

    private void setUserInfo(String userInfo) {
        final UserBean userBean = JSON.parseObject(userInfo, UserBean.class);
        final ProfileImageBean profileImage = userBean.getProfile_image();
        final String large = profileImage.getLarge();
        final String username = userBean.getUsername();
        final String portfolioUrl = userBean.getPortfolio_url();
        final String location = userBean.getLocation();
        final String bio = userBean.getBio();

        String avatarUrl;
        if (large == null) {
            avatarUrl = "res://" + getActivity().getPackageName() + "/" + R.mipmap.ic_avatar_default;
        } else {
            avatarUrl = large;
        }
        ImageLoader.loaderImage(mSivAvatar, avatarUrl);

        if (username != null) {
            mTvUserName.setText(username);
        }

        if (location != null || portfolioUrl != null) {
            mLlLocationSite.setVisibility(View.VISIBLE);
            if (location != null) {
                mTvLocation.setVisibility(View.VISIBLE);
                mTvLocation.setText(location);
            } else {
                mTvLocation.setVisibility(View.GONE);
            }
            if (portfolioUrl != null) {
                mTvWebSite.setVisibility(View.VISIBLE);
                mTvWebSite.setText(portfolioUrl);
            } else {
                mTvWebSite.setVisibility(View.GONE);
            }

        } else {
            mLlLocationSite.setVisibility(View.GONE);
        }

        if (bio != null) {
            mTvBio.setVisibility(View.VISIBLE);
            mTvBio.setText(bio);
        } else {
            mTvBio.setVisibility(View.GONE);
        }
    }
}
