package com.cleverzheng.wallpaper.http.observer;

import com.cleverzheng.wallpaper.http.callback.OnResultCallback;
import com.cleverzheng.wallpaper.utils.StringUtil;
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
            String[] temp = e.getMessage().split("#");
            int code = Integer.parseInt(temp[0]);
            String msg = temp[1];
            mCallBack.onFailed(code, msg);
            if (StringUtil.isEmpty(msg)) {
                ToastUtil.showShortSafe("请求失败");
            } else {
                ToastUtil.showShortSafe(msg);
            }
        } else {
            mCallBack.onFailed(-1, message);
            ToastUtil.showShortSafe(message);
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
