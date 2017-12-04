package com.wangzai.lovesy.core.net.download.services;

import android.content.Context;
import android.content.Intent;

import com.wangzai.lovesy.core.net.download.db.ThreadDAO;
import com.wangzai.lovesy.core.net.download.db.ThreadDAOImpl;
import com.wangzai.lovesy.core.net.download.entities.FileEntity;
import com.wangzai.lovesy.core.net.download.entities.ThreadEntity;
import com.wangzai.lovesy.core.util.LogUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wangzai on 2017/11/28
 */

public class DownloadTask {

    private Context mContext;
    private FileEntity mFile;
    private ThreadDAO mThreadDAO;
    private int mFinished;
    public boolean isPause = false;
    private int mThreadCount = 1;
    private List<DownloadThread> mThreadList;
    public static ExecutorService sExecutor = Executors.newCachedThreadPool();

    public DownloadTask(Context context, FileEntity file, int threadCount) {
        this.mContext = context;
        this.mFile = file;
        this.mThreadCount = threadCount;
        mThreadDAO = new ThreadDAOImpl(mContext);
    }

    public void download() {
        LogUtil.i("开始下载");
        final List<ThreadEntity> allThread = mThreadDAO.queryAllThread(mFile.getUrl());
        if (allThread.size() == 0) {
//            threadEntity = new ThreadEntity(0, mFile.getUrl(), 0, mFile.getLength(), 0);
            //获取每个线程的下载长度
            int length = mFile.getLength() / mThreadCount;
            for (int i = 0; i < mThreadCount; i++) {
                ThreadEntity threadEntity = new ThreadEntity(i, mFile.getUrl(), length * i, length * (i + 1) - 1, 0);
                if (i == mThreadCount - 1) {
                    threadEntity.setEnd(mFile.getLength());
                }
                allThread.add(threadEntity);

                mThreadDAO.insertThread(threadEntity);
            }
        }

        mThreadList = new ArrayList<>();

        for (ThreadEntity threadEntity : allThread) {
            final DownloadThread downloadThread = new DownloadThread(threadEntity);
//            downloadThread.start();
            DownloadTask.sExecutor.execute(downloadThread);

            mThreadList.add(downloadThread);
        }
    }

    private synchronized void checkAllThreadFinished() {
        boolean allFinished = true;
        for (DownloadThread downloadThread : mThreadList) {
            if (!downloadThread.isFinished) {
                allFinished = false;
                break;
            }
        }

        if (allFinished) {
            //通知下载结束
            Intent intent = new Intent(DownloadService.ACTION_FINISH);
            intent.putExtra(DownloadService.EXTRA_UPDATE, mFile);
            mContext.sendBroadcast(intent);

            //下载完成删除线程信息
            mThreadDAO.deleteAllThread(mFile.getUrl());
        }
    }

    class DownloadThread extends Thread {
        private ThreadEntity mThread;
        //标识线程是否执行结束
        public boolean isFinished;

        public DownloadThread(ThreadEntity threadEntity) {
            this.mThread = threadEntity;
        }

        @Override
        public void run() {
            super.run();
            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            InputStream is = null;
            try {
                final URL url = new URL(mThread.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                //设置下载位置
                int start = mThread.getStart() + mThread.getProgress();
                conn.setRequestProperty("Range", "bytes=" + start + "-" + mThread.getEnd());
                //设置写入位置
                File file = new File(DownloadService.DOWNLOAD_PATH, mFile.getFileName());
                raf = new RandomAccessFile(file, "rwd");
                raf.seek(start);
                mFinished += mThread.getProgress();

                if (conn.getResponseCode() == 206) {
                    is = conn.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int len = -1;
                    long time = System.currentTimeMillis();
                    Intent intent = new Intent(DownloadService.ACTION_UPDATE);
                    while ((len = is.read(buffer)) != -1) {
                        raf.write(buffer, 0, len);
                        mFinished += len;
                        //累加每个线程的完成进度
                        mThread.setProgress(mThread.getProgress() + len);
                        //下载进度更新
//                        if (System.currentTimeMillis() - time > 500) {
                            time = System.currentTimeMillis();
                            intent.putExtra(DownloadService.EXTRA_UPDATE, mFinished * 100 / mFile.getLength());
                            intent.putExtra(DownloadService.EXTRA_ID, mFile.getId());
                            mContext.sendBroadcast(intent);
//                        }

                        //暂停下载
                        if (isPause) {
                            mThreadDAO.updateThread(mThread.getUrl(), mThread.getId(), mThread.getProgress());
                            return;
                        }
                    }

                    isFinished = true;

                    checkAllThreadFinished();
                }
            } catch (IOException e) {
                try {
                    is.close();
                    raf.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }

}
