package com.cleverzheng.wallpaper.test;

/**
 * @author：cleverzheng
 * @date：2017/1/23:10:53
 * @email：zhengwang043@gmail.com
 * @description：
 */

public class DataManager {

    MvpModel dataSource;

    public DataManager(MvpModel dataSource) {
        this.dataSource = dataSource;
    }

    public String getShowContent() {
        return dataSource.getStringFromCache() + dataSource.getStringFromRemote();
    }
}
