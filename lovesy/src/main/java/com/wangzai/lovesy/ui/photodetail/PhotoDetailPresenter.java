package com.wangzai.lovesy.ui.photodetail;

import com.wangzai.lovesy.bean.PhotoBean;
import com.wangzai.lovesy.bean.UrlsBean;
import com.wangzai.lovesy.http.HttpClient;
import com.wangzai.lovesy.http.callback.OnResultCallback;
import com.wangzai.lovesy.http.observer.HttpObserver;
import com.wangzai.lovesy.utils.StringUtil;

/**
 * Created by wangzai on 2017/2/21.
 */

public class PhotoDetailPresenter implements PhotoDetailContract.Presenter {

    private PhotoDetailContract.View photoDetailView;
    private String photoId;
    private PhotoBean mPhotoBean;

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
        if (StringUtil.isEmpty(photoId)) {
            return;
        }
        HttpObserver<PhotoBean> observer = new HttpObserver<>(new OnResultCallback<PhotoBean>() {
            @Override
            public void onSuccess(PhotoBean photoBean) {
                if (photoBean != null) {
                    mPhotoBean = photoBean;
                    int width = photoBean.getWidth();
                    int height = photoBean.getHeight();
//                    photoDetailView.setImageSize(800, 1600);
                    UrlsBean urls = photoBean.getUrls();
                    if (urls != null) {
                        String thumb = urls.getThumb();
                        String small = urls.getSmall();
                        String regular = urls.getRegular();
                        String raw = urls.getRaw();
                        String full = urls.getFull();
                        if (!StringUtil.isEmpty(regular) && !StringUtil.isEmpty(small)) {
                            photoDetailView.setPhoto(thumb, regular);
//                            photoDetailView.setPhoto(regular);
                        }
                    }
                }
            }

            @Override
            public void onFailed(int code, String errorMsg) {

            }
        });
        HttpClient.getInstance().getSinglePhoto(observer, photoId);
    }

    @Override
    public void share() {

    }

    @Override
    public void download() {

//        HttpObserver<LinksBean> observer = new HttpObserver<>(new OnResultCallback<LinksBean>() {
//            @Override
//            public void onSuccess(LinksBean linksBean) {
//                download("https://images.unsplash.com/photo-1421899528807-04d925f39555?\n" +
//                        "ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&dl=cesar-lopez-rivadeneira-6088.jpg&s=03b3dd99abb6821e65e46a201a76ce0a", path);
//            }
//
//            @Override
//            public void onFailed(int code, String message) {
//
//            }
//        });
//        HttpClient.getInstance().getPhotoDownload(observer, url);

//        OkHttpManger.getInstance().downloadAsync();
    }

    @Override
    public void setWallpaper() {

    }
}
