package com.cleverzheng.wallpaper.http;

import com.cleverzheng.wallpaper.BuildConfig;
import com.cleverzheng.wallpaper.WallpaperApplication;
import com.cleverzheng.wallpaper.bean.CollectionBean;
import com.cleverzheng.wallpaper.bean.PhotoBean;
import com.cleverzheng.wallpaper.bean.UserBean;
import com.cleverzheng.wallpaper.http.api.CollectionService;
import com.cleverzheng.wallpaper.http.api.PhotoService;
import com.cleverzheng.wallpaper.http.api.UserService;
import com.cleverzheng.wallpaper.http.exception.NetworkException;
import com.cleverzheng.wallpaper.http.interceptor.NetworkCacheInterceptor;
import com.cleverzheng.wallpaper.http.interceptor.NetworkRetryInterceptor;
import com.cleverzheng.wallpaper.http.observer.HttpObserver;
import com.cleverzheng.wallpaper.utils.LogUtil;
import com.cleverzheng.wallpaper.utils.NetworkUtil;
import com.cleverzheng.wallpaper.utils.ToastUtil;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

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

//        okHttpClient.addInterceptor(new NetworkRetryInterceptor(2));
        if (NetworkUtil.isConnected()) {
            if (NetworkUtil.isAvailableByPing()) {
                okHttpClient.addNetworkInterceptor(new NetworkCacheInterceptor());
            } else {
                okHttpClient.addInterceptor(new NetworkCacheInterceptor());
            }
        } else {
            okHttpClient.addInterceptor(new NetworkCacheInterceptor());
        }
        okHttpClient.cache(cacheConfig());

        return okHttpClient.build();
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
                LogUtil.i("WallpaperLog", message);
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

    private class HttpFunction implements Function<Observable<? extends Throwable>, Observable<?>> {
        private int maxRetries = 2;
        private int retryDelayMillis;
        private int retryCount;

        @Override
        public Observable<?> apply(@NonNull Observable<? extends Throwable> observable) throws Exception {
            return observable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                @Override
                public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                    if (++retryCount <= maxRetries) {
                        LogUtil.i("WallpaperLog", "重试" + retryCount);
                        return Observable.timer(retryDelayMillis, TimeUnit.MILLISECONDS);
                    }
                    // Max retries hit. Just pass the error along.
                    return Observable.error(throwable);
                }
            });
        }
    }

    private class HttpResultFunction<T> implements Function<retrofit2.Response<T>, T> {
        @Override
        public T apply(@NonNull retrofit2.Response<T> response) throws Exception {
            int code = response.code();
            if (code == 200) {
                return response.body();
            } else {
                LogUtil.i("WallpaperLog", response.message());
                throw new NetworkException(code, NetworkException.EXCEPTION_MESSAGE_UNKNOWN);
            }
        }
    }

    /****************************************PhotoService*******************************************/
    public void getNewestPhotoList(HttpObserver<List<PhotoBean>> observer, int page, int pre_page) {
        getPhotoService().getNewestPhotoList(page, pre_page)
                .retryWhen(new HttpFunction())
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
