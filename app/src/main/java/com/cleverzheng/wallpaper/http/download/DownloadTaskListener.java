package com.cleverzheng.wallpaper.http.download;

/**
 * Created by wangzai on 2017/7/4.
 */

public interface DownloadTaskListener {
    /**
     * queue
     *
     * @param downloadTask
     */
    void onQueue(DownloadTask downloadTask);

    /**
     * connecting
     *
     * @param downloadTask
     */
    void onConnecting(DownloadTask downloadTask);

    /**
     * downloading
     *
     * @param downloadTask
     */
    void onDownloading(DownloadTask downloadTask);

    /**
     * pauseTask
     *
     * @param downloadTask
     */
    void onPause(DownloadTask downloadTask);

    /**
     * cancel
     *
     * @param downloadTask
     */
    void onCancel(DownloadTask downloadTask);

    /**
     * success
     *
     * @param downloadTask
     */
    void onFinish(DownloadTask downloadTask);

    /**
     * failure
     *
     * @param downloadTask
     */
    void onError(DownloadTask downloadTask, int code);
}
