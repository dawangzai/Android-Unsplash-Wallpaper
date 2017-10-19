package com.wangzai.lovesy.account;

import com.wangzai.lovesy.core.util.storage.LoveSyPreference;

/**
 * Created by wangzai on 2017/9/19
 */

public class AccountManager {

    public static String mUserInfo;

    private enum SignTag {
        /**
         * 标记登录状态
         */
        SIGN_TAG
    }

    /**
     * 保存用户登录状态
     *
     * @param state 登录状态值
     */
    public static void setSignState(boolean state) {
        LoveSyPreference.setAppFlag(SignTag.SIGN_TAG.name(), state);
    }

    /**
     * 保存登录 token
     *
     * @param token
     */
    public static void setAccessToken(String token) {
        LoveSyPreference.addCustomAppProfile("access_token", token);
    }

    /**
     * 保存查询到的用户信息
     *
     * @param userInfo
     */
    public static void setUserInfo(String userInfo) {
        mUserInfo = userInfo;
    }

    /**
     * 检查用户登录状态
     *
     * @param checker 回调
     */
    public static void checkAccount(IUserChecker checker) {
        if (isSignIn()) {
            checker.onSignIn();
        } else {
            checker.onNotSignIn();
        }
    }

    public static boolean isSignIn() {
        return LoveSyPreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

}
