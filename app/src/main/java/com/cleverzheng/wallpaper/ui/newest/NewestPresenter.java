package com.cleverzheng.wallpaper.ui.newest;

import android.app.Activity;
import android.util.Log;

import com.cleverzheng.wallpaper.MainActivity;
import com.cleverzheng.wallpaper.base.BaseActivity;
import com.cleverzheng.wallpaper.bean.PhotoBean;
import com.cleverzheng.wallpaper.global.Constant;
import com.cleverzheng.wallpaper.network.Network;
import com.cleverzheng.wallpaper.network.PhotoApi;
import com.cleverzheng.wallpaper.operator.OpenActivityOp;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author：cleverzheng
 * @date：2017/2/12:11:02
 * @email：zhengwang043@gmail.com
 * @description：最新页面Presenter的实现类
 */

public class NewestPresenter implements NewestContract.Presenter {
    private NewestContract.View newestView;

    private PhotoApi photoApi;
    private MainActivity activity;

    public NewestPresenter(NewestContract.View newestView, Activity activity) {
        this.activity = (MainActivity) activity;
        this.newestView = newestView;
        newestView.setPresent(this);
    }

    @Override
    public void start() {
        photoApi = Network.getInstance().getPhotoApi();
        refreshData(1, Constant.PER_PAGE);
    }

    @Override
    public void refreshData(int page, int per_page) {
        if (photoApi != null) {
            activity.showLoading();
            photoApi.getNewestPhotoList(page, per_page)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<PhotoBean>>() {
                        @Override
                        public void onCompleted() {
                            Log.i("abc", "onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i("abc", e.toString());
                        }

                        @Override
                        public void onNext(List<PhotoBean> photoBeen) {
                            ((MainActivity) activity).dismissLoading();
                            if (photoBeen != null && photoBeen.size() > 0) {
                                newestView.refresh(photoBeen);
                            }
                        }
                    });
        }
    }

    @Override
    public void loadMoreData(int page, int per_page) {
        if (photoApi != null) {
            photoApi.getNewestPhotoList(page, per_page)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<PhotoBean>>() {
                        @Override
                        public void onCompleted() {
                            Log.i("abc", "onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.i("abc", e.toString());
                        }

                        @Override
                        public void onNext(List<PhotoBean> photoBeen) {
                            if (photoBeen != null && photoBeen.size() > 0) {
                                newestView.loadMore(photoBeen);
                            }
                        }
                    });
        }
    }

    @Override
    public void openPhotoDetail(String photoId) {
        OpenActivityOp.openPhotoDetailActivity(activity, photoId);
    }

    @Override
    public void openUserDetail(String username) {
        OpenActivityOp.openPersonDetailActivity(activity, username);
    }
}
