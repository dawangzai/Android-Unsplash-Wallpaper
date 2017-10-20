package com.wangzai.lovesy.core.net.rx.lift;

import android.content.Intent;
import android.net.Uri;

import com.wangzai.lovesy.core.app.LoveSy;
import com.wangzai.lovesy.core.net.HttpCreator;
import com.wangzai.lovesy.core.ui.loader.LoveSyLoader;
import com.wangzai.lovesy.core.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by wangzai on 2017/10/18
 */

public class DownloadTransformer implements ObservableTransformer<ResponseBody, String> {

    private final String mDownloadDir;
    private final String mDownloadName;
    private final String mDownloadExtension;
    private File file;

    public DownloadTransformer(String downloadDir,
                               String downloadName,
                               String downloadExtension) {
        if (downloadDir == null || downloadDir.equals("")) {
            downloadDir = "apk";
        }
        if (downloadExtension == null || downloadExtension.equals("")) {
            downloadExtension = "apk";
        }
        this.mDownloadDir = downloadDir;
        this.mDownloadName = downloadName;
        this.mDownloadExtension = downloadExtension;
    }

    @Override
    public ObservableSource<String> apply(@NonNull Observable<ResponseBody> upstream) {
        return upstream
                .map(new Function<ResponseBody, String>() {
                    @Override
                    public String apply(@NonNull ResponseBody responseBody) throws Exception {
                        final InputStream inputStream = responseBody.byteStream();
                        if (mDownloadName == null) {
                            file = FileUtil.writeToDisk(inputStream, mDownloadDir, mDownloadExtension.toUpperCase(), mDownloadExtension);
                        } else {
                            FileUtil.writeToDisk(inputStream, mDownloadDir, mDownloadName);
                        }
                        return String.valueOf(responseBody.contentLength());
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        //清空请求参数容器，供下次请求使用
                        HttpCreator.getParams().clear();
                        LoveSyLoader.stopLoading();
//                        logErrorMessage(throwable);
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        //清空请求参数容器，供下次请求使用
                        HttpCreator.getParams().clear();
                        LoveSyLoader.stopLoading();

                        autoInstallApk();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void autoInstallApk() {
        final Intent install = new Intent();
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        install.setAction(Intent.ACTION_VIEW);
        install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        LoveSy.getApplicationContext().startActivity(install);
    }
}
