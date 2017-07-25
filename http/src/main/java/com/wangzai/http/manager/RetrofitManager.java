package com.wangzai.http.manager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangzai on 2017/7/25.
 */

public class RetrofitManager {
    private static RetrofitManager instance;

    private Retrofit.Builder mRetrofitBuilder;
    private OkHttpClient.Builder mOkHttpBuilder;

    public RetrofitManager() {

        mOkHttpBuilder = OkHttpManager.getInstance().getBuilder();

        mRetrofitBuilder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(mOkHttpBuilder.build());
    }

    public static RetrofitManager getInstance() {

        if (instance == null) {
            synchronized (RetrofitManager.class) {
                if (instance == null) {
                    instance = new RetrofitManager();
                }
            }
        }
        return instance;
    }

    public Retrofit.Builder getRetrofitBuilder() {
        return mRetrofitBuilder;
    }

    public Retrofit getRetrofit() {
        return mRetrofitBuilder.client(mOkHttpBuilder.build()).build();
    }

    public static <T> T createApi(Class<T> cls) {
        return getInstance().getRetrofit().create(cls);
    }

    public void setBaseUrl(String baseUrl) {
        getRetrofitBuilder().baseUrl(baseUrl);
    }
}
