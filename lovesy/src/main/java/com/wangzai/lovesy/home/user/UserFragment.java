package com.wangzai.lovesy.home.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.wangzai.lovesy.R;
import com.wangzai.lovesy.bean.ProfileImageBean;
import com.wangzai.lovesy.bean.UserBean;
import com.wangzai.lovesy.core.account.AccountManager;
import com.wangzai.lovesy.core.account.IUserChecker;
import com.wangzai.lovesy.core.activity.home.BaseHomeFragment;
import com.wangzai.lovesy.core.net.rx.RxHttpClient;
import com.wangzai.lovesy.core.net.rx.observer.ResultObserver;
import com.wangzai.lovesy.core.ui.image.loader.ImageLoader;
import com.wangzai.lovesy.core.widget.SimpleImageView;
import com.wangzai.lovesy.utils.activity.ActivityUtil;

import butterknife.BindView;

/**
 * Created by wangzai on 2017/10/9
 */

public class UserFragment extends BaseHomeFragment implements IUserChecker, View.OnClickListener {

    @BindView(R.id.siv_avatar)
    SimpleImageView mSivAvatar;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.ll_user_info)
    LinearLayout mLlUserInfo;
    @BindView(R.id.tv_bio)
    TextView mTvBio;
    @BindView(R.id.ll_collection)
    LinearLayout mLlCollection;
    @BindView(R.id.ll_like)
    LinearLayout mLlLike;
    @BindView(R.id.ll_download)
    LinearLayout mLlDownload;

    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;
    @BindView(R.id.ll_error_layout)
    LinearLayout mLlErrorLayout;
    @BindView(R.id.tv_error_msg)
    TextView mTvErrorMsg;

    @Override
    public Object setLayout() {
        return R.layout.fragment_user;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        mLlDownload.setOnClickListener(this);
        mLlLike.setOnClickListener(this);
        mLlCollection.setOnClickListener(this);
        mLlUserInfo.setOnClickListener(this);
        mLlErrorLayout.setOnClickListener(this);

    }

    @Override
    protected void onFragmentVisible() {
        //检查登录状态
        AccountManager.checkAccount(this);
        setDefault();
    }

    @Override
    public void onSignIn() {
        //查用户信息
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
                        mLlContainer.setVisibility(View.INVISIBLE);
                        mLlErrorLayout.setVisibility(View.VISIBLE);
                        mTvErrorMsg.setText(msg);
                    }
                });
    }

    @Override
    public void onNotSignIn() {

        mLlContainer.setVisibility(View.VISIBLE);
        mLlErrorLayout.setVisibility(View.INVISIBLE);

        mLlDownload.setVisibility(View.VISIBLE);
        mLlCollection.setVisibility(View.GONE);
        mLlLike.setVisibility(View.GONE);
        mLlUserInfo.setVisibility(View.VISIBLE);
        String defaultAvatar = "res://" + getActivity().getPackageName() + "/" + R.mipmap.ic_avatar_default;
        ImageLoader.loaderImage(mSivAvatar, defaultAvatar);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.ll_user_info:
                if (AccountManager.isSignIn()) {
                    Toast.makeText(this.getContext(), R.string.coming_soon, Toast.LENGTH_SHORT).show();
                } else {
                    ActivityUtil.startSignInActivityResult(this);
                }
                break;
            case R.id.ll_download:
                ActivityUtil.startUserProfileActivity(getActivity(), getString(R.string.text_user_profile_download));
                break;
            case R.id.ll_like:
                break;
            case R.id.ll_error_layout:
                AccountManager.checkAccount(this);
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

    private void setDefault() {
        mLlContainer.setVisibility(View.VISIBLE);
        mLlErrorLayout.setVisibility(View.INVISIBLE);

        mLlDownload.setVisibility(View.VISIBLE);
        mTvUserName.setVisibility(View.VISIBLE);
        mTvBio.setVisibility(View.GONE);
        mTvUserName.setText(getString(R.string.text_fragment_user_sign_in));
        String defaultAvatar = "res://" + getActivity().getPackageName() + "/" + R.mipmap.ic_avatar_default;
        ImageLoader.loaderImage(mSivAvatar, defaultAvatar);
    }

    private void setUserInfo(String userInfo) {

        mLlContainer.setVisibility(View.VISIBLE);
        mLlErrorLayout.setVisibility(View.INVISIBLE);

        mLlDownload.setVisibility(View.VISIBLE);
        mLlCollection.setVisibility(View.VISIBLE);
        mLlLike.setVisibility(View.VISIBLE);

        final UserBean userBean = JSON.parseObject(userInfo, UserBean.class);
        final ProfileImageBean profileImage = userBean.getProfile_image();
        final String large = profileImage.getLarge();
        final String username = userBean.getUsername();
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

        if (bio != null) {
            mTvBio.setVisibility(View.VISIBLE);
            mTvBio.setText(bio);
        } else {
            mTvBio.setVisibility(View.GONE);
        }
    }
}
