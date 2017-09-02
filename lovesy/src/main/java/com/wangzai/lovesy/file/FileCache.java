package com.wangzai.lovesy.file;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by wangzai on 2017/7/3.
 */

public class FileCache implements CacheFunction {
    private static String cacheRoot;

    public static void install(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            // SD-card available
            cacheRoot = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/"
                    + context.getPackageName() + "/cache";
            File file = new File(cacheRoot);
            file.mkdirs();
        } else {
            cacheRoot = context.getCacheDir().getAbsolutePath();
        }
    }

    @Override
    public <T> T get(String key) {
        return null;
    }

    @Override
    public <T> T get(String key, T defaultValue) {
        return null;
    }

    @Override
    public <T> void put(String key, T value) {

    }

    @Override
    public int remove(String key) {
        return 0;
    }

    @Override
    public boolean exists(String key) {
        return false;
    }
}
