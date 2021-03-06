package com.wangzai.lovesy.core.download.entities;

/**
 * Created by wangzai on 2017/11/28
 */

public class ThreadInfo {
    private int id;
    private String url;
    private int start;
    private int end;
    private int progress;

    public ThreadInfo() {
        super();
    }

    public ThreadInfo(int id, String url, int start, int end, int progress) {
        this.id = id;
        this.url = url;
        this.start = start;
        this.end = end;
        this.progress = progress;
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

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "ThreadInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", progress=" + progress +
                '}';
    }
}
