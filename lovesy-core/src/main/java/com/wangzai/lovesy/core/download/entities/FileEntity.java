package com.wangzai.lovesy.core.download.entities;

import java.io.Serializable;

/**
 * Created by wangzai on 2017/11/28
 */

public class FileEntity implements Serializable {
    private static final long serialVersionUID = -8410832293878744589L;
    private int id;
    private String url;
    private String dir;
    private String fileName;
    private int threadCount;
    private int length;
    private int progress;

    public FileEntity() {
        super();
    }

    public FileEntity(int id, String url, String dir, String fileName, int threadCount, int length, int progress) {
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

    @Override
    public String toString() {
        return "FileEntity{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", fileName='" + fileName + '\'' +
                ", length=" + length +
                ", progress=" + progress +
                '}';
    }
}
