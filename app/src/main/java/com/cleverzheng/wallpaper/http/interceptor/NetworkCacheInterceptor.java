package com.cleverzheng.wallpaper.http.interceptor;

import com.cleverzheng.wallpaper.BuildConfig;
import com.cleverzheng.wallpaper.utils.NetworkUtil;
import com.cleverzheng.wallpaper.utils.StringUtil;
import com.cleverzheng.wallpaper.utils.ToastUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wangzai on 2017/6/2.
 */

public class NetworkCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        CacheControl cacheControl = request.cacheControl(); //拿到各接口初始请求报文的cache-control
        Request.Builder requestBuilder = request.newBuilder();
        requestBuilder.addHeader("Authorization", "Client-ID " + BuildConfig.CLIENT_ID);
        if (!NetworkUtil.isConnected() || !NetworkUtil.isAvailableByPing()) {
            requestBuilder.cacheControl(CacheControl.FORCE_CACHE);
        }

        Response response = chain.proceed(requestBuilder.build());

        int maxAge = cacheControl.maxAgeSeconds(); //是否使用缓存标志
        if (NetworkUtil.isConnected()) {
            if (NetworkUtil.isAvailableByPing()) {
                //有网络且网络可用，network-status=0
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", cacheControl.toString())
                        .build();
            } else {
                if (maxAge >= 0) {
                    //网络不可用，使用缓存，network-status=1
                    response = response.newBuilder()
                            .header("x-network-status", "1")
                            .build();
                } else {
                    //网络不可用，不使用缓存，network-status=2
                    response = response.newBuilder()
                            .header("x-network-status", "2")
                            .build();
                }
            }
        } else {
            if (maxAge >= 0) {
                //没有网络，使用缓存，network-status=3
                response = response.newBuilder()
                        .header("x-network-status", "3")
                        .build();
            } else {
                //没有网络，不使用缓存，network-status=4
                response = response.newBuilder()
                        .header("x-network-status", "4")
                        .build();
            }
        }
        return response;
    }
}
