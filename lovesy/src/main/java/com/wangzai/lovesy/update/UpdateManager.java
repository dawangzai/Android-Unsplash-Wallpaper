package com.wangzai.lovesy.update;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;

import com.alibaba.fastjson.JSON;
import com.wangzai.lovesy.R;
import com.wangzai.lovesy.bean.VersionBean;
import com.wangzai.lovesy.core.net.rx.RxHttpClient;
import com.wangzai.lovesy.core.net.rx.observer.ResultObserver;
import com.wangzai.lovesy.core.notification.LoveSyNotification;

import java.lang.reflect.Method;

/**
 * Created by wangzai on 2017/10/20
 */

public class UpdateManager {
    private final String mVersionInfo;
    private final Context mContext;
    private String installUrl;

    public UpdateManager(Context context, String versionInfo) {
        this.mContext = context;
        this.mVersionInfo = versionInfo;
    }

    public void update() {
        final VersionBean versionBean = JSON.parseObject(mVersionInfo, VersionBean.class);
        installUrl = versionBean.getInstall_url();
        showUpdateDialog(versionBean.getChangelog());
    }

    private void showUpdateDialog(String message) {
        new AlertDialog.Builder(mContext)
                .setTitle("版本更新")
                .setMessage(message)
                .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //检查网络
                        if (getDataEnabled()) {
                            showNetworkDialog();
                        } else {
                            //下载
                            downloadApk();
                        }
                    }
                })
                .setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
    }

    private void showNetworkDialog() {
        new AlertDialog.Builder(mContext)
                .setTitle("版本更新")
                .setMessage("现在下载将使用流量，是否现在下载")
                .setPositiveButton("立即下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        downloadApk();
                    }
                })
                .setNegativeButton("取消下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();
    }

    private void downloadApk() {

        LoveSyNotification.builder()
                .id(0)
                .title("下载更新")
                .text("正在下载")
                .smallIcon(R.mipmap.ic_launcher)
                .context(mContext)
                .build()
                .notification();

        RxHttpClient.builder()
                .url(installUrl)
                .build()
                .download()
                .subscribe(new ResultObserver() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull String result) {

                        LoveSyNotification.builder()
                                .id(0)
                                .title("下载更新")
                                .text("完成下载")
                                .smallIcon(R.mipmap.ic_launcher)
                                .context(mContext)
                                .build()
                                .notification();
                    }

                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });
    }

    /**
     * 检查是否是移动网络
     *
     * @return
     */
    private boolean getDataEnabled() {
        try {
            TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            Method getMobileDataEnabledMethod = tm.getClass().getDeclaredMethod("getDataEnabled");
            if (null != getMobileDataEnabledMethod) {
                return (boolean) getMobileDataEnabledMethod.invoke(tm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
