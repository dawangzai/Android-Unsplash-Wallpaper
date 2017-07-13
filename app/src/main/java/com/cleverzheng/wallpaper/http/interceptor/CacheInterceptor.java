package com.cleverzheng.wallpaper.http.interceptor;

import android.util.Log;

import com.cleverzheng.wallpaper.BuildConfig;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wangzai on 2017/6/26.
 */

public class CacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Request.Builder requestBuilder = request.newBuilder();
        requestBuilder.addHeader("Authorization", "Client-ID " + BuildConfig.CLIENT_ID);

//        requestBuilder.cacheControl(CacheControl.FORCE_CACHE);

        Log.i("network-cache", "-------interceptor-request------");

        CacheControl cacheControl = request.cacheControl(); //拿到各接口初始请求报文的cache-control

        Response response = chain.proceed(requestBuilder.build());

        Log.i("network-cache", "-------interceptor-response------");

//        response = response.newBuilder()
//                .removeHeader("Pragma")
//                .header("Cache-Control", cacheControl.toString())
//                .build();
        return response;
    }
}
