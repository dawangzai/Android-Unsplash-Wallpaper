package com.wangzai.lovesy.core.download;

import android.content.Intent;

import com.wangzai.lovesy.core.download.db.ThreadDao;
import com.wangzai.lovesy.core.net.download.entities.ThreadEntity;

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

    private ThreadEntity mThreadEntity;
    private ThreadDao mThreadDao;

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
            int start = mThreadEntity.getStart() + mThreadEntity.getFinished();
            conn.setRequestProperty("Range", "bytes=" + start + "-" + mThreadEntity.getEnd());
            //设置写入位置
            File file = new File(DownloadService.DOWNLOAD_PATH, mFile.getFileName());
            raf = new RandomAccessFile(file, "rwd");
            raf.seek(start);
            mFinished += mThreadEntity.getFinished();

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
                    mThreadEntity.setFinished(mThreadEntity.getFinished() + len);
                    //下载进度更新
//                        if (System.currentTimeMillis() - time > 500) {
                    time = System.currentTimeMillis();
                    intent.putExtra(DownloadService.EXTRA_UPDATE, mFinished * 100 / mFile.getLength());
                    intent.putExtra(DownloadService.EXTRA_ID, mFile.getId());
                    mContext.sendBroadcast(intent);
//                        }

                    //暂停下载
                    if (isPause) {
                        mThreadDao.updateThread(mThreadEntity.getUrl(), mThreadEntity.getId(), mThreadEntity.getFinished());
                        return;
                    }
                }

                isFinished = true;

                checkAllThreadFinished();
            }
        } catch (IOException e) {
            try {
                if (is != null) {
                    is.close();
                }
                if (raf != null) {
                    raf.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
