package com.wangzai.lovesy.core.download.entities;

/**
 * Created by wangzai on 2017/11/28
 */

public class ThreadEntity {
    private int id;
    private String url;
    private int start;
    private int end;
    private int finished;

    public ThreadEntity() {
        super();
    }
    public ThreadEntity(int id, String url, int start, int end, int finished) {
        this.id = id;
        this.url = url;
        this.start = start;
        this.end = end;
        this.finished = finished;
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

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "ThreadEntity{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", finished=" + finished +
                '}';
    }
}
