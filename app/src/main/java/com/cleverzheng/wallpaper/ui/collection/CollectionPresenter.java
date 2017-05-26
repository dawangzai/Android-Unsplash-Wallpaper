package com.cleverzheng.wallpaper.ui.collection;

import android.app.Activity;

import com.cleverzheng.wallpaper.bean.CollectionBean;
import com.cleverzheng.wallpaper.global.Constant;
import com.cleverzheng.wallpaper.http.HttpClient;
import com.cleverzheng.wallpaper.http.callback.OnResultCallback;
import com.cleverzheng.wallpaper.http.observer.HttpObserver;

import java.util.List;

/**
 * Created by wangzai on 2017/2/21.
 */
public class CollectionPresenter implements CollectionContract.Presenter {
    private CollectionContract.View collectionView;
    private Activity activity;
    private HttpClient httpClient;

    public CollectionPresenter(CollectionContract.View collectionView, Activity activity) {
        this.collectionView = collectionView;
        this.activity = activity;
        collectionView.setPresent(this);
    }

    @Override
    public void start() {
        httpClient = HttpClient.getInstance();
        refreshData(1, Constant.PER_PAGE);
    }

    @Override
    public void refreshData(int page, int per_page) {
        HttpObserver<List<CollectionBean>> observer = new HttpObserver<>(new OnResultCallback<List<CollectionBean>>() {
            @Override
            public void onSuccess(List<CollectionBean> collectionBeen) {
                collectionView.refresh(collectionBeen);
            }

            @Override
            public void onFailed(int code, String errorMsg) {

            }
        });
        httpClient.getCollectionList(observer, page, per_page);
    }

    @Override
    public void loadMoreData(int page, int per_page) {
        HttpObserver<List<CollectionBean>> observer = new HttpObserver<>(new OnResultCallback<List<CollectionBean>>() {
            @Override
            public void onSuccess(List<CollectionBean> collectionBeen) {
                collectionView.loadMore(collectionBeen);
            }

            @Override
            public void onFailed(int code, String errorMsg) {

            }
        });
        httpClient.getCollectionList(observer, page, per_page);
    }
}
