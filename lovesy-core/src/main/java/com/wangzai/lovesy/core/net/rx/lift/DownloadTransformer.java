package com.wangzai.lovesy.core.net.rx.lift;

import android.os.Environment;

import com.wangzai.lovesy.core.net.HttpCreator;
import com.wangzai.lovesy.core.ui.loader.LoveSyLoader;
import com.wangzai.lovesy.core.util.LogUtil;
import com.wangzai.lovesy.core.util.file.FileUtil;

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

public class DownloadTransformer implements ObservableTransformer<ResponseBody, Long> {

    private static final String SDCARD_DIR = "LoveSy/photo";
//            Environment.getExternalStorageDirectory().getPath();

    @Override
    public ObservableSource<Long> apply(@NonNull Observable<ResponseBody> upstream) {
        return upstream
                .map(new Function<ResponseBody, Long>() {
                    @Override
                    public Long apply(@NonNull ResponseBody responseBody) throws Exception {
                        final InputStream inputStream = responseBody.byteStream();
                        FileUtil.writeToDisk(inputStream, SDCARD_DIR, "apk", "apk");
                        LogUtil.i(SDCARD_DIR);
                        return responseBody.contentLength();
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
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }
}