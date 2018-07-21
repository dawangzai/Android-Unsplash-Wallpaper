package com.wangzai.lovesy.core.download.db.file;

import com.wangzai.lovesy.core.download.entities.FileInfo;

import java.util.List;

/**
 * Created by wangzai on 2017/12/27
 */

public interface FileDao {
    void insertFile(FileInfo fileInfo);

    void deleteFile(String url);

    void deleteAllFile();

    void updateFile(String url, int progress, int status);

    List<FileInfo> queryAllFile();

//    boolean isExistsThread(String url);
//
//    boolean isThreadExists(String url, int threadId);
}
