package com.wangzai.lovesy.core.download;

import com.wangzai.lovesy.core.download.callback.IDownloadListener;
import com.wangzai.lovesy.core.download.db.ThreadDao;
import com.wangzai.lovesy.core.download.entities.FileEntity;
import com.wangzai.lovesy.core.download.entities.ThreadEntity;

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

    private final ThreadEntity mThreadEntity;
    private final ThreadDao mThreadDao;
    private final FileEntity mFileEntity;
    private final IDownloadListener mListener;

    public DownloadThread(
            ThreadEntity threadEntity,
            ThreadDao threadDao,
            FileEntity fileEntity,
            IDownloadListener listener) {
        this.mThreadEntity = threadEntity;
        this.mThreadDao = threadDao;
        this.mFileEntity = fileEntity;
        this.mListener = listener;
    }

    @Override
    public void run() {
        HttpURLConnection conn = null;
        RandomAccessFile raf = null;
        InputStream is = null;
        try {
            final URL url = new URL(mThreadEntity.getUrl());
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setRequestMethod("GET");
            //设置下载位置
            int start = mThreadEntity.getStart() + mThreadEntity.getProgress();
            conn.setRequestProperty("Range", "bytes=" + start + "-" + mThreadEntity.getEnd());
            //设置写入位置
            File file = new File(mFileEntity.getDir(), mFileEntity.getFileName());
            raf = new RandomAccessFile(file, "rwd");
            raf.seek(start);

            mListener.onProgress(mThreadEntity.getProgress());

            if (conn.getResponseCode() == 206) {
                is = conn.getInputStream();
                byte[] buffer = new byte[1024 * 4];
                int len = -1;
                while ((len = is.read(buffer)) != -1) {
                    raf.write(buffer, 0, len);

                    //累加每个线程的完成进度
                    mThreadEntity.setProgress(mThreadEntity.getProgress() + len);
                    mListener.onProgress(len);

                    //暂停下载
                    if (mListener.onPause()) {
                        mThreadDao.updateThread(mThreadEntity.getUrl(), mThreadEntity.getId(), mThreadEntity.getProgress());
                        return;
                    }
                }

                mThreadDao.deleteThread(mThreadEntity.getUrl(), mThreadEntity.getId());
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
}
