package com.wangzai.lovesy.core.net.download.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.wangzai.lovesy.core.net.download.entities.FileEntity;
import com.wangzai.lovesy.core.util.LogUtil;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by wangzai on 2017/11/28
 */

public class DownloadService extends Service {

    public static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().
            getAbsolutePath() + "/download";

    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final String ACTION_FINISH = "ACTION_FINISH";

    public static final String EXTRA_FILE = "EXTRA_FILE";
    public static final String EXTRA_UPDATE = "EXTRA_UPDATE";
    public static final String EXTRA_ID = "EXTRA_ID";

    public static final int MSG_INIT = 0x1;

    //下载任务集合
    private Map<Integer, DownloadTask> mTasks = new LinkedHashMap<>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (ACTION_START.equals(intent.getAction())) {
            FileEntity file = (FileEntity) intent.getSerializableExtra(EXTRA_FILE);
            LogUtil.i(file.toString());
//            DownloadTask.sExecutor.execute(new InitThread(file));
            new InitThread(file).start();
        } else if (ACTION_STOP.equals(intent.getAction())) {
            FileEntity file = (FileEntity) intent.getSerializableExtra(EXTRA_FILE);
            LogUtil.i(file.toString());
            DownloadTask task = mTasks.get(file.getId());
            if (task != null) {
                task.isPause = true;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_INIT:
                    FileEntity fileEntity = (FileEntity) msg.obj;
                    LogUtil.i(fileEntity.toString());
                    //启动下载任务
                    DownloadTask task = new DownloadTask(DownloadService.this, fileEntity, 3);
                    task.download();
                    mTasks.put(fileEntity.getId(), task);
                    break;
                default:
                    break;
            }
        }
    };

    class InitThread extends Thread {

        private FileEntity mFile = null;
        private RandomAccessFile raf = null;

        public InitThread(FileEntity file) {
            this.mFile = file;
        }

        @SuppressWarnings("ResultOfMethodCallIgnored")
        @Override
        public void run() {
//            super.run();
            HttpURLConnection conn = null;
            try {
                URL url = new URL(mFile.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                int lenght = -1;
                if (conn.getResponseCode() == 200) {
                    lenght = conn.getContentLength();
                }
                if (lenght <= 0) {
                    return;
                }
                File dir = new File(DOWNLOAD_PATH);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                File file = new File(dir, mFile.getFileName());
                LogUtil.i(file.getAbsolutePath());

                raf = new RandomAccessFile(file, "rwd");
                raf.setLength(lenght);
                mFile.setLength(lenght);
                mHandler.obtainMessage(MSG_INIT, mFile).sendToTarget();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    raf.close();
                    if (conn != null) {
                        conn.disconnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
