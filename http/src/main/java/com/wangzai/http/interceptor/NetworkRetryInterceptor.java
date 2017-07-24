package com.wangzai.http.interceptor;

import com.cleverzheng.wallpaper.utils.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wangzai on 2017/6/8.
 */

public class NetworkRetryInterceptor implements Interceptor {
    public int maxRetry;//最大重试次数
    private int retryNum = 0;//重试次数

    public NetworkRetryInterceptor(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        while (!response.isSuccessful() && retryNum < maxRetry) {
            retryNum++;
            LogUtil.i("WallpaperLog", "重试" + retryNum + "次");
            response = chain.proceed(request);
        }
        return response;
    }
}
