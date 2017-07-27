package com.wangzai.http.observer;

import io.reactivex.disposables.Disposable;

/**
 * Created by wangzai on 2017/7/27.
 */

public interface HttpCallBack<T> {
    void doOnSubscribe(Disposable d);

    void doOnError(int code, String errorMsg);

    void doOnNext(T t);

    void doOnCompleted();
}
