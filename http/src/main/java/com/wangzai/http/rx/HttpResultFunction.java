package com.wangzai.http.rx;

import com.wangzai.http.utils.LogUtil;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import retrofit2.Response;

/**
 * Created by wangzai on 2017/7/26.
 */

public class HttpResultFunction<T> implements Function<Response<T>, T> {

    @Override
    public T apply(@NonNull Response<T> response) throws Exception {
        return null;
    }
//    @Override
//    public T apply(@NonNull Response<T> response) throws Exception {
//        int code = response.code();
//        if (code == 200) {
//            return response.body();
//        } else {
//            LogUtil.i("HttpRequest", response.message());
//            return null;
////            throw new NetworkException(code, NetworkException.EXCEPTION_MESSAGE_UNKNOWN);
//        }
//    }
}
