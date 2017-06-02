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

public class CacheInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();
        requestBuilder.addHeader("Authorization", "Client-ID " + BuildConfig.CLIENT_ID);
        if (!NetworkUtil.isConnected()) {
            requestBuilder.cacheControl(CacheControl.FORCE_CACHE);
            ToastUtil.showShortSafe("暂无网络");
        }
        Request request = requestBuilder.build();

        Response response = chain.proceed(request);
        String cacheControl = request.cacheControl().toString();
        if (NetworkUtil.isConnected()) {
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=60")
                    .build();
        } else {
            if (StringUtil.isEmpty(cacheControl)) {
                //没有网络，做界面UI提醒
                response = response.newBuilder()
                        .header("X-refresh-ui", "true")
                        .build();
            } else {
                //没有网络，不做界面UI处理
                response = response.newBuilder()
                        .header("X-refresh-ui", "false")
                        .build();
            }
        }
        return response;
    }
}
