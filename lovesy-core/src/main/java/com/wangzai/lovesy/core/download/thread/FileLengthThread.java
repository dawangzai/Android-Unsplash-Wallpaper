package com.wangzai.lovesy.core.download.thread;

import com.wangzai.lovesy.core.download.callback.IFileListener;
import com.wangzai.lovesy.core.download.entities.FileInfo;
import com.wangzai.lovesy.core.util.LogUtil;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wangzai on 2017/12/27
 */

public class FileLengthThread implements Runnable {

    private final FileInfo mFileInfo;
    private final IFileListener mFileListener;

    public FileLengthThread(FileInfo fileInfo, IFileListener fileListener) {
        this.mFileInfo = fileInfo;
        this.mFileListener = fileListener;
    }

    @Override
    public void run() {
        HttpURLConnection conn = null;
        RandomAccessFile raf = null;
        try {
            URL url = new URL(mFileInfo.getUrl());
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setRequestMethod("GET");
            int length = -1;
            if (conn.getResponseCode() == 200) {
                length = conn.getContentLength();
            }
            if (length <= 0) {
                return;
            }
            LogUtil.i("FileLength=" + length);
            File dir = new File(mFileInfo.getDir());
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(dir, mFileInfo.getFileName());
            LogUtil.i(file.getAbsolutePath());

            raf = new RandomAccessFile(file, "rwd");
            raf.setLength(length);
//            mFileInfo.setLength(length);
            mFileListener.onFileLength(length);
            LogUtil.i(mFileInfo.toString());


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (raf != null) {
                    raf.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
