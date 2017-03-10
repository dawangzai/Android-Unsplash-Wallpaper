package com.cleverzheng.wallpaper.network;

import android.util.Log;

import com.cleverzheng.wallpaper.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author ：cleverzheng
 * @date ：2017/1/17:16:21
 * @email ：zhengwang043@gmail.com
 * @description ：
 */

public class Network {
    private static Network mNetworks;

    private static final int DEFAULT_TIMEOUT = 5;

    private static Retrofit retrofit;
    private static PhotoApi mPhotoApi;
    private static CollectionApi mCollectionApi;
    private static UserApi mUserApi;

    public static Network getInstance() {
        if (mNetworks == null) {
            mNetworks = new Network();
        }
        return mNetworks;
    }

    public PhotoApi getPhotoApi() {
        return mPhotoApi == null ? configRetrofit(PhotoApi.class, true) : mPhotoApi;
    }

    public CollectionApi getCollectionApi() {
        return mCollectionApi == null ? configRetrofit(CollectionApi.class, true) : mCollectionApi;
    }

    public UserApi getUserApi() {
        return mUserApi == null ? configRetrofit(UserApi.class, true) : mUserApi;
    }

    private <T> T configRetrofit(Class<T> service, boolean isGetToken) {
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(configClient(true))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(service);
    }

    private OkHttpClient configClient(final boolean isGetToken) {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

        //为所有请求添加头部 Header 配置的拦截器
        Interceptor headerIntercept = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
//                builder.addHeader("X-Client-Platform", "Android");
//                builder.addHeader("X-Client-Version", BuildConfig.VERSION_NAME);
//                builder.addHeader("X-Client-Build", String.valueOf(BuildConfig.VERSION_CODE));
//
//                builder.removeHeader("Accept");
//                if (isGetToken) {
//                    builder.addHeader("Accept", "application/vnd.PHPHub.v1+json");
//                } else {
//                    builder.addHeader("Accept", "application/vnd.OralMaster.v1+json");
//                }

//                if (!TextUtils.isEmpty(mToken)) {
//                    builder.addHeader("Authorization", "Bearer " + mToken);
//                }
                builder.addHeader("Authorization", "Client-ID " + "b05bfc46a0de4842346cb5ce7c766b3a8c9da071ec77f3b5f719406829c2fb31");

                Request request = builder.build();

                return chain.proceed(request);
            }
        };

        // Log信息拦截器
        if (BuildConfig.LOG_DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    //打印retrofit日志
                    Log.i("test", "retrofitBack = " + message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient.addInterceptor(loggingInterceptor);
        }

        okHttpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.addNetworkInterceptor(headerIntercept);

        return okHttpClient.build();
    }

}
