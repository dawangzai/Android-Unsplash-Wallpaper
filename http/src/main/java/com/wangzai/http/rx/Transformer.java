package com.wangzai.http.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by wangzai on 2017/7/26.
 */

public class Transformer {

    public static <T> ObservableTransformer<Response<T>, T> transformer() {
        return new ObservableTransformer<Response<T>, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<Response<T>> upstream) {
                return upstream
                        .map(new HttpResultFunction<T>())
                        .retryWhen(new HttpRetryFunction())
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
                                //在主线程执行，可以做一些进度条的处理
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
