package com.cleverzheng.wallpaper.ui.persondetail;

import android.app.Activity;

import com.cleverzheng.wallpaper.bean.CollectionBean;
import com.cleverzheng.wallpaper.bean.PhotoBean;
import com.cleverzheng.wallpaper.bean.UserBean;
import com.cleverzheng.wallpaper.http.HttpClient;
import com.cleverzheng.wallpaper.http.callback.OnResultCallback;
import com.cleverzheng.wallpaper.http.observer.HttpObserver;

import java.util.List;


/**
 * Created by wangzai on 2017/2/25.
 */

public class PersonDetailPresenter implements PersonDetailContract.Presenter {
    private PersonDetailFragment mView;
    private PersonDetailActivity activity;
    private String username;
    private HttpClient httpClient;

    public PersonDetailPresenter(Activity activity, PersonDetailFragment personDetailFragment, String username) {
        this.activity = (PersonDetailActivity) activity;
        this.mView = personDetailFragment;
        this.username = username;
        mView.setPresent(this);
    }

    @Override
    public void start() {
        httpClient = HttpClient.getInstance();
        getUserInfo();
    }

    @Override
    public void getUserInfo() {
        HttpObserver<UserBean> observer = new HttpObserver<>(new OnResultCallback<UserBean>() {
            @Override
            public void onSuccess(UserBean userBean) {
                if (userBean != null) {
                    mView.setUserInfo(userBean);
                }
            }

            @Override
            public void onFailed(int code, String errorMsg) {

            }
        });
        httpClient.getUserInfo(observer, username);
    }

    @Override
    public void getPersonPhotos() {
        HttpObserver<List<PhotoBean>> observer = new HttpObserver<>(new OnResultCallback<List<PhotoBean>>() {
            @Override
            public void onSuccess(List<PhotoBean> photoBeen) {
                mView.setPersonPhotos(photoBeen);
            }

            @Override
            public void onFailed(int code, String errorMsg) {

            }
        });
        httpClient.getUserPhotoList(observer, username);
    }

    @Override
    public void getPersonCollections() {
        HttpObserver<List<CollectionBean>> observer = new HttpObserver<>(new OnResultCallback<List<CollectionBean>>() {
            @Override
            public void onSuccess(List<CollectionBean> collectionBeanList) {
                mView.setPersonCollections(collectionBeanList);
            }

            @Override
            public void onFailed(int code, String errorMsg) {

            }
        });
        httpClient.getUserCollectionList(observer, username);
    }
}
