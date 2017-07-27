package com.wangzai.http.observer;

import io.reactivex.disposables.Disposable;

/**
 * Created by wangzai on 2017/7/27.
 */

public abstract class HttpObserver<T> extends BaseObserver<T> {

    /**
     * 获取disposable 在onDestroy方法中取消订阅disposable.dispose()
     */
    protected abstract void getDisposable(Disposable d);

    /**
     * 成功回调
     *
     * @param t
     */
    protected abstract void onSuccess(T t);

    /**
     * 失败回调
     *
     * @param errorMsg
     */
    protected abstract void onError(int code, String errorMsg);


    @Override
    public void doOnSubscribe(Disposable d) {
        getDisposable(d);
    }

    @Override
    public void doOnError(int code, String errorMsg) {
        onError(code, errorMsg);
    }

    @Override
    public void doOnNext(T t) {
        onSuccess(t);
    }

    @Override
    public void doOnCompleted() {

    }
}
