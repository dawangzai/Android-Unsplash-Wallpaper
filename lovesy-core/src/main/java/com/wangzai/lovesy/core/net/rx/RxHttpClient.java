package com.wangzai.lovesy.core.net.rx;

import android.content.Context;

import com.wangzai.lovesy.core.net.HttpCreator;
import com.wangzai.lovesy.core.net.HttpMethod;
import com.wangzai.lovesy.core.net.rx.lift.DownloadTransformer;
import com.wangzai.lovesy.core.net.rx.lift.Transformer;
import com.wangzai.lovesy.core.ui.loader.LoaderStyle;

import java.io.InputStream;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by wangzai on 2017/9/5
 */

public class RxHttpClient {
    private static final HashMap<String, Object> PARAMS = HttpCreator.getParams();
    private final String mUrl;
    private final RequestBody mBody;
    private final Context mContext;
    private final LoaderStyle mLoaderStyle;

    RxHttpClient(String url, RequestBody body, Context context, LoaderStyle loaderStyle) {
        this.mUrl = url;
        this.mBody = body;
        this.mContext = context;
        this.mLoaderStyle = loaderStyle;
    }

    public static RxHttpClientBuilder builder() {
        return new RxHttpClientBuilder();
    }

    private Observable<String> request(HttpMethod method) {
        final RxHttpService service = HttpCreator.getRxHttpService();
        Observable<String> observable = null;

        switch (method) {
            case GET:
                observable = service.get(mUrl, PARAMS);
                break;
            case POST:
                observable = service.post(mUrl, PARAMS);
                break;
            case POST_RAW:
                observable = service.postRaw(mUrl, mBody);
                break;
            case PUT:
                observable = service.put(mUrl, PARAMS);
                break;
            case PUT_RAW:
                observable = service.putRaw(mUrl, mBody);
                break;
            case DELETE:
                observable = service.put(mUrl, PARAMS);
                break;
            default:
                break;
        }
        return observable;
    }

    public final Observable<String> get() {
        return getObservable(HttpMethod.GET);
    }

    public final Observable<String> post() {
        if (mBody == null) {
            return getObservable(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null !");
            }
            return getObservable(HttpMethod.POST_RAW);
        }
    }

    public final Observable<String> put() {
        if (mBody == null) {
            return getObservable(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) {
                throw new RuntimeException("params must be null !");
            }
            return getObservable(HttpMethod.PUT_RAW);
        }
    }

    public final Observable<Long> download() {
        return HttpCreator.getRxHttpService()
                .download(mUrl, PARAMS)
                .compose(new DownloadTransformer());
    }

    private Observable<String> getObservable(HttpMethod method) {
        return request(method).compose(new Transformer(mContext, mLoaderStyle));
    }
}
