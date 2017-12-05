package com.wangzai.lovesy.core.download;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.wangzai.lovesy.core.download.entities.FileEntity;
import com.wangzai.lovesy.core.util.LogUtil;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wangzai on 2017/11/30
 */

public class DownloadService extends IntentService {

    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final String ACTION_FINISH = "ACTION_FINISH";

    public static final String EXTRA_FILE = "EXTRA_FILE";
    public static final String EXTRA_UPDATE = "EXTRA_UPDATE";
    public static final String EXTRA_ID = "EXTRA_ID";

    public DownloadService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final FileEntity fileEntity = (FileEntity) intent.getSerializableExtra(MultiThreadDownload.EXTRA_FILE);
//            new DownloadHandler(this, fileEntity);
            setFileLength(fileEntity);
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
            File dir = new File(fileEntity.getDir());
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(dir, fileEntity.getFileName());
            LogUtil.i(file.getAbsolutePath());

            raf = new RandomAccessFile(file, "rwd");
            raf.setLength(length);
            fileEntity.setLength(length);

            // TODO: 2017/11/30 开始下载
            new DownloadHandler(this, fileEntity);
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
