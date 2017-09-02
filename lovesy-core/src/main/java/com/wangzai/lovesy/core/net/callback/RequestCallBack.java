package com.wangzai.lovesy.core.net.callback;

import com.wangzai.lovesy.core.net.HttpCreator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wangzai on 2017/8/18
 */

public class RequestCallBack implements Callback<String> {
    private final IRequest mRequest;
    private final ISuccess mSuccess;
    private final IError mError;
    private final IFailure mFailure;

    public RequestCallBack(
            IRequest request,
            ISuccess success,
            IError error,
            IFailure failure) {
        this.mRequest = request;
        this.mSuccess = success;
        this.mError = error;
        this.mFailure = failure;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) {
            if (call.isExecuted()) {
                if (mSuccess != null) {
                    mSuccess.onSuccess(response.body());
                }
            }
        } else {
            if (mError != null) {
                int code = response.code();
                if (code >= 500 && code < 600) {
                    mError.onError(code, "服务端错误，请稍后再试");
                } else if (code >= 400 && code < 500) {
                    mError.onError(code, "客户端错误，请稍后再试");
                } else {
                    String message = response.message();
                    if (message != null) {
                        mError.onError(code, message);
                    }
                }
            }
        }

        onRequestFinish();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (mFailure != null) {
            mFailure.onFailure("网络不可用！请检查您的网络设置");
        }

        onRequestFinish();
    }

    private void onRequestFinish() {
        //清空请求参数容器，供下次请求使用
        HttpCreator.getParams().clear();
        if (mRequest != null) {
            mRequest.onRequestEnd();
        }
    }
}
