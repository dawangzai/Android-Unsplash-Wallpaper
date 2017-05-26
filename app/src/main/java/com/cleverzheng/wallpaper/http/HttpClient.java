package com.cleverzheng.wallpaper.http;

import android.util.Log;

import com.cleverzheng.wallpaper.BuildConfig;
import com.cleverzheng.wallpaper.bean.CollectionBean;
import com.cleverzheng.wallpaper.bean.PhotoBean;
import com.cleverzheng.wallpaper.bean.UserBean;
import com.cleverzheng.wallpaper.http.api.CollectionService;
import com.cleverzheng.wallpaper.http.api.PhotoService;
import com.cleverzheng.wallpaper.http.api.UserService;
import com.cleverzheng.wallpaper.http.exception.ApiException;
import com.cleverzheng.wallpaper.http.observer.HttpObserver;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangzai on 2017/5/25.
 */

public class HttpClient {
    private static HttpClient mClient;

    private static final int DEFAULT_TIMEOUT = 5;

    private static Retrofit mRetrofit;
    private static PhotoService mPhotoService;
    private static UserService mUserService;
    private static CollectionService mCollectionService;

    public static HttpClient getInstance() {
        if (mClient == null) {
            mClient = new HttpClient();
        }
        return mClient;
    }

    public PhotoService getPhotoService() {
        return mPhotoService == null ? configRetrofit(PhotoService.class) : mPhotoService;
    }

    public UserService getUserService() {
        return mUserService == null ? configRetrofit(UserService.class) : mUserService;
    }

    public CollectionService getCollectionService() {
        return mCollectionService == null ? configRetrofit(CollectionService.class) : mCollectionService;
    }


    private <T> T configRetrofit(Class<T> service) {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/")
                .client(configClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return mRetrofit.create(service);
    }

    private OkHttpClient configClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS); //设置超时时间

        //为所有请求添加头部 Header 配置的拦截器
        Interceptor headerIntercept = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("Authorization", "Client-ID " + BuildConfig.CLIENT_ID);

                Request request = builder.build();

                return chain.proceed(request);
            }
        };

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("test", "retrofitBack = " + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (BuildConfig.LOG_DEBUG) {
            okHttpClient.addInterceptor(loggingInterceptor);
        }
        okHttpClient.addNetworkInterceptor(headerIntercept);

        return okHttpClient.build();
    }

    private class HttpResultFunction<T> implements Function<retrofit2.Response<T>, T> {
        @Override
        public T apply(@NonNull retrofit2.Response<T> response) throws Exception {
            int code = response.code();
            if (code == 200) {
                return response.body();
            } else {
                throw new ApiException(code, response.message());
            }
        }
    }

    /****************************************PhotoService*******************************************/
    public void getNewestPhotoList(HttpObserver<List<PhotoBean>> observer, int page, int pre_page) {
        getPhotoService().getNewestPhotoList(page, pre_page)
                .map(new HttpResultFunction<List<PhotoBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getSinglePhoto(HttpObserver<PhotoBean> observer, String id) {
        getPhotoService().getSinglePhoto(id)
                .map(new HttpResultFunction<PhotoBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /****************************************CollectionService*******************************************/
    public void getCollectionList(HttpObserver<List<CollectionBean>> observer, int page, int pre_page) {
        getCollectionService().getCollectionList(page, pre_page)
                .map(new HttpResultFunction<List<CollectionBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getSingleCollection(HttpObserver<CollectionBean> observer, String id) {
        getCollectionService().getSingleCollection(id)
                .map(new HttpResultFunction<CollectionBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getCollectionPhotoList(HttpObserver<List<PhotoBean>> observer, String id) {
        getCollectionService().getCollectionPhotoList(id)
                .map(new HttpResultFunction<List<PhotoBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /****************************************UserService*******************************************/
    public void getUserInfo(HttpObserver<UserBean> observer, String username) {
        getUserService().getUserInfo(username)
                .map(new HttpResultFunction<UserBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getUserPhotoList(HttpObserver<List<PhotoBean>> observer, String username) {
        getUserService().getUserPhotoList(username)
                .map(new HttpResultFunction<List<PhotoBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getUserCollectionList(HttpObserver<List<CollectionBean>> observer, String username) {
        getUserService().getUserCollectionList(username)
                .map(new HttpResultFunction<List<CollectionBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}