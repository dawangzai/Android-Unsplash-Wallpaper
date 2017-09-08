package com.wangzai.lovesy;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wangzai.lovesy.base.BaseActivity;
import com.wangzai.lovesy.http.HttpClient;
import com.wangzai.lovesy.http.callback.OnResultCallback;
import com.wangzai.lovesy.http.observer.HttpObserver;

/**
 * Created by wangzai on 2017/9/7
 */

public class HttpTestActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        @SuppressWarnings("unchecked") HttpObserver observer = new HttpObserver(new OnResultCallback() {
            @Override
            public void onSuccess(Object o) {

            }

            @Override
            public void onFailed(int code, String message) {

            }
        });
        HttpClient.getInstance().getSinglePhoto(observer, "pvZrGsVbLd8");
    }
}
