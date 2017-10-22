package com.wangzai.lovesy.update.version;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.wangzai.lovesy.BuildConfig;
import com.wangzai.lovesy.api.ApiService;
import com.wangzai.lovesy.core.app.LoveSy;
import com.wangzai.lovesy.core.net.rx.RxHttpClient;
import com.wangzai.lovesy.core.net.rx.observer.ResultObserver;

import java.util.HashMap;

import io.reactivex.annotations.NonNull;

/**
 * Created by wangzai on 2017/10/19
 */

public class VersionManager {

    public static void checkVersion(final IVersionChecker checker) {
        HashMap<String, Object> params = new HashMap<>(16);
        params.put("bundle_id", BuildConfig.BUNDLE_ID);
        params.put("api_token",BuildConfig.API_TOKEN);
        params.put("type","android");
        RxHttpClient.builder()
                .url(ApiService.Version.VERSION)
                .params(params)
                .build()
                .get()
                .subscribe(new ResultObserver() {
                    @SuppressWarnings("ConstantConditions")
                    @Override
                    public void onSuccess(@NonNull String result) {
                        if (currentVersion() == null) {
                            return;
                        }
                        if (currentVersion().equals(result)) {
                            checker.onLatest();
                        } else {
                            checker.onNotLatest(result);
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });
    }

    private static String currentVersion() {
        final String packageName = LoveSy.getApplicationContext().getPackageName();
        try {
            PackageInfo packageInfo = LoveSy.getApplicationContext().getPackageManager().getPackageInfo(packageName, 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }
}
