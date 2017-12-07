package com.wangzai.lovesy.core.download;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.wangzai.lovesy.core.download.entities.FileEntity;
import com.wangzai.lovesy.core.util.LogUtil;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by wangzai on 2017/11/30
 */

public class DownloadService extends IntentService {

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private Map<Integer, DownloadHandler> mDownloads = new LinkedHashMap<>();
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final FileEntity fileEntity = (FileEntity) intent.getSerializableExtra(MultiThreadDownload.EXTRA_FILE);
            final DownloadHandler handler = mDownloads.get(fileEntity.getId());
            if (MultiThreadDownload.ACTION_START.equals(intent.getAction())) {
                if (handler == null) {
                    setFileLength(fileEntity);
                } else {
                    handler.download();
                }
            } else if (MultiThreadDownload.ACTION_STOP.equals(intent.getAction())) {
                if (handler != null) {
                    LogUtil.i("暂停DownloadHandler=" + mDownloads.toString());
                    handler.pause();
                }
            }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void setFileLength(FileEntity fileEntity) {
        HttpURLConnection conn = null;
        RandomAccessFile raf = null;
        try {
            URL url = new URL(fileEntity.getUrl());
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setRequestMethod("GET");
            int length = -1;
            if (conn.getResponseCode() == 200) {
                length = conn.getContentLength();
            }
            if (length <= 0) {
                return;
            }
            LogUtil.i("FileLength=" + length);
            File dir = new File(fileEntity.getDir());
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(dir, fileEntity.getFileName());
            LogUtil.i(file.getAbsolutePath());

            raf = new RandomAccessFile(file, "rwd");
            raf.setLength(length);
            fileEntity.setLength(length);

            LogUtil.i(fileEntity.toString());

            final DownloadHandler downloadHandler = new DownloadHandler(this, fileEntity);
            mDownloads.put(fileEntity.getId(), downloadHandler);
            LogUtil.i(mDownloads.get(fileEntity.getId()).toString());
            downloadHandler.download();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
