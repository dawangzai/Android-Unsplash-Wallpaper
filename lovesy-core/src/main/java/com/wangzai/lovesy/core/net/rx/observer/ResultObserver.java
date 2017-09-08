package com.wangzai.lovesy.core.net.rx.observer;

import android.widget.Toast;

import com.wangzai.lovesy.core.app.LoveSy;

import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by wangzai on 2017/9/6
 */

public abstract class ResultObserver implements Observer<String> {

    public abstract void onSuccess(@NonNull String result);

    public abstract void onFailure(int code, String msg);

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull String s) {
        onSuccess(s);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        toastErrorMessage(e);
    }

    @Override
    public void onComplete() {

    }

    private void toastErrorMessage(@NonNull Throwable e) {
        if (e instanceof HttpException) {
            final HttpException exception = (HttpException) e;
            int code = exception.code();
            String message = exception.message();
            if (message.isEmpty()) {
                if (code >= 500 && code < 600) {
                    message = "服务端异，请稍后重试";
                } else if (code >= 400 && code < 500) {
                    message = "客户端错，请稍后重试";
                }
            }
            onFailure(code, message);
            Toast.makeText(LoveSy.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        } else if (e instanceof SocketTimeoutException) {
            onFailure(0, "连接超时，请稍后重试");
            Toast.makeText(LoveSy.getApplicationContext(), "连接超时，请稍后重试", Toast.LENGTH_SHORT).show();
        } else {
            onFailure(0, "网络错误，请稍后重试");
            Toast.makeText(LoveSy.getApplicationContext(), "网络错误，请稍后重试", Toast.LENGTH_SHORT).show();
        }
    }
}
