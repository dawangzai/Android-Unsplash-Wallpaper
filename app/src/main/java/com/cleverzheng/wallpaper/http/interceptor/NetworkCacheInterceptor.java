package com.cleverzheng.wallpaper.http.interceptor;

import com.cleverzheng.wallpaper.BuildConfig;
import com.cleverzheng.wallpaper.http.exception.NetworkException;
import com.cleverzheng.wallpaper.utils.NetworkUtil;
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
    public Response intercept(Chain chain) throws IOException, NetworkException {
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
                //有网络且网络可用
                if (response.isSuccessful()) {
                    response = response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", cacheControl.toString())
                            .build();
                }
            } else {
                Response cacheResponse = response.cacheResponse(); //拿到缓存中的数据，查看是否为null
                if (cacheResponse == null) {
                    throw new NetworkException(NetworkException.EXCEPTION_CODE_10001, NetworkException.EXCEPTION_MESSAGE_10001);
                } else {
                    if (maxAge >= 0) {
                        ToastUtil.showShortSafe("网络不可用，请检查网络设置");
                    } else {
                        //网络不可用，不使用缓存
                        throw new NetworkException(NetworkException.EXCEPTION_CODE_10002, NetworkException.EXCEPTION_MESSAGE_10002);
                    }
                }
            }
        } else {
            Response cacheResponse = response.cacheResponse(); //拿到缓存中的数据，查看是否为null
            if (cacheResponse == null) {
                throw new NetworkException(NetworkException.EXCEPTION_CODE_10000, NetworkException.EXCEPTION_MESSAGE_10000);
            } else {
                if (maxAge >= 0) {
                    //没有网络，使用缓存
                    ToastUtil.showShortSafe("没有网络，请检查网络设置");
                } else {
                    //没有网络，不使用缓存
                    throw new NetworkException(NetworkException.EXCEPTION_CODE_10003, NetworkException.EXCEPTION_MESSAGE_10003);
                }
            }
        }
        return response;
    }
}
