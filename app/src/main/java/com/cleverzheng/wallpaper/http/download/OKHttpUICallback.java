package com.cleverzheng.wallpaper.http.download;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by wangzai on 2017/6/29.
 */

public class OKHttpUICallback {
    /**
     * 带有进度的上传、下载回调接口
     */
    public interface ProgressCallback{
        void onSuccess(Call call, Response response, String path);
        void onProgress(long byteReadOrWrite, long contentLength, boolean done);
        void onError(Call call, IOException e);
    }
}
