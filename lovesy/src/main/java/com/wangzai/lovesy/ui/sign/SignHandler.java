package com.wangzai.lovesy.ui.sign;

import com.wangzai.lovesy.core.app.AccountManager;

/**
 * Created by wangzai on 2017/9/19
 */

class SignHandler {
    static void onSignIn(ISignListener signListener) {
        AccountManager.setSignState(true);
        if (signListener != null) {
            signListener.onSignInSuccess();
        }
    }
}
