package com.wangzai.lovesy.core.download;

import android.content.Context;

import com.wangzai.lovesy.core.app.LoveSy;
import com.wangzai.lovesy.core.download.callback.IError;
import com.wangzai.lovesy.core.download.callback.IFailure;
import com.wangzai.lovesy.core.download.callback.IProgress;
import com.wangzai.lovesy.core.download.db.file.FileDao;
import com.wangzai.lovesy.core.download.db.file.FileDaoImpl;
import com.wangzai.lovesy.core.download.db.thread.ThreadDao;
import com.wangzai.lovesy.core.download.db.thread.ThreadDaoImpl;
import com.wangzai.lovesy.core.download.entities.FileInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wangzai on 2017/12/27
 */

public class DownloadManager {

    private static DownloadManager sDownloadManager;
    private static final List<FileInfo> FILE_INFOS = DownloadService.getFiles();
    private static final ConcurrentHashMap<Integer, DownloadTask> DOWNLOAD_TASKS = DownloadService.getTasks();
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(3);
    private FileDao mFileDao;
    private ThreadDao mThreadDao;

    private DownloadManager() {
        mThreadDao = new ThreadDaoImpl(LoveSy.getApplicationContext());
    }

    public static DownloadManager getInstance() {
        synchronized (DownloadManager.class) {
            if (sDownloadManager == null) {
                sDownloadManager = new DownloadManager();
            }
            return sDownloadManager;
        }
    }

    public void prepareDownload() {

    }

    public void download(FileInfo fileInfo, IProgress progress, IError error, IFailure failure) {
        //判断 FILE_INFOS 中是否存在
        final DownloadTask downloadTask = DownloadTask.create(fileInfo, progress, error, failure, mExecutorService, mThreadDao);
        downloadTask.start();
        //存在直接取
    }

    public void pause(FileInfo fileInfo) {
    }

    public void delete(FileInfo fileInfo) {

    }


}
