package com.wangzai.http.rx;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import retrofit2.Response;

/**
 * Created by wangzai on 2017/7/26.
 */

public class TestFunction<T> implements Function<Response<T>,T> {
    @Override
    public T apply(@NonNull Response<T> response) throws Exception {
        return null;
    }
}
