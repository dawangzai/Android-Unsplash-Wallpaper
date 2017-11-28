package com.wangzai.lovesy.ui.update.version;

/**
 * Created by wangzai on 2017/10/19
 */

public interface IVersionChecker {

    void onNotLatest(String versionInfo);
    void onLatest();
}
