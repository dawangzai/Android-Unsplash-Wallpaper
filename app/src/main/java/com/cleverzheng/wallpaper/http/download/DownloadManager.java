package com.cleverzheng.wallpaper.http.download;

/**
 * Created by wangzai on 2017/6/27.
 */

public class DownloadManager {
    private static DownloadManager instances;

    private DownloadManager() {
    }

    private static DownloadManager initDownloadManager() {
        if (instances == null) {
            synchronized (DownloadManager.class) {
                if (instances == null) {
                    instances = new DownloadManager();
                }
            }
        }
        return instances;
    }

    public static DownloadManager getInstances() {
        return initDownloadManager();
    }
}
