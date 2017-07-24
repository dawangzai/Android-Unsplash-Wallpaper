package com.cleverzheng.wallpaper.http.utils;


/**
 * Created by wangzai on 2017/7/24.
 */

public class NetworkUtil {
    /**
     * 判断网络是否可用
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     *
     * @return {@code true}: 可用<br>{@code false}: 不可用
     */
    public static boolean isAvailableByPing() {
        ShellUtil.CommandResult result = ShellUtil.execCmd("ping -c 1 -w 1 223.5.5.5", false);
        boolean ret = result.result == 0;
        if (result.errorMsg != null) {
            LogUtil.d("isAvailableByPing errorMsg", result.errorMsg);
        }
        if (result.successMsg != null) {
            LogUtil.d("isAvailableByPing successMsg", result.successMsg);
        }
        return ret;
    }
}
