package com.wangzai.http.interceptor;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wangzai on 2017/7/24.
 */

public class HeaderInterceptor implements Interceptor {
    private Map<String, Object> header = new TreeMap<>();
    private String key;
    private String value;

    public HeaderInterceptor(Map<String, Object> header) {
        this.header = header;
    }

    public HeaderInterceptor(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();
        if (header != null && header.size() > 0) {
            for (Map.Entry<String, Object> entry : header.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), (String) entry.getValue());
            }
        }
        if (key != null && value != null) {
            requestBuilder.addHeader(key, value);
        }

        return chain.proceed(requestBuilder.build());
    }
}
