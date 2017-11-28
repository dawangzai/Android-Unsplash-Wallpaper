package com.wangzai.lovesy.core.net;

import com.wangzai.lovesy.core.net.callback.IError;
import com.wangzai.lovesy.core.net.callback.IFailure;
import com.wangzai.lovesy.core.net.callback.IRequest;
import com.wangzai.lovesy.core.net.callback.ISuccess;
import com.wangzai.lovesy.core.net.callback.RequestCallBack;

import java.util.HashMap;
import java.util.WeakHashMap;

import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by wangzai on 2017/8/18
 */

public class HttpClient {

    private final WeakHashMap<String, Object> mParams;
    private final String mUrl;
    private final RequestBody mBody;
    private final IRequest mRequest;
    private final ISuccess mSuccess;
    private final IError mError;
    private final IFailure mFailure;
//    private final Context mContext;

    public HttpClient(
            String url,
            WeakHashMap<String, Object> params,
            RequestBody body,
            IRequest request,
            ISuccess success,
            IError error,
            IFailure failure
//            Context context
    ) {
        this.mUrl = url;
        this.mParams = params;
        this.mBody = body;
        this.mRequest = request;
        this.mSuccess = success;
        this.mError = error;
        this.mFailure = failure;
    }

    public static HttpClientBuilder builder() {
        return new HttpClientBuilder();
    }

    private void request(HttpMethod method) {
        final HttpService service = HttpCreator.getHttpService();
        Call<String> call = null;

        if (mRequest != null) {
            mRequest.onRequestStart();
        }

        switch (method) {
            case GET:
                call = service.get(mUrl, mParams);
                break;
            case POST:
                call = service.post(mUrl, mParams);
                break;
            case POST_RAW:
                call = service.postRaw(mUrl, mBody);
                break;
            case PUT:
                call = service.put(mUrl, mParams);
                break;
            case PUT_RAW:
                call = service.putRaw(mUrl, mBody);
                break;
            case DELETE:
                call = service.put(mUrl, mParams);
                break;
            default:
                break;
        }

        if (call != null) {
            call.enqueue(getRequestCallBack());
        }
    }

    private RequestCallBack getRequestCallBack() {
        return new RequestCallBack(
                mRequest,
                mSuccess,
                mError,
                mFailure);
    }

    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        if (mBody == null) {
            request(HttpMethod.POST);
        } else {
            if (!mParams.isEmpty()) {
                throw new RuntimeException("params must be null !");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put() {
        if (mBody == null) {
            request(HttpMethod.PUT);
        } else {
            if (!mParams.isEmpty()) {
                throw new RuntimeException("params must be null !");
            }
            request(HttpMethod.PUT_RAW);
        }
    }
}
