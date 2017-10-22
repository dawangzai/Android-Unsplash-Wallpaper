package com.wangzai.lovesy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wangzai.lovesy.account.AccountManager;
import com.wangzai.lovesy.account.IUserChecker;
import com.wangzai.lovesy.api.ApiService;
import com.wangzai.lovesy.core.activity.LoveSyActivity;
import com.wangzai.lovesy.core.app.LoveSy;
import com.wangzai.lovesy.core.net.rx.RxHttpClient;
import com.wangzai.lovesy.core.net.rx.observer.ResultObserver;
import com.wangzai.lovesy.utils.activity.ActivityUtil;

public class MainActivity extends LoveSyActivity implements IUserChecker {

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState) {
        getWindow().getDecorView().
                setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        AccountManager.checkAccount(this);
    }

    @Override
    public void onSignIn() {
        //查用户信息
        RxHttpClient.builder()
                .url(ApiService.Me.ME)
                .build()
                .get()
                .subscribe(new ResultObserver() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull String result) {
                        AccountManager.setUserInfo(result);
                        ActivityUtil.startHomeActivity(MainActivity.this);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        ActivityUtil.startHomeActivity(MainActivity.this);
                    }
                });
    }


    @Override
    public void onNotSignIn() {
        //给启动页两秒
        LoveSy.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityUtil.startHomeActivity(MainActivity.this);
            }
        }, 2000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
