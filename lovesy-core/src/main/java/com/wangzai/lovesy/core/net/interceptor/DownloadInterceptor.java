package com.wangzai.lovesy.core.net.interceptor;

import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wangzai on 2017/11/30
 */

public class DownloadInterceptor extends BaseInterceptor {
    public DownloadInterceptor(InterceptorType type) {
        super(type);
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        final Request request = chain.request();
        return null;
    }
}
