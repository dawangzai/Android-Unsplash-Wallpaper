package com.wangzai.lovesy.core.download.entities;

import java.io.Serializable;

/**
 * Created by wangzai on 2017/11/28
 */

public class FileInfo implements Serializable {
    private static final long serialVersionUID = -8410832293878744589L;

    public static final int STATUS_NONE = 0;
    public static final int STATUS_PREPARE_DOWNLOAD = 1;
    public static final int STATUS_DOWNLOADING = 2;
    public static final int STATUS_WAIT = 3;
    public static final int STATUS_PAUSED = 4;
    public static final int STATUS_COMPLETED = 5;
    public static final int STATUS_ERROR = 6;
    public static final int STATUS_REMOVED = 7;

    private int id;
    private String url;
    private String dir;
    private String fileName;
    private int threadCount;
    private int length;
    private int progress;

    private int status;

    public FileInfo() {
        super();
    }

    public FileInfo(int id, String url, String dir, String fileName, int threadCount, int length, int progress) {
        this.id = id;
        this.url = url;
        this.dir = dir;
        this.fileName = fileName;
        this.threadCount = threadCount;
        this.length = length;
        this.progress = progress;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", dir='" + dir + '\'' +
                ", fileName='" + fileName + '\'' +
                ", threadCount=" + threadCount +
                ", length=" + length +
                ", progress=" + progress +
                '}';
    }

    public boolean isPause() {
        return status == STATUS_PAUSED ||
                status == STATUS_ERROR ||
                status == STATUS_REMOVED;
    }
}
