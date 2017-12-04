package com.wangzai.lovesy.core.download;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.wangzai.lovesy.core.download.callback.IDownloadListener;
import com.wangzai.lovesy.core.download.db.ThreadDao;
import com.wangzai.lovesy.core.download.db.ThreadDaoImpl;
import com.wangzai.lovesy.core.download.entities.FileEntity;
import com.wangzai.lovesy.core.net.download.entities.ThreadEntity;

import java.util.List;

/**
 * Created by wangzai on 2017/12/4
 */

public class DownloadHandler implements IDownloadListener {

    private final Context mContext;
    private final FileEntity mFileEntity;
    private final Handler mHandler;

    private int mProgress;
    private volatile boolean isFinished;
    private ThreadDao mThreadDao;

    public DownloadHandler(Context context, FileEntity fileEntity, Handler handler) {
        this.mFileEntity = fileEntity;
        this.mHandler = handler;
        this.mContext = context;
        mThreadDao = new ThreadDaoImpl(mContext);
    }

    private void download() {
        final List<ThreadEntity> threadList = mThreadDao.queryAllThread(mFileEntity.getUrl());
        if (threadList.size() == 0) {
            //获取每个线程的下载长度
            final int threadCount = mFileEntity.getThreadCount();
            int length = mFileEntity.getLength() / threadCount;
            for (int i = 0; i < threadCount; i++) {
                ThreadEntity threadEntity = new ThreadEntity(i, mFileEntity.getUrl(), length * i, length * (i + 1) - 1, 0);
                if (i == threadCount - 1) {
                    threadEntity.setEnd(mFileEntity.getLength());
                }
                threadList.add(threadEntity);

                mThreadDao.insertThread(threadEntity);
            }
        }

        for (ThreadEntity threadEntity : threadList) {
            final DownloadThread downloadThread = new DownloadThread(threadEntity);
            execute(downloadThread);
        }
    }

    @Override
    public void onProgress(int progress) {

    }

    @Override
    public void onFinished() {

    }

    @Override
    public boolean onPause() {
        return false;
    }

    private synchronized void checkAllThreadFinished() {
        final List<ThreadEntity> threadEntities = mThreadDao.queryAllThread(mFileEntity.getUrl());
        if (threadEntities == null) {
            // TODO: 2017/12/4 下载完成，通知
        }
    }

}
