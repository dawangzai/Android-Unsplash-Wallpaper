package com.wangzai.http.manager;

import okhttp3.OkHttpClient;

/**
 * Created by wangzai on 2017/7/25.
 */

public class OkHttpManager {
    public static OkHttpManager instance;
    private OkHttpClient.Builder builder;

    private OkHttpManager() {
        this.builder = new OkHttpClient.Builder();
    }

    public static OkHttpManager getInstance() {
        if (instance == null) {
            synchronized (OkHttpManager.class) {
                if (instance == null) {
                    instance = new OkHttpManager();
                }
            }
        }
        return instance;
    }

    public OkHttpClient.Builder getBuilder() {
        return builder;
    }
}
