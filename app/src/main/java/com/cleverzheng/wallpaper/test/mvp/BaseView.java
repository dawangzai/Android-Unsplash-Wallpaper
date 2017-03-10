package com.cleverzheng.wallpaper.test.mvp;

/**
 * @author：cleverzheng
 * @date：2017/1/23:11:24
 * @email：zhengwang043@gmail.com
 * @description：MVP框架View层的基类,提供一些加载动画，以及加载错误的操作
 */

public interface BaseView {
    void showMessage(String msg);

    void close();

    void showProgress(String msg);

    void showProgress(String msg, int progress);

    void hideProgress();

    void showErrorMessage(String msg, String content);
}
