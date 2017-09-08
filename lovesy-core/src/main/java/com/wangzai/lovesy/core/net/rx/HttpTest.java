package com.wangzai.lovesy.core.net.rx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wangzai.lovesy.core.net.*;
import com.wangzai.lovesy.core.net.callback.ISuccess;

import java.util.WeakHashMap;


/**
 * Created by wangzai on 2017/9/5
 */

public class HttpTest extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WeakHashMap<String, Object> params = new WeakHashMap<>();
        params.put("page", 1);
        params.put("per_page", 10);

//        RxHttpClient.builder()
//                .url("photos/")
//                .params(params)
//                .build()
//                .get()
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(@NonNull String s) throws Exception {
//
//                    }
//                });

        com.wangzai.lovesy.core.net.HttpClient.builder()
                .url("photos/")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.i("LoveSyDebug", response);
                    }
                })
                .build()
                .get();
    }
}
