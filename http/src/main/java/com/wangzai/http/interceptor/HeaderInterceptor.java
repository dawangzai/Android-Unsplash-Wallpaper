package com.wangzai.http.interceptor;

import com.cleverzheng.wallpaper.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wangzai on 2017/7/24.
 */

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();
        requestBuilder.addHeader("Authorization", "Client-ID " + BuildConfig.CLIENT_ID);
        return chain.proceed(requestBuilder.build());
    }
}
