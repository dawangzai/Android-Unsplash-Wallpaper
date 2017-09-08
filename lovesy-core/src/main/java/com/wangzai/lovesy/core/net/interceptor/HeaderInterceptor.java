package com.wangzai.lovesy.core.net.interceptor;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wangzai on 2017/8/23
 */

public class HeaderInterceptor extends BaseInterceptor {

    public HeaderInterceptor(InterceptorType type) {
        super(type);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder requestBuilder = chain.request().newBuilder();
        requestBuilder.addHeader("Authorization", "Client-ID " + "b05bfc46a0de4842346cb5ce7c766b3a8c9da071ec77f3b5f719406829c2fb31");

        return chain.proceed(requestBuilder.build());
    }
}
