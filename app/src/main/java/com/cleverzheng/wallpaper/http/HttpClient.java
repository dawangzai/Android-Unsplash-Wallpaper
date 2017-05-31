package com.cleverzheng.wallpaper.http;

import android.util.Log;

import com.cleverzheng.wallpaper.BuildConfig;
import com.cleverzheng.wallpaper.WallpaperApplication;
import com.cleverzheng.wallpaper.bean.CollectionBean;
import com.cleverzheng.wallpaper.bean.PhotoBean;
import com.cleverzheng.wallpaper.bean.UserBean;
import com.cleverzheng.wallpaper.http.api.CollectionService;
import com.cleverzheng.wallpaper.http.api.PhotoService;
import com.cleverzheng.wallpaper.http.api.UserService;
import com.cleverzheng.wallpaper.http.exception.NetworkException;
import com.cleverzheng.wallpaper.http.observer.HttpObserver;
import com.cleverzheng.wallpaper.utils.NetworkUtil;
import com.cleverzheng.wallpaper.utils.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CacheControl;
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
                .baseUrl(BuildConfig.BASE_URL)
                .client(configClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return mRetrofit.create(service);
    }

    private OkHttpClient configClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS); //设置超时时间
        if (BuildConfig.LOG_DEBUG) {
            okHttpClient.addInterceptor(logConfig());
        }
        okHttpClient.addNetworkInterceptor(interceptorConfig());
        okHttpClient.cache(cacheConfig());

        return okHttpClient.build();
    }

    /**
     * 配置的拦截器
     *
     * @return
     */
    private Interceptor interceptorConfig() {
        Interceptor headerIntercept = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                CacheControl.Builder cacheBuilder = new CacheControl.Builder();
                cacheBuilder.maxAge(0, TimeUnit.SECONDS);
                cacheBuilder.maxStale(365,TimeUnit.DAYS);
                CacheControl cacheControl = cacheBuilder.build();

                Request.Builder builder = chain.request().newBuilder();
                if (NetworkUtil.isConnected()) {
                    builder.addHeader("Authorization", "Client-ID " + BuildConfig.CLIENT_ID);
                } else {
                    builder.cacheControl(cacheControl);
                    ToastUtil.showShortSafe("暂无网络");//子线程安全显示Toast
                }

                Response response = chain.proceed(builder.build());
                if (NetworkUtil.isConnected()) {
                    int maxAge = 60 * 60; // read from cache for 1 minute
                    response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                } else {
                    int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                    response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                }
                return response;
            }
        };
        return headerIntercept;
    }

    /**
     * 打印日志配置
     *
     * @return
     */
    private HttpLoggingInterceptor logConfig() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("test", "retrofitBack = " + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    /**
     * 缓存配置
     *
     * @return
     */
    private Cache cacheConfig() {
        File cacheDir = new File(WallpaperApplication.getInstance().getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(cacheDir, cacheSize);
        return cache;
    }

    private class HttpResultFunction<T> implements Function<retrofit2.Response<T>, T> {
        @Override
        public T apply(@NonNull retrofit2.Response<T> response) throws Exception {
            int code = response.code();
            if (code == 200) {
                return response.body();
            } else {
                throw new NetworkException(code, response.message());
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
