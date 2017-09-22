package com.wangzai.lovesy.mvp.base;

/**
 * Created by wangzai on 2017/2/12.
 */
public interface BaseView<T> {
    void setPresent(T present);

    void loadDataSuccess();

    void loadDataFailed(String message);
}
