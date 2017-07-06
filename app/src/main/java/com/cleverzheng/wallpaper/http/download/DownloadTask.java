package com.cleverzheng.wallpaper.http.download;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wangzai on 2017/7/4.
 */

public class DownloadTask implements Runnable {
    private OkHttpClient mClient;
    private TaskEntity mTaskEntity;
    private DownloadTaskListener mDownloadTaskListener;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (mDownloadTaskListener == null) {
                return;
            }
            int code = msg.what;
            switch (code) {
                case TaskStatus.TASK_STATUS_INIT:
                    break;
                case TaskStatus.TASK_STATUS_QUEUE:
                    mDownloadTaskListener.onQueue(DownloadTask.this);
                    break;
                case TaskStatus.TASK_STATUS_CONNECTING:
                    mDownloadTaskListener.onConnecting(DownloadTask.this);
                    break;
                case TaskStatus.TASK_STATUS_DOWNLOADING:
                    mDownloadTaskListener.onDownloading(DownloadTask.this);
                    break;
                case TaskStatus.TASK_STATUS_PAUSE:
                    mDownloadTaskListener.onPause(DownloadTask.this);
                    break;
                case TaskStatus.TASK_STATUS_CANCEL:
                    mDownloadTaskListener.onCancel(DownloadTask.this);
                    break;
                case TaskStatus.TASK_STATUS_LINK_FAILURE_ERROR:
                    mDownloadTaskListener.onError(DownloadTask.this, TaskStatus.TASK_STATUS_LINK_FAILURE_ERROR);
                    break;
                case TaskStatus.TASK_STATUS_REQUEST_ERROR:
                    mDownloadTaskListener.onError(DownloadTask.this, TaskStatus.TASK_STATUS_REQUEST_ERROR);
                    break;
                case TaskStatus.TASK_STATUS_STORAGE_ERROR:
                    mDownloadTaskListener.onError(DownloadTask.this, TaskStatus.TASK_STATUS_STORAGE_ERROR);
                    break;
                case TaskStatus.TASK_STATUS_FINISH:
                    mDownloadTaskListener.onFinish(DownloadTask.this);
                    break;
                default:
                    break;
            }
        }
    };

    public void setClient(OkHttpClient client) {
        this.mClient = client;
    }

    @Override
    public void run() {
//        Request request = new Request();
//        try {
//            Response execute = mClient.newCall(request).execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public TaskEntity getTaskEntity() {
        return mTaskEntity;
    }

    public void init() {
        mTaskEntity.setTaskStatus(TaskStatus.TASK_STATUS_INIT);
        mHandler.sendEmptyMessage(TaskStatus.TASK_STATUS_INIT);
    }

    public void queue() {
        mTaskEntity.setTaskStatus(TaskStatus.TASK_STATUS_QUEUE);
        mHandler.sendEmptyMessage(TaskStatus.TASK_STATUS_QUEUE);
    }
}
