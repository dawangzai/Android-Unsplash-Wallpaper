package com.wangzai.http.rx;

import com.wangzai.http.exception.ApiException;
import com.wangzai.http.utils.LogUtil;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import retrofit2.Response;

/**
 * Created by wangzai on 2017/7/26
 */

public class HttpResultFunction<T> implements Function<Response<T>, T> {

    @Override
    public T apply(@NonNull Response<T> response) throws Exception {
        int code = response.code();
        String message = response.message();
        LogUtil.i("code:" + code);
        LogUtil.i("message:" + response.message());
        if (response.isSuccessful()) {
            return response.body();
        } else {
            throw new ApiException(code, message);
        }
    }
}
