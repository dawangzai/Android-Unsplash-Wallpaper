package com.wangzai.http.interceptor;

import com.wangzai.http.utils.LogUtil;
import com.wangzai.http.utils.NetworkUtil;
import com.wangzai.http.utils.StringUtil;

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
        String cacheControl = request.cacheControl().toString();

        //没有网络时，不管缓存是否失效，都使用缓存
        if (!NetworkUtil.isAvailableByPing()) {
            if (!StringUtil.isEmpty(cacheControl)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
        }

        Response response = chain.proceed(request);
        if (response.code() < 400) {
            return response
                    .newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        }
        return response;
    }
}
