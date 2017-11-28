package com.wangzai.lovesy.core.net.download.db;

import com.wangzai.lovesy.core.net.download.entities.ThreadEntity;

import java.util.List;

/**
 * Created by wangzai on 2017/11/28
 */

public interface ThreadDAO {

    void insertThread(ThreadEntity threadEntity);

    void deleteThread(String url, int threadId);

    void updateThread(String url, int threadId, int finished);

    List<ThreadEntity> queryAllThread(String url);

    boolean isThreadExists(String url, int threadId);
}
