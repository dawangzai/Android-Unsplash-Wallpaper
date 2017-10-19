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
import com.wangzai.lovesy.api.ApiService;
import com.wangzai.lovesy.bean.ProfileImageBean;
import com.wangzai.lovesy.bean.UserBean;
import com.wangzai.lovesy.account.AccountManager;
import com.wangzai.lovesy.account.IUserChecker;
import com.wangzai.lovesy.core.fragment.LoveSyFragment;
import com.wangzai.lovesy.core.net.rx.RxHttpClient;
import com.wangzai.lovesy.core.net.rx.observer.ResultObserver;
import com.wangzai.lovesy.core.ui.image.loader.ImageLoader;
import com.wangzai.lovesy.core.util.LogUtil;
import com.wangzai.lovesy.core.widget.SimpleImageView;
import com.wangzai.lovesy.utils.activity.ActivityUtil;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by wangzai on 2017/10/9
 */

public class UserFragment extends LoveSyFragment implements IUserChecker, View.OnClickListener {

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
    @BindView(R.id.ll_photo)
    LinearLayout mLlPhoto;

    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;
    @BindView(R.id.ll_error_layout)
    LinearLayout mLlErrorLayout;
    @BindView(R.id.tv_error_msg)
    TextView mTvErrorMsg;

    private String username;

    @Override
    public Object setLayout() {
        return R.layout.fragment_user;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        mLlPhoto.setOnClickListener(this);
        mLlLike.setOnClickListener(this);
        mLlCollection.setOnClickListener(this);
        mLlUserInfo.setOnClickListener(this);
        mLlErrorLayout.setOnClickListener(this);

        setHasOptionsMenu(true);
    }

    @Override
    protected void onFragmentVisible() {
        setDefault();
        //检查登录状态
        AccountManager.checkAccount(this);
    }

    @Override
    public void onSignIn() {
        if (AccountManager.mUserInfo != null) {
            setUserInfo(AccountManager.mUserInfo);
            return;
        }
        //查用户信息
        RxHttpClient.builder()
                .url(ApiService.Me.ME)
                .loader(getActivity())
                .build()
                .get()
                .subscribe(new ResultObserver() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull String result) {
                        setUserInfo(result);
                        AccountManager.setUserInfo(result);
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

        mTvBio.setVisibility(View.GONE);
        mTvUserName.setText(getString(R.string.text_fragment_user_sign_in));
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
            case R.id.ll_photo:
                if (AccountManager.isSignIn()) {
                    ActivityUtil.startUserProfileActivity(getActivity(), 0, username);
                } else {
//                    Toast.makeText(getActivity(), getString(R.string.text_login), Toast.LENGTH_SHORT).show();
                    testDownload();
//                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext());
//                    mBuilder.setSmallIcon(R.mipmap.ic_launcher)
//                            .setContentTitle("正在下载图片")
//                            .setContentText("点击查看");
//                    Intent resultIntent = new Intent(getActivity(), HomeActivity.class);
//
//                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(getContext());
//                    stackBuilder.addParentStack(HomeActivity.class);
//                    stackBuilder.addNextIntent(resultIntent);
//                    PendingIntent resultPendingIntent =
//                            stackBuilder.getPendingIntent(
//                                    0,
//                                    PendingIntent.FLAG_UPDATE_CURRENT
//                            );
//                    mBuilder.setContentIntent(resultPendingIntent);
//                    NotificationManager mNotificationManager =
//                            (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
//                    mNotificationManager.notify(001, mBuilder.build());

                }
                break;
            case R.id.ll_like:
                if (AccountManager.isSignIn()) {
                    ActivityUtil.startUserProfileActivity(getActivity(), 1, username);
                } else {
                    Toast.makeText(getActivity(), getString(R.string.text_login), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_collection:
                if (AccountManager.isSignIn()) {
                    ActivityUtil.startUserProfileActivity(getActivity(), 2, username);
                } else {
                    Toast.makeText(getActivity(), getString(R.string.text_login), Toast.LENGTH_SHORT).show();
                }
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

        mTvBio.setVisibility(View.GONE);
        String defaultAvatar = "res://" + getActivity().getPackageName() + "/" + R.mipmap.ic_avatar_default;
        ImageLoader.loaderImage(mSivAvatar, defaultAvatar);
    }

    private void setUserInfo(String userInfo) {

        final UserBean userBean = JSON.parseObject(userInfo, UserBean.class);
        final ProfileImageBean profileImage = userBean.getProfile_image();
        final String large = profileImage.getLarge();
        username = userBean.getUsername();
        final String bio = userBean.getBio();

        mLlContainer.setVisibility(View.VISIBLE);
        mLlErrorLayout.setVisibility(View.INVISIBLE);

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

    private void testDownload() {
        RxHttpClient.builder()
                .url("http://prem.newegg.cn/android/NeweggCNAndroidPhone.apk")
                .build()
                .download()
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                        LogUtil.i("onSubscribe");
                    }

                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull Long aLong) {
                        LogUtil.i("onNext---" + aLong);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        LogUtil.i("onError");
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.i("onComplete");
                    }
                });
    }
}
