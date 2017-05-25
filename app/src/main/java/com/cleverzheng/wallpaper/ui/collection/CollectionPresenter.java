package com.cleverzheng.wallpaper.ui.collection;

import android.app.Activity;
import android.util.Log;

import com.cleverzheng.wallpaper.MainActivity;
import com.cleverzheng.wallpaper.bean.CollectionBean;
import com.cleverzheng.wallpaper.bean.PhotoBean;
import com.cleverzheng.wallpaper.global.Constant;
import com.cleverzheng.wallpaper.network.CollectionApi;
import com.cleverzheng.wallpaper.network.Network;

import java.util.List;



/**
 * @author：cleverzheng
 * @date：2017/2/21:17:59
 * @email：zhengwang043@gmail.com
 * @description：
 */

public class CollectionPresenter implements CollectionContract.Presenter {
    private CollectionContract.View collectionView;
    private Activity activity;
    private CollectionApi collectionApi;

    public CollectionPresenter(CollectionContract.View collectionView, Activity activity) {
        this.collectionView = collectionView;
        this.activity = activity;
        collectionView.setPresent(this);
    }

    @Override
    public void start() {
        collectionApi = Network.getInstance().getCollectionApi();
        refreshData(1, Constant.PER_PAGE);
    }

    @Override
    public void refreshData(int page, int per_page) {
        if (collectionApi != null) {
//            collectionApi.getCollectionList(page, per_page)
//                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Subscriber<List<CollectionBean>>() {
//                        @Override
//                        public void onCompleted() {
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//
//                        }
//
//                        @Override
//                        public void onNext(List<CollectionBean> collectionBeen) {
//                            collectionView.refresh(collectionBeen);
//                        }
//                    });
        }
    }

    @Override
    public void loadMoreData(int page, int per_page) {
        if (collectionApi != null) {
//            collectionApi.getCollectionList(page, per_page)
//                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Subscriber<List<CollectionBean>>() {
//                        @Override
//                        public void onCompleted() {
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//
//                        }
//
//                        @Override
//                        public void onNext(List<CollectionBean> collectionBeen) {
//                            collectionView.loadMore(collectionBeen);
//                        }
//                    });
        }
    }
}
