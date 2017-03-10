package com.cleverzheng.wallpaper.ui.persondetail;

import android.app.Activity;

import com.cleverzheng.wallpaper.bean.CollectionBean;
import com.cleverzheng.wallpaper.bean.PhotoBean;
import com.cleverzheng.wallpaper.bean.UserBean;
import com.cleverzheng.wallpaper.network.Network;
import com.cleverzheng.wallpaper.network.UserApi;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author：cleverzheng
 * @date：2017/2/25:11:04
 * @email：zhengwang043@gmail.com
 * @description：
 */

public class PersonDetailPresenter implements PersonDetailContract.Presenter {
    private PersonDetailFragment mView;
    private PersonDetailActivity activity;
    private String username;
    private UserApi userApi;

    public PersonDetailPresenter(Activity activity, PersonDetailFragment personDetailFragment, String username) {
        this.activity = (PersonDetailActivity) activity;
        this.mView = personDetailFragment;
        this.username = username;
        mView.setPresent(this);
    }

    @Override
    public void start() {
        userApi = Network.getInstance().getUserApi();
        getUserInfo();
    }

    @Override
    public void getUserInfo() {
        if (userApi != null) {
            activity.showLoading();
            userApi.getUserInfo(username)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<UserBean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(UserBean userBean) {
                            activity.dismissLoading();
                            if (userBean != null) {
                                mView.setUserInfo(userBean);
                            }
                        }
                    });
        }
    }

    @Override
    public void getPersonPhotos() {
        if (userApi != null) {
            activity.showLoading();
            userApi.getUserPhotoList(username)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<PhotoBean>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(List<PhotoBean> photoBeen) {
                            activity.dismissLoading();
                            mView.setPersonPhotos(photoBeen);
                        }
                    });
        }
    }

    @Override
    public void getPersonCollections() {
        if (userApi != null) {
            activity.showLoading();
            userApi.getUserCollectionList(username)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<CollectionBean>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(List<CollectionBean> collectionBeanList) {
                            activity.dismissLoading();
                            mView.setPersonCollections(collectionBeanList);
                        }
                    });
        }
    }
}
