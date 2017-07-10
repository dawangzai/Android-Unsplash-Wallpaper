package com.cleverzheng.wallpaper.http.observer;

import com.cleverzheng.wallpaper.http.callback.OnResultCallback;
import com.cleverzheng.wallpaper.http.exception.NetworkException;
import com.cleverzheng.wallpaper.utils.LogUtil;
import com.cleverzheng.wallpaper.utils.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by wangzai on 2017/5/26.
 */

public class HttpObserver<T> implements Observer<T> {
    private OnResultCallback mCallBack;
    private Disposable mDisposable;

    public HttpObserver(OnResultCallback<T> onResultCallback) {
        this.mCallBack = onResultCallback;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        this.mDisposable = d;
    }

    @Override
    public void onNext(@NonNull T t) {
        if (mCallBack != null) {
            mCallBack.onSuccess(t);
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        String message = e.getMessage();
        if (message.contains("#")) {
            String[] msgTemp = message.split("#");
            int code = Integer.parseInt(msgTemp[0]);
            String msg = msgTemp[1];
            mCallBack.onFailed(code, msg);
            LogUtil.i("WallpaperLog", message);
            ToastUtil.showShortSafe(msg);
        } else {
            LogUtil.i("WallpaperLog", message);
            ToastUtil.showShortSafe(NetworkException.EXCEPTION_MESSAGE_UNKNOWN);
            mCallBack.onFailed(NetworkException.EXCEPTION_CODE_UNKNOWN, NetworkException.EXCEPTION_MESSAGE_UNKNOWN);
        }
    }

    @Override
    public void onComplete() {

    }

    /**
     * 取消接收http响应
     */
    public void unSubscribe() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
