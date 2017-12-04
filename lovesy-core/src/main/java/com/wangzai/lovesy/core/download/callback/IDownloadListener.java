package com.wangzai.lovesy.core.download.callback;

/**
 * Created by wangzai on 2017/12/4
 */

public interface IDownloadListener {

    void onProgress(int progress);

    void onFinished();

    boolean onPause();
}
