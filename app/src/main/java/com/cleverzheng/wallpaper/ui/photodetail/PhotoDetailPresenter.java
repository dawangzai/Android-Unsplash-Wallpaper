package com.cleverzheng.wallpaper.ui.photodetail;

import android.util.Log;

import com.cleverzheng.wallpaper.bean.PhotoBean;
import com.cleverzheng.wallpaper.bean.UrlsBean;
import com.cleverzheng.wallpaper.network.Network;
import com.cleverzheng.wallpaper.network.PhotoApi;
import com.cleverzheng.wallpaper.utils.StringUtil;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * @author：cleverzheng
 * @date：2017/2/21:17:14
 * @email：zhengwang043@gmail.com
 * @description：
 */

public class PhotoDetailPresenter implements PhotoDetailContract.Presenter {

    private PhotoDetailContract.View photoDetailView;
    private String photoId;

    public PhotoDetailPresenter(PhotoDetailContract.View photoDetailView, String photoId) {
        this.photoDetailView = photoDetailView;
        this.photoId = photoId;
        photoDetailView.setPresent(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getSinglePhoto() {
        PhotoApi photoApi = Network.getInstance().getPhotoApi();
        if (StringUtil.isEmpty(photoId)) {
            return;
        }
        photoApi.getSinglePhoto(photoId)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PhotoBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(PhotoBean photoBean) {
                        if (photoBean != null) {
                            UrlsBean urls = photoBean.getUrls();
                            if (urls != null) {
                                String regular = urls.getRegular();
                                if (!StringUtil.isEmpty(regular)) {
                                    photoDetailView.setPhoto(regular);
                                }
                            }
                        }
                    }
                });
    }

    @Override
    public void share() {

    }

    @Override
    public void download() {

    }

    @Override
    public void setWallpaper() {

    }
}
