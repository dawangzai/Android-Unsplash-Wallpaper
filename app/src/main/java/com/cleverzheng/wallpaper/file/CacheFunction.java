package com.cleverzheng.wallpaper.file;

/**
 * Created by wangzai on 2017/7/3.
 */

public interface CacheFunction {
    <T> T get(String key);

    <T> T get(String key, T defaultValue);

    <T> void put(String key, T value);

    int remove(String key);

    boolean exists(String key);
}
