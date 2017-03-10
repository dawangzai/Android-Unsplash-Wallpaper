package com.cleverzheng.wallpaper.test.mvp;

/**
 * @author：cleverzheng
 * @date：2017/1/23:11:27
 * @email：zhengwang043@gmail.com
 * @description：MVP框架Presenter的基类， 提供关联View的方法。
 */

public interface BasePresenter<V extends BaseView> {
    void attachView(V view);

    void detachView();
}
