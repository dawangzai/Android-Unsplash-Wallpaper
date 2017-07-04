package com.cleverzheng.wallpaper.ui.newest;

import android.app.Activity;
import android.os.Environment;

import com.cleverzheng.wallpaper.MainActivity;
import com.cleverzheng.wallpaper.bean.PhotoBean;
import com.cleverzheng.wallpaper.global.Constant;
import com.cleverzheng.wallpaper.http.HttpClient;
import com.cleverzheng.wallpaper.http.callback.OnResultCallback;
import com.cleverzheng.wallpaper.http.observer.HttpObserver;
import com.cleverzheng.wallpaper.operator.OpenActivityOp;
import com.cleverzheng.wallpaper.utils.LogUtil;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

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
        OpenActivityOp.openPersonDetailActivity(activity, username);
    }

    @Override
    public void downloadPicture(String url) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wallpaper";
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        File filePath = new File(path);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }

//        HttpObserver<LinksBean> observer = new HttpObserver<>(new OnResultCallback<LinksBean>() {
//            @Override
//            public void onSuccess(LinksBean linksBean) {
//
//            }
//
//            @Override
//            public void onFailed(int code, String message) {
//
//            }
//        });
//        httpClient.getPhotoDownload(observer, url);
        FileDownloader.getImpl().create("https://images.unsplash.com/photo-1497968021412-a86898ccbc4a?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&s=a4ca3749b929683efef2f8d2a7b6a62e")
                .setPath(path)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        LogUtil.i("download", "pending");
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        LogUtil.i("download", soFarBytes + "-----" + totalBytes);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        LogUtil.i("download", "完成");
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        LogUtil.i("download", "paused");
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        LogUtil.i("download", "完error成");
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        LogUtil.i("download", "warn");
                    }
                }).start();
    }
}
