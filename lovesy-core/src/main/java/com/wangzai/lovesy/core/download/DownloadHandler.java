package com.wangzai.lovesy.core.download;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.wangzai.lovesy.core.download.callback.IDownloadListener;
import com.wangzai.lovesy.core.download.db.ThreadDao;
import com.wangzai.lovesy.core.download.db.ThreadDaoImpl;
import com.wangzai.lovesy.core.download.entities.FileEntity;
import com.wangzai.lovesy.core.download.entities.ThreadEntity;
import com.wangzai.lovesy.core.util.LogUtil;

import java.util.List;

/**
 * Created by wangzai on 2017/12/4
 */

public class DownloadHandler implements IDownloadListener {

    private final Context mContext;
    private final FileEntity mFileEntity;

    private int mProgress;
    private volatile boolean isFinished = false;
    private volatile boolean isPause = false;
    private ThreadDao mThreadDao;
    private Intent mIntent;

    public DownloadHandler(Context context, FileEntity fileEntity) {
        this.mFileEntity = fileEntity;
        this.mContext = context;
        mThreadDao = new ThreadDaoImpl(mContext);
        mIntent = new Intent();
    }

    public void download() {
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
            final DownloadThread downloadThread = new DownloadThread(threadEntity, mThreadDao, mFileEntity, this);
            downloadThread.start();
//            execute(downloadThread);
        }
    }

    public void pause() {
        isPause = true;
    }

    @Override
    public synchronized void onProgress(int progress) {
        mProgress += progress;
        int finished = mProgress * 100 / mFileEntity.getLength();
        mIntent.setAction(MultiThreadDownload.ACTION_UPDATE);
        mIntent.putExtra(MultiThreadDownload.EXTRA_UPDATE, finished);
        mIntent.putExtra(MultiThreadDownload.EXTRA_FILE_ID, mFileEntity.getId());
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(mIntent);
        LogUtil.i("进度=" + mProgress);
    }

    @Override
    public synchronized void onFinished() {
        checkAllThreadFinished();
    }

    @Override
    public synchronized boolean onPause() {
        return isPause;
    }

    private synchronized void checkAllThreadFinished() {
        final List<ThreadEntity> threadEntities = mThreadDao.queryAllThread(mFileEntity.getUrl());
        if (threadEntities.size() == 0) {
            mIntent.setAction(MultiThreadDownload.ACTION_FINISH);
            mIntent.putExtra(MultiThreadDownload.EXTRA_FILE_ID, mFileEntity.getId());
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(mIntent);
            LogUtil.i("下载结束");
        }
    }

}
