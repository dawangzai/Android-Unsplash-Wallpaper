package com.wangzai.http.observer;

import com.wangzai.http.exception.ApiException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by wangzai on 2017/7/26.
 */

public abstract class BaseObserver<T> implements Observer<T>, HttpCallBack<T> {

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        doOnSubscribe(d);
    }

    @Override
    public void onNext(@NonNull T t) {
        doOnNext(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        String message = e.getMessage();
        if (e instanceof ApiException) {
            String[] msgTemp = message.split("#");
            int code = Integer.parseInt(msgTemp[0]);
            String msg = msgTemp[1];
            doOnError(code, msg);
        } else {
            //可以根据具体的异常自己匹配code和msg
            doOnError(ApiException.UNKNOWN_CODE, ApiException.UNKNOWN_EXCEPTION);
        }
    }

    @Override
    public void onComplete() {
        doOnCompleted();
    }
}
