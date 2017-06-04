package com.cleverzheng.wallpaper.ui.photodetail;

import com.cleverzheng.wallpaper.bean.PhotoBean;
import com.cleverzheng.wallpaper.bean.UrlsBean;
import com.cleverzheng.wallpaper.http.HttpClient;
import com.cleverzheng.wallpaper.http.callback.OnResultCallback;
import com.cleverzheng.wallpaper.http.observer.HttpObserver;
import com.cleverzheng.wallpaper.utils.StringUtil;

/**
 * Created by wangzai on 2017/2/21.
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
        if (StringUtil.isEmpty(photoId)) {
            return;
        }
        HttpObserver<PhotoBean> observer = new HttpObserver<>(new OnResultCallback<PhotoBean>() {
            @Override
            public void onSuccess(PhotoBean photoBean) {
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

    }

    @Override
    public void setWallpaper() {

    }
}
