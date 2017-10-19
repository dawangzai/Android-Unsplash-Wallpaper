package com.wangzai.lovesy.version;

/**
 * Created by wangzai on 2017/10/19
 */

public interface IVersionChecker {

    void onLatest();

    void onNotLatest(String versionInfo);
}
