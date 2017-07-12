package com.cleverzheng.wallpaper.ui.collectiondetail;

import com.cleverzheng.wallpaper.bean.CollectionBean;
import com.cleverzheng.wallpaper.bean.PhotoBean;
import com.cleverzheng.wallpaper.http.HttpClient;
import com.cleverzheng.wallpaper.http.callback.OnResultCallback;
import com.cleverzheng.wallpaper.http.observer.HttpObserver;

import java.util.List;

/**
 * Created by wangzai on 2017/7/10.
 */

public class CollectionDetailPresenter implements CollectionDetailContract.Presenter {

    private HttpClient mClient;
    private CollectionDetailFragment mView;
    private int collectionId;

    public CollectionDetailPresenter(CollectionDetailFragment collectionDetailFragment, int collectionId) {
        this.mView = collectionDetailFragment;
        this.collectionId = collectionId;
        mView.setPresent(this);
    }

    @Override
    public void start() {
        if (mClient == null) {
            mClient = HttpClient.getInstance();
        }
        getCollectionInfo(collectionId);
        getCollectionPhotos(collectionId);
    }

    @Override
    public void getCollectionInfo(int collectionId) {
        HttpObserver<CollectionBean> observer = new HttpObserver<>(new OnResultCallback<CollectionBean>() {
            @Override
            public void onSuccess(CollectionBean collectionBean) {
                if (collectionBean != null) {
                    mView.setCollectionInfo(collectionBean);
                }
            }

            @Override
            public void onFailed(int code, String message) {

            }
        });
        mClient.getSingleCollection(observer, collectionId);
    }

    @Override
    public void getCollectionPhotos(int collectionId) {
        HttpObserver<List<PhotoBean>> observer = new HttpObserver<>(new OnResultCallback<List<PhotoBean>>() {
            @Override
            public void onSuccess(List<PhotoBean> photoBeen) {
                if (photoBeen != null) {
                    mView.refreshCollectionPhotos(photoBeen);
                }
            }

            @Override
            public void onFailed(int code, String message) {

            }
        });
        mClient.getCollectionPhotoList(observer, collectionId);
    }
}
