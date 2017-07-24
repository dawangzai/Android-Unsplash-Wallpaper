package com.wangzai.http.download;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.cleverzheng.wallpaper.http.HttpClientManager;

import java.io.File;

/**
 * Created by wangzai on 2017/7/4.
 */

public class DownloadFileUtil {
    public static String getFileName(String photoId) {
        if (!TextUtils.isEmpty(photoId)) {
            return photoId;
        }

        return System.currentTimeMillis() + "";
    }

    public static String getDefaultFilePath(Context context) {
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/Android/data/"
                + context.getPackageName()
                + "/picture/";
        File file = new File(filePath);
        if (!file.exists()) {
            boolean createDir = file.mkdirs();
            if (createDir) {
//                if (HttpClientManager.getInstance().isDebug()) {
                    Log.d("EasyDownloadTask", "create file dir success");
//                }
            }
        }
        return filePath;
    }
}
