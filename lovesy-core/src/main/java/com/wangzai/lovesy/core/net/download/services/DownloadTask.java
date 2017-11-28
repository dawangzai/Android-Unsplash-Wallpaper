package com.wangzai.lovesy.core.net.download.services;

import android.content.Context;

import com.facebook.datasource.FirstAvailableDataSourceSupplier;
import com.wangzai.lovesy.core.net.download.db.ThreadDAO;
import com.wangzai.lovesy.core.net.download.db.ThreadDAOImpl;
import com.wangzai.lovesy.core.net.download.entities.FileEntity;
import com.wangzai.lovesy.core.net.download.entities.ThreadEntity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

    public DownloadTask(Context context, FileEntity file, int threadCount) {
        this.mContext = context;
        this.mFile = file;
        this.mThreadCount = threadCount;
        mThreadDAO = new ThreadDAOImpl(mContext);
    }

    public void download() {
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
            }
        }

        mThreadList = new ArrayList<>();

        for (ThreadEntity threadEntity : allThread) {
            final DownloadThread downloadThread = new DownloadThread(threadEntity);
            downloadThread.start();

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
            // TODO: 2017/11/28
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

            //向数据库插入线程信息
            if (!mThreadDAO.isThreadExists(mFile.getUrl(), mFile.getId())) {
                mThreadDAO.insertThread(mThread);
            }

            HttpURLConnection conn = null;
            try {
                final URL url = new URL(mThread.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");

                //设置下载位置
                int start = mThread.getStart() + mThread.getFinished();
                conn.setRequestProperty("Range", "bytes=" + start + "-" + mThread.getEnd());

                //设置写入位置
                File file = new File(DownloadService.DOWNLOAD_PATH, mFile.getFileName());
                RandomAccessFile raf = new RandomAccessFile(file, "raw");
                raf.seek(start);

                mFinished += mThread.getFinished();

                if (conn.getResponseCode() == 206) {
                    final InputStream inputStream = conn.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int len = -1;
                    while ((len = inputStream.read(buffer)) != -1) {
                        raf.write(buffer, 0, len);
                        //下载进度更新
                        // TODO: 2017/11/28
                        mFinished += len;
                        //累加每个线程的完成进度
                        mThread.setFinished(mThread.getFinished() + len);
                        //暂停下载
                        if (isPause) {
                            mThreadDAO.updateThread(mThread.getUrl(), mThread.getId(), mThread.getFinished());
                            return;
                        }
                    }

                    isFinished = true;

                    //下载完成删除线程信息
                    mThreadDAO.deleteThread(mThread.getUrl(), mThread.getId());
                    checkAllThreadFinished();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
