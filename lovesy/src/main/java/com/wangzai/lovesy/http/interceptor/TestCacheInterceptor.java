package com.wangzai.lovesy.http.interceptor;

import com.wangzai.lovesy.BuildConfig;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wangzai on 2017/7/7.
 */

public class TestCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        CacheControl cacheControl = request.cacheControl(); //拿到各接口初始请求报文的cache-control

        Request.Builder requestBuilder = request.newBuilder();

        requestBuilder.addHeader("Authorization", "Client-ID " + BuildConfig.CLIENT_ID);

        Response response = chain.proceed(requestBuilder.build());
        int maxAge = cacheControl.maxAgeSeconds(); //是否使用缓存标志

        response = response.newBuilder()
                .removeHeader("Pragma")
                .header("Cache-Control", cacheControl.toString())
                .build();
        return response;
    }
}
