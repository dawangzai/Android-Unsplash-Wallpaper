package com.cleverzheng.wallpaper.http.observer;

import com.cleverzheng.wallpaper.http.callback.OnResultCallback;
import com.cleverzheng.wallpaper.http.exception.ApiException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by wangzai on 2017/5/26.
 */

public class HttpObserver<T> implements Observer<T> {
    private OnResultCallback mCallBack;

    public HttpObserver(OnResultCallback<T> onResultCallback) {
        this.mCallBack = onResultCallback;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull T t) {
        if (mCallBack != null) {
            mCallBack.onSuccess(t);
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        String msg = e.getMessage();
        int code;
        if (msg.contains("#")) {
            code = Integer.parseInt(msg.split("#")[0]);
            mCallBack.onFailed(code, msg.split("#")[1]);
        } else {
            code = ApiException.Code_Default;
            mCallBack.onFailed(code, msg);
        }
    }

    @Override
    public void onComplete() {

    }
}
