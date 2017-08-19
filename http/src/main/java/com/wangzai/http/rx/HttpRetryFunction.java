package com.wangzai.http.rx;

import com.wangzai.http.utils.LogUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * Created by wangzai on 2017/7/26.
 */

public class HttpRetryFunction implements Function<Observable<? extends Throwable>, Observable<?>> {
    private int maxRetries = 2; //重试两次
    private int retryDelayMillis = 2; //延迟两秒重试
    private int retryCount;

    @Override
    public Observable<?> apply(@NonNull Observable<? extends Throwable> observable) throws Exception {
//        return observable.zipWith(Observable.range(1, 2), new BiFunction<Throwable, Integer, Integer>() {
//            @Override
//            public Integer apply(@NonNull Throwable throwable, @NonNull Integer integer) throws Exception {
//                  return integer;
//            }
//        }).flatMap(new Function<Integer, ObservableSource<?>>() {
//            @Override
//            public ObservableSource<?> apply(@NonNull Integer integer) throws Exception {
//                LogUtil.i("重试" + integer);
////                    (long) Math.pow(5, integer)
//                return Observable.timer(2, TimeUnit.SECONDS);
////
//            }
//        });
        return observable.flatMap(new Function<Throwable, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                if (++retryCount <= maxRetries) {
                    LogUtil.i("重试:" + retryCount);
                    return Observable.timer(retryDelayMillis, TimeUnit.MILLISECONDS);
                }
                return Observable.error(throwable);
            }
        });
    }
}
