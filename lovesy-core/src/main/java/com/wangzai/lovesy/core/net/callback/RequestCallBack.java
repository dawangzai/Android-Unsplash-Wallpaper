package com.wangzai.lovesy.core.net.callback;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.wangzai.lovesy.core.app.LoveSy;

import java.net.SocketTimeoutException;

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
    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
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
    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
        if (mFailure != null) {
            if (t instanceof SocketTimeoutException) {
                mFailure.onFailure("连接超时，请稍后重试");
                Toast.makeText(LoveSy.getApplicationContext(), "连接超时，请稍后重试", Toast.LENGTH_SHORT).show();
            } else {
                mFailure.onFailure("网络错误，请稍后重试");
                Toast.makeText(LoveSy.getApplicationContext(), "网络错误，请稍后重试", Toast.LENGTH_SHORT).show();
            }
        }

        onRequestFinish();
    }

    private void onRequestFinish() {
//        //清空请求参数容器，供下次请求使用
//        HttpCreator.getParams().clear();
        if (mRequest != null) {
            mRequest.onRequestEnd();
        }
    }
}
