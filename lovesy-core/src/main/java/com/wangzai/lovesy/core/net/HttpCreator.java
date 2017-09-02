package com.wangzai.lovesy.core.net;

import com.wangzai.lovesy.core.app.ConfigKeys;
import com.wangzai.lovesy.core.app.LoveSy;
import com.wangzai.lovesy.core.net.cookie.HttpCookieJar;
import com.wangzai.lovesy.core.net.interceptor.BaseInterceptor;
import com.wangzai.lovesy.core.net.interceptor.InterceptorType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by wangzai on 2017/8/18
 */

public class HttpCreator {

    /**
     * Http 请求的参数容器。
     * 一个请求结束之后会将其清空供下一次请求使用
     */
    private static final class ParamsHolder {
        private static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    }

    /**
     * Http 请求的拦截器
     */
    private static final class InterceptorHolder {
        private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();
    }

    /**
     * Http 请求的 Cookie 容器
     */
    private static final class CookieHolder {
        private static final HashMap<HttpUrl, List<Cookie>> COOKIES = new HashMap<>();

    }

    /**
     * 构建 OkHttp Client
     */
    private static final class OkHttpHolder {
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();

        private static OkHttpClient.Builder addInterceptors() {
            if (!getInterceptors().isEmpty()) {
                for (Interceptor interceptor : getInterceptors()) {
                    if (interceptor.getClass().getSimpleName().equals("HttpLoggingInterceptor")) {
                        BUILDER.addNetworkInterceptor(interceptor);
                        continue;
                    }
                    InterceptorType type = ((BaseInterceptor) interceptor).getInterceptorType();
                    switch (type) {
                        case INTERCEPTOR:
                            BUILDER.addInterceptor(interceptor);
                            break;
                        case NETWORK_INTERCEPTOR:
                            BUILDER.addNetworkInterceptor(interceptor);
                            break;
                        default:
                            break;
                    }
                }
            }
            return BUILDER;
        }

        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptors()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .cookieJar(new HttpCookieJar())
//                .sslSocketFactory(HttpsManager.getFactory(), (X509TrustManager) HttpsManager.getTrustManager())
//                .hostnameVerifier(HttpsManager.getVerifier())
                .build();
    }

    /**
     * 构建 Retrofit
     */
    private static final class RetrofitHolder {
        private static final String BASE_URL = LoveSy.getConfiguration(ConfigKeys.API_HOST);
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    /**
     * 创建 Service
     */
    private static class ServiceHolder {
        private static final HttpService HTTP_SERVICE = RetrofitHolder.RETROFIT_CLIENT.create(HttpService.class);
    }

    static HttpService getHttpService() {
        return ServiceHolder.HTTP_SERVICE;
    }

    public static WeakHashMap<String, Object> getParams() {
        return ParamsHolder.PARAMS;
    }

    public static ArrayList<Interceptor> getInterceptors() {
        return InterceptorHolder.INTERCEPTORS;
    }

    public static HashMap<HttpUrl, List<Cookie>> getCookies() {
        return CookieHolder.COOKIES;
    }
}
