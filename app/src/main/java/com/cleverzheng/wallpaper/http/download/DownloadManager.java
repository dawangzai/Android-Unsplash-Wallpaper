package com.cleverzheng.wallpaper.http.download;

import android.os.Environment;

import com.cleverzheng.wallpaper.utils.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by wangzai on 2017/6/27.
 */

public class DownloadManager {
    private static DownloadManager instances;
    private OkHttpClient mClient;

    private DownloadManager() {
        mClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)//写入超时
                .readTimeout(30, TimeUnit.SECONDS)//读取超时
                .build();
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

    public void downloadFile() {
        Request request = new Request.Builder().url("http://img.mukewang.com/55249cf30001ae8a06000338.jpg").build();

        mClient.newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());

                return response.newBuilder().body(new DownloadResponseBody(response.body())).build();
            }
        });

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.i("download", "onFailure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wallpaper";
                File filePath = new File(path, "test.jpg");
//                if (!filePath.exists()) {
//                    filePath.mkdirs();
//                }
                LogUtil.i("download", "onResponse");
                ResponseBody body = response.body();
                LogUtil.i("download", body.toString());
//                byte[] buffer = new byte[2048];
//                InputStream is = response.body().byteStream();
//                int len;
//                FileOutputStream fos = new FileOutputStream(filePath);
//                while ((len = is.read(buffer)) != -1) {
//                    fos.write(buffer, 0, len);
//                }
//                fos.flush();
//                if (is != null) {
//                    is.close();
//                }
//                if (fos != null) {
//                    fos.close();
//                }

                BufferedSink sink = Okio.buffer(Okio.sink(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/wallpaper" + System.currentTimeMillis())));
                sink.writeAll(response.body().source());
                sink.close();
            }
        });

    }
}
