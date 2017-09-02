package com.wangzai.lovesy.ui.newest;

import android.app.Activity;
import android.os.Environment;

import com.wangzai.lovesy.MainActivity;
import com.wangzai.lovesy.bean.PhotoBean;
import com.wangzai.lovesy.global.Constant;
import com.wangzai.lovesy.http.HttpClient;
import com.wangzai.lovesy.http.callback.OnResultCallback;
import com.wangzai.lovesy.http.observer.HttpObserver;
import com.wangzai.lovesy.operator.OpenActivityOp;

import java.io.File;
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
                    newestView.loadDataSuccess();
                    newestView.refresh(photoBeen);
                }
            }

            @Override
            public void onFailed(int code, String message) {
                newestView.loadDataFailed(message);
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
//        OpenActivityOp.openPersonDetailActivity(activity, username);
        OpenActivityOp.openTestActivity(activity);
    }

    @Override
    public void downloadPicture(String url) {
        final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wallpaper";
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }

        OpenActivityOp.openTestActivity(activity);

//        download("http://img.mukewang.com/55249cf30001ae8a06000338.jpg", path);
//        download("https://images.unsplash.com/photo-1421899528807-04d925f39555?\n" +
//                "ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&dl=cesar-lopez-rivadeneira-6088.jpg&s=03b3dd99abb6821e65e46a201a76ce0a", path);
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
//        httpClient.getPhotoDownload(observer, url);

//        httpClient.downloadFile();
    }

//    private void download(String url, String path) {
//        Disposable disposable = RxDownload.getInstance(activity)
//                .download(url, "download5.jpg", path)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<DownloadStatus>() {
//                    @Override
//                    public void accept(@NonNull DownloadStatus downloadStatus) throws Exception {
//                        Log.i("download", "downloadStatus");
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(@NonNull Throwable throwable) throws Exception {
//                        Log.i("download", "throwable");
//                    }
//                }, new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        Log.i("download", "run");
//                    }
//                });
//    }
}
