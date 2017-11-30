package com.wangzai.lovesy.core.download;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.wangzai.lovesy.core.download.entities.FileEntity;
import com.wangzai.lovesy.core.util.StringUtil;
import com.wangzai.lovesy.core.util.file.FileUtil;

/**
 * Created by wangzai on 2017/11/30
 */

public class DownloadManager {
    private static final String DIR_IMAGE = Environment.getExternalStorageDirectory().
            getAbsolutePath() + "/lovesy";

    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final String ACTION_FINISH = "ACTION_FINISH";

    public static final String EXTRA_FILE = "EXTRA_FILE";
    public static final String EXTRA_UPDATE = "EXTRA_UPDATE";
    public static final String EXTRA_ID = "EXTRA_ID";

    private final String mUrl;
    private final String mFileName;
    private final String mDir;
    private final int mThreadCount;
    private final Context mContext;

    private FileEntity mFileEntity;

    private DownloadManager(Context context,
                            String url,
                            String fileName,
                            String dir,
                            int threadCount) {
        this.mUrl = url;
        this.mFileName = fileName;
        this.mDir = dir;
        this.mThreadCount = threadCount;
        this.mContext = context;

        initFile();
    }

    private void initFile() {
        mFileEntity = new FileEntity(0, mUrl, mFileName, 0, 0);
    }

    public void download() {
        Intent intent = new Intent(mContext, DownloadService.class);
        intent.setAction(ACTION_START);
        intent.putExtra(EXTRA_FILE, mFileEntity);
        mContext.startService(intent);
    }

    public void pause() {
        Intent intent = new Intent(mContext, DownloadService.class);
    }

    public final class Builder {
        private String url;
        private String fileName;
        private String extension;
        private String dir;
        private int threadCount = 0;
        private Context context;

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

        @NonNull
        public DownloadManager build() {

            if (StringUtil.isEmpty(dir)) {
                this.dir = DIR_IMAGE;
            }
            if (StringUtil.isEmpty(extension)) {
                this.extension = "";
            }
            if (StringUtil.isEmpty(fileName)) {
                this.fileName = FileUtil.getFileNameByTime(extension.toUpperCase(), extension);
            }

            return new DownloadManager(
                    context,
                    url,
                    fileName,
                    dir,
                    threadCount);
        }
    }
}
