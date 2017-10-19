package com.wangzai.lovesy.core.uptate;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

/**
 * Created by wangzai on 2017/10/19
 */

public class UpdtaeManager {

    private final String mInstallUrl;
    private final NotificationCompat mNotification;
    private Context mContext;
    private AlertDialog mUptateDialog;
    private AlertDialog mNetworkDialog;

    public UpdtaeManager(String installUrl, NotificationCompat notification) {
        this.mInstallUrl = installUrl;
        this.mNotification = notification;
    }



    private void initUptateDialog(String message) {
        mUptateDialog = new AlertDialog
                .Builder(mContext)
                .setTitle("版本更新")
                .setMessage(message)
                .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //检查网络
                        if (getDataEnabled()) {
                            initNetworkDialog();
                        } else {
                            //下载
                        }
                    }
                })
                .setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();

    }

    private void initNetworkDialog() {
        mNetworkDialog = new AlertDialog
                .Builder(mContext)
                .setTitle("版本更新")
                .setMessage("网络提醒")
                .setPositiveButton("立即下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //检查网络
                    }
                })
                .setNegativeButton("取消下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
    }

    private void downloadApk() {

    }

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
