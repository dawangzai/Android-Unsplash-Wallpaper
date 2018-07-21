package com.wangzai.lovesy.core.download;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.wangzai.lovesy.core.download.callback.IError;
import com.wangzai.lovesy.core.download.callback.IFailure;
import com.wangzai.lovesy.core.download.callback.IProgress;
import com.wangzai.lovesy.core.download.entities.FileInfo;
import com.wangzai.lovesy.core.util.StringUtil;
import com.wangzai.lovesy.core.util.file.FileUtil;

/**
 * Created by wangzai on 2017/11/30
 */

public class MultiThreadDownload {
    private static final String DIR_IMAGE = Environment.getExternalStorageDirectory().
            getAbsolutePath() + "/lovesy";

    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final String ACTION_FINISH = "ACTION_FINISH";

    public static final String EXTRA_FILE = "EXTRA_FILE";
    public static final String EXTRA_UPDATE = "EXTRA_UPDATE";
    public static final String EXTRA_FILE_ID = "EXTRA_FILE_ID";

    private final String mUrl;
    private final String mFileName;
    private final String mDir;
    private final int mThreadCount;
    private final Context mContext;

    private final IProgress mIProgress;
    private final IError mIError;
    private final IFailure mIFailure;

    private FileInfo mFileInfo;

    private MultiThreadDownload(Context context,
                                String url,
                                String fileName,
                                String dir,
                                int threadCount,
                                IProgress progress,
                                IError error,
                                IFailure failure) {
        this.mUrl = url;
        this.mFileName = fileName;
        this.mDir = dir;
        this.mThreadCount = threadCount;
        this.mContext = context;
        this.mIProgress = progress;
        this.mIError = error;
        this.mIFailure = failure;

        initFile();
    }

    public static Builder builder() {
        return new Builder();
    }

    private void initFile() {
        mFileInfo = new FileInfo(mUrl.hashCode(), mUrl, mDir, mFileName, mThreadCount, 0, 0);
    }

    public void download() {

        DownloadService.getDownloadManager().download(mFileInfo, mIProgress, mIError, mIFailure);
    }

    public void pause() {
        
    }

    public void delete() {

    }

    public static final class Builder {
        private String url;
        private String fileName;
        private String extension;
        private String dir;
        private int threadCount = 0;
        private Context context;

        private IProgress progress;
        private IError error;
        private IFailure failure;

        public Builder threadCount(int threadCount) {
            this.threadCount = threadCount;
            return this;
        }

        @NonNull
        public Builder context(Context context) {
            this.context = context;
            return this;
        }

        @NonNull
        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public Builder extension(String extension) {
            this.extension = extension;
            return this;
        }

        public Builder dir(String dir) {
            this.dir = dir;
            return this;
        }

        public Builder progress(IProgress progress) {
            this.progress = progress;
            return this;
        }

        public Builder error(IError error) {
            this.error = error;
            return this;
        }

        public Builder failure(IFailure failure) {
            this.failure = failure;
            return this;
        }

        @NonNull
        public MultiThreadDownload build() {

            if (StringUtil.isEmpty(dir)) {
                this.dir = DIR_IMAGE;
            }
            if (StringUtil.isEmpty(extension)) {
                this.extension = "";
            }
            if (StringUtil.isEmpty(fileName)) {
                this.fileName = FileUtil.getFileNameByTime(extension.toUpperCase(), extension);
            }
            if (threadCount == 0 || threadCount > 5) {
                this.threadCount = 3;
            }

            return new MultiThreadDownload(
                    context,
                    url,
                    fileName,
                    dir,
                    threadCount,
                    progress,
                    error,
                    failure);
        }
    }
}
