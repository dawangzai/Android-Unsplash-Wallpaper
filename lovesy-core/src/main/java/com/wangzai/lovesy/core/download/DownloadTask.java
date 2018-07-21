package com.wangzai.lovesy.core.download;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.wangzai.lovesy.core.download.callback.IDownloadListener;
import com.wangzai.lovesy.core.download.callback.IError;
import com.wangzai.lovesy.core.download.callback.IFailure;
import com.wangzai.lovesy.core.download.callback.IFileListener;
import com.wangzai.lovesy.core.download.callback.IProgress;
import com.wangzai.lovesy.core.download.db.thread.ThreadDao;
import com.wangzai.lovesy.core.download.db.thread.ThreadDaoImpl;
import com.wangzai.lovesy.core.download.entities.FileInfo;
import com.wangzai.lovesy.core.download.entities.ThreadInfo;
import com.wangzai.lovesy.core.download.thread.DownloadThread;
import com.wangzai.lovesy.core.download.thread.FileLengthThread;
import com.wangzai.lovesy.core.util.LogUtil;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by wangzai on 2017/12/4
 */

public class DownloadTask implements IDownloadListener, IFileListener {
    private int mProgress;
    private volatile boolean isFinished = false;
    private volatile boolean isPause = false;
    private ThreadDao mThreadDao;
    private Intent mIntent;
    private ExecutorService mExecutorService;

    private final FileInfo mFileInfo;
    private final IProgress mIprogress;
    private final IError mIError;
    private final IFailure mIfailure;

    private DownloadTask(FileInfo fileInfo,
                         IProgress progress,
                         IError error,
                         IFailure failure,
                         ExecutorService executorService,
                         ThreadDao threadDao) {
        this.mFileInfo = fileInfo;
        this.mIprogress = progress;
        this.mIError = error;
        this.mIfailure = failure;
        this.mExecutorService = executorService;
        this.mThreadDao = threadDao;
    }

    public static DownloadTask create(FileInfo fileInfo,
                                      IProgress progress,
                                      IError error,
                                      IFailure failure,
                                      ExecutorService executorService,
                                      ThreadDao threadDao) {
        return new DownloadTask(fileInfo, progress, error, failure, executorService, threadDao);
    }

    public void start() {
        final int fileLength = mFileInfo.getLength();
        if (fileLength <= 0) {
            getFileLength();
        } else {
            createDownloadThread();
        }
    }

    private void createDownloadThread() {
        final List<ThreadInfo> threadList = mThreadDao.queryAllThread(mFileInfo.getUrl());
        LogUtil.i("ThreadList=" + threadList.size());
        if (threadList.size() == 0) {
            //获取每个线程的下载长度
            final int threadCount = mFileInfo.getThreadCount();
            int length = mFileInfo.getLength() / threadCount;
            for (int i = 0; i < threadCount; i++) {
                ThreadInfo threadInfo = new ThreadInfo(i, mFileInfo.getUrl(), length * i, length * (i + 1) - 1, 0);
                if (i == threadCount - 1) {
                    threadInfo.setEnd(mFileInfo.getLength());
                }
                threadList.add(threadInfo);

                mThreadDao.insertThread(threadInfo);
            }
        }

        for (ThreadInfo threadInfo : threadList) {
            final DownloadThread downloadThread = new DownloadThread(threadInfo, mThreadDao, mFileInfo, this);
//            downloadThread.start();
//            mExecutor.execute(downloadThread);
            mExecutorService.submit(downloadThread);
        }
    }

    private void getFileLength() {
        final FileLengthThread fileLengthThread = new FileLengthThread(mFileInfo, this);
        mExecutorService.submit(fileLengthThread);
    }

    public void pause() {
        isPause = true;
    }

    @Override
    public void onFileLength(int length) {
        mFileInfo.setLength(length);
        createDownloadThread();
    }

    @Override
    public void onProgress(int progress) {
        mProgress += progress;
        int finished = mProgress * 100 / mFileInfo.getLength();
        mIntent.setAction(MultiThreadDownload.ACTION_UPDATE);
        mIntent.putExtra(MultiThreadDownload.EXTRA_UPDATE, finished);
        mIntent.putExtra(MultiThreadDownload.EXTRA_FILE_ID, mFileInfo.getId());
//        LocalBroadcastManager.getInstance(mContext).sendBroadcast(mIntent);
        LogUtil.i("进度=" + mProgress);
    }

    @Override
    public void onFinished() {
        checkAllThreadFinished();
    }

    @Override
    public boolean onPause() {
        return isPause;
    }

    private void checkAllThreadFinished() {
        final List<ThreadInfo> threadEntities = mThreadDao.queryAllThread(mFileInfo.getUrl());
        if (threadEntities.size() == 0) {
            mIntent.setAction(MultiThreadDownload.ACTION_FINISH);
            mIntent.putExtra(MultiThreadDownload.EXTRA_FILE_ID, mFileInfo.getId());
//            LocalBroadcastManager.getInstance(mContext).sendBroadcast(mIntent);
            LogUtil.i("下载结束");
        }
    }
}
