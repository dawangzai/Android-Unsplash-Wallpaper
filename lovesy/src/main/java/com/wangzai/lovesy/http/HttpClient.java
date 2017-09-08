package com.wangzai.lovesy.http;

import android.content.Context;
import android.os.Environment;

import com.wangzai.lovesy.BuildConfig;
import com.wangzai.lovesy.LoveSyApp;
import com.wangzai.lovesy.bean.AccessToken;
import com.wangzai.lovesy.bean.CollectionBean;
import com.wangzai.lovesy.bean.LinksBean;
import com.wangzai.lovesy.bean.PhotoBean;
import com.wangzai.lovesy.bean.UserBean;
import com.wangzai.lovesy.http.api.CollectionService;
import com.wangzai.lovesy.http.api.LoginService;
import com.wangzai.lovesy.http.api.PhotoService;
import com.wangzai.lovesy.http.api.UserService;
import com.wangzai.lovesy.http.download.DownloadManager;
import com.wangzai.lovesy.http.exception.NetworkException;
import com.wangzai.lovesy.http.interceptor.CacheInterceptor;
import com.wangzai.lovesy.http.interceptor.HeaderInterceptor;
import com.wangzai.lovesy.http.observer.HttpObserver;
import com.wangzai.lovesy.http.utils.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
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
    private static LoginService mLoginService;

    public static void initDownloadEnvironment(Context context, int threadCount) {
        DownloadManager.getInstance().init(context, threadCount);
    }

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

    public LoginService getLoginService() {
        return mLoginService == null ? configRetrofit(LoginService.class) : mLoginService;
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
        okHttpClient.addInterceptor(new HeaderInterceptor());
        okHttpClient.addInterceptor(new CacheInterceptor());
        okHttpClient.addNetworkInterceptor(new CacheInterceptor());
//        if (NetworkUtil.isConnected()) {
//            if (NetworkUtil.isAvailableByPing()) {
//                okHttpClient.addNetworkInterceptor(new NetworkCacheInterceptor());
//            } else {
//                okHttpClient.addInterceptor(new NetworkCacheInterceptor());
////                okHttpClient.addNetworkInterceptor(new NetworkCacheInterceptor());
//            }
//        } else {
//            okHttpClient.addInterceptor(new NetworkCacheInterceptor());
//        }
        okHttpClient.cache(cacheConfig());
//        okHttpClient.build().newCall().execute()
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
        //缓存目录 \data\data\包名\cache\api_cache
        File cacheDir = new File(LoveSyApp.getInstance().getCacheDir(), "api_cache");
        //缓存空间 10M
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(cacheDir, cacheSize);
        return cache;
    }

    private class HttpFunction implements Function<Observable<? extends Throwable>, Observable<?>> {
        private int maxRetries = 2; //重试两次
        private int retryDelayMillis = 2; //延迟两秒重试
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

    public void login(HttpObserver<AccessToken> observer, String code) {
        getLoginService().getAccessToken(
                "b05bfc46a0de4842346cb5ce7c766b3a8c9da071ec77f3b5f719406829c2fb31",
                "af3b7125ce78c9a05bac4f9b9f216260919c3646eaf02ea9f36f0f10b014a965",
                "http://dawangzai.com",
                code, "authorization_code")
                .retryWhen(new HttpFunction())
                .map(new HttpResultFunction<AccessToken>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getAccessCode(HttpObserver<AccessToken> observer) {
        getLoginService().getAuthorizationCode("b05bfc46a0de4842346cb5ce7c766b3a8c9da071ec77f3b5f719406829c2fb31",
                "http://dawangzai.com",
                "code",
                "public+read_user+write_user+read_collections+write_collections")
                .retryWhen(new HttpFunction())
                .map(new HttpResultFunction<AccessToken>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


    /****************************************download***********************************************/
    public void downloadFile() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = builder
                .readTimeout(5, TimeUnit.MILLISECONDS)
                .writeTimeout(5, TimeUnit.MILLISECONDS)
                .connectTimeout(5, TimeUnit.MILLISECONDS)
                .build();
        String url = "https://images.unsplash.com/photo-1421899528807-04d925f39555?\n" +
                "ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&dl=cesar-lopez-rivadeneira-6088.jpg&s=03b3dd99abb6821e65e46a201a76ce0a";
        Request request = new Request.Builder().url(url).addHeader("Authorization", "Client-ID " + BuildConfig.CLIENT_ID).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.i("download", "失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                InputStream inputStream = response.body().byteStream();
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    final long total = response.body().contentLength();

                    long sum = 0;
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wallpaper";
                    File dir = new File(path);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File file = new File(dir, "download34.jpg");
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        sum += len;
                        fos.write(buf, 0, len);
//                        final long finalSum = sum;
//                        OkHttpUtils.getInstance().getDelivery().execute(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                inProgress(finalSum * 1.0f / total, total, id);
//                            }
//                        });
                    }
                    fos.flush();

                } finally {
                    try {
                        response.body().close();
                        if (is != null) is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null) fos.close();
                    } catch (IOException e) {
                    }

                }
            }
        });
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

    public void getPhotoDownload(HttpObserver<LinksBean> observer, String id) {
        getPhotoService().getPhotoDownload(id)
                .map(new HttpResultFunction<LinksBean>())
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

    public void getSingleCollection(HttpObserver<CollectionBean> observer, int id) {
        getCollectionService().getSingleCollection(id)
                .map(new HttpResultFunction<CollectionBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getCollectionPhotoList(HttpObserver<List<PhotoBean>> observer, int id) {
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
