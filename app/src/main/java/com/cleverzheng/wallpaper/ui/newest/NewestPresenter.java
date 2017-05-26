package com.cleverzheng.wallpaper.ui.newest;

import android.app.Activity;

import com.cleverzheng.wallpaper.MainActivity;
import com.cleverzheng.wallpaper.bean.PhotoBean;
import com.cleverzheng.wallpaper.global.Constant;
import com.cleverzheng.wallpaper.http.HttpClient;
import com.cleverzheng.wallpaper.http.callback.OnResultCallback;
import com.cleverzheng.wallpaper.http.observer.HttpObserver;
import com.cleverzheng.wallpaper.operator.OpenActivityOp;

import java.util.List;

/**
 * Created by wangzai on 2017/2/12.
 */
public class NewestPresenter implements NewestContract.Presenter {
    private NewestContract.View newestView;

    private MainActivity activity;
    private HttpClient httpClient;

    public NewestPresenter(NewestContract.View newestView, Activity activity) {
        this.activity = (MainActivity) activity;
        this.newestView = newestView;
        newestView.setPresent(this);
    }

    @Override
    public void start() {
        httpClient = HttpClient.getInstance();
        refreshData(1, Constant.PER_PAGE);
    }

    @Override
    public void refreshData(int page, int per_page) {
        HttpObserver<List<PhotoBean>> observer = new HttpObserver<>(new OnResultCallback<List<PhotoBean>>() {
            @Override
            public void onSuccess(List<PhotoBean> photoBeen) {
                if (photoBeen != null && photoBeen.size() > 0) {
                    newestView.refresh(photoBeen);
                }
            }

            @Override
            public void onFailed(int code, String errorMsg) {

            }
        });
        httpClient.getNewestPhotoList(observer, page, per_page);
    }

    @Override
    public void loadMoreData(int page, int per_page) {
        HttpObserver<List<PhotoBean>> observer = new HttpObserver<>(new OnResultCallback<List<PhotoBean>>() {
            @Override
            public void onSuccess(List<PhotoBean> photoBeen) {
                if (photoBeen != null && photoBeen.size() > 0) {
                    newestView.loadMore(photoBeen);
                }
            }

            @Override
            public void onFailed(int code, String errorMsg) {

            }
        });
        httpClient.getNewestPhotoList(observer, page, per_page);
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
