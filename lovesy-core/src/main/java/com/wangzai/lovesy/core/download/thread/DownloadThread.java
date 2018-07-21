package com.wangzai.lovesy.core.download.thread;

import com.wangzai.lovesy.core.download.callback.IDownloadListener;
import com.wangzai.lovesy.core.download.db.thread.ThreadDao;
import com.wangzai.lovesy.core.download.entities.FileInfo;
import com.wangzai.lovesy.core.download.entities.ThreadInfo;
import com.wangzai.lovesy.core.util.LogUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wangzai on 2017/11/30
 */

public class DownloadThread extends Thread {

    private final ThreadInfo mThreadInfo;
    private final ThreadDao mThreadDao;
    private final FileInfo mFileInfo;
    private final IDownloadListener mListener;

    public DownloadThread(
            ThreadInfo threadInfo,
            ThreadDao threadDao,
            FileInfo fileInfo,
            IDownloadListener listener) {
        this.mThreadInfo = threadInfo;
        this.mThreadDao = threadDao;
        this.mFileInfo = fileInfo;
        this.mListener = listener;
    }

    @Override
    public void run() {
        HttpURLConnection conn = null;
        RandomAccessFile raf = null;
        InputStream is = null;
        try {
            final URL url = new URL(mThreadInfo.getUrl());
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setRequestMethod("GET");
            //设置下载位置
            int start = mThreadInfo.getStart() + mThreadInfo.getProgress();
            conn.setRequestProperty("Range", "bytes=" + start + "-" + mThreadInfo.getEnd());
            //设置写入位置
            File file = new File(mFileInfo.getDir(), mFileInfo.getFileName());
            raf = new RandomAccessFile(file, "rwd");
            raf.seek(start);

            mListener.onProgress(mThreadInfo.getProgress());

            if (conn.getResponseCode() == 206) {
                is = conn.getInputStream();
                byte[] buffer = new byte[1024 * 4];
                int len = -1;
                while ((len = is.read(buffer)) != -1) {
                    //暂停下载
//                    if (mListener.onPause()) {
//                        mThreadDao.updateThread(mThreadInfo.getUrl(), mThreadInfo.getId(), mThreadInfo.getProgress());
//                        return;
//                    }
                    checkPause();

                    raf.write(buffer, 0, len);
                    //累加每个线程的完成进度
                    mThreadInfo.setProgress(mThreadInfo.getProgress() + len);
                    mListener.onProgress(len);

                }

                mThreadDao.deleteThread(mThreadInfo.getUrl(), mThreadInfo.getId());
                LogUtil.i("删除线程==" + mThreadInfo.getId());
                mListener.onFinished();
            }
        } catch (IOException e) {
            try {
                if (is != null) {
                    is.close();
                }
                if (raf != null) {
                    raf.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void checkPause() {
        if (mFileInfo.isPause()) {
            mListener.onPause();
            return;
        }
    }
}
