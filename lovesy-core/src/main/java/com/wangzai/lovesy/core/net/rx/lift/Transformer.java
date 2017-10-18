package com.wangzai.lovesy.core.net.rx.lift;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.wangzai.lovesy.core.net.HttpCreator;
import com.wangzai.lovesy.core.ui.loader.LoaderStyle;
import com.wangzai.lovesy.core.ui.loader.LoveSyLoader;
import com.wangzai.lovesy.core.util.LogUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import retrofit2.HttpException;

/**
 * Created by wangzai on 2017/9/6
 */

public class Transformer implements ObservableTransformer<String, String> {
    private Context mContext;
    private LoaderStyle mStyle;

    public Transformer(Context context, LoaderStyle style) {
        this.mContext = context;
        this.mStyle = style;
    }

    @Override
    public ObservableSource<String> apply(@NonNull Observable<String> upstream) {
        return upstream
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        LoveSyLoader.showLoading(mContext, mStyle);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        //清空请求参数容器，供下次请求使用
                        HttpCreator.getParams().clear();
                        LoveSyLoader.stopLoading();
                        logErrorMessage(throwable);
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

    /**
     * 打印错误日志
     *
     * @param throwable
     * @throws IOException
     */
    private void logErrorMessage(@NonNull Throwable throwable) throws IOException {
        if (throwable instanceof HttpException) {
            HttpException exception = (HttpException) throwable;
            int code = exception.code();
            String message = exception.message();
            ResponseBody responseBody = exception.response().errorBody();
            if (responseBody != null) {
                BufferedSource source = responseBody.source();
                Charset charset = Charset.forName("UTF-8");
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(Charset.forName("UTF-8"));
                }
                String errorMsg = source.readString(charset);
                String errors = JSON.parseObject(errorMsg).getJSONArray("errors").toString();
                if (message.isEmpty()) {
                    message += errors;
                } else {
                    message += "," + errors;
                }
            }
            LogUtil.i("错误码 = " + code);
            LogUtil.i("错误信息 = " + message);
        } else {
            LogUtil.i("错误信息 = " + throwable.toString());
        }
    }
}


//    @Override
//    public ObservableSource<T> apply(@NonNull Observable<String> upstream) {
//        return upstream
//                .map(new Function<String, T>() {
//                    @SuppressWarnings("unchecked")
//                    @Override
//                    public T apply(@NonNull String s) throws Exception {
//                        return (T) JSON.parseObject(s);
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(@NonNull Disposable disposable) throws Exception {
//                        Log.i("LoveSyDebug", "doOnSubscribe");
//                    }
//                })
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread());
//    }
