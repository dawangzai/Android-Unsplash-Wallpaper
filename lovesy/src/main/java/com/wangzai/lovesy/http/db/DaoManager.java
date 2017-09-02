package com.wangzai.lovesy.http.db;

import com.wangzai.lovesy.http.download.DownloadManager;
import com.wangzai.lovesy.http.download.TaskEntity;
import com.wangzai.lovesy.http.download.TaskEntityDao;

import java.util.List;

/**
 * Created by wangzai on 2017/7/3.
 */

public class DaoManager {
    private static DaoManager mInstance;

    private DaoManager() {
    }

    public static DaoManager instance() {
        synchronized (DaoManager.class) {
            if (mInstance == null) {
                mInstance = new DaoManager();
            }
        }
        return mInstance;
    }

    public void insertOrReplace(TaskEntity entity) {
        DownloadManager.getInstance().getDaoSession().insertOrReplace(entity);
    }

    public TaskEntity queryWithId(String taskId) {
        return DownloadManager
                .getInstance()
                .getDaoSession()
                .getTaskEntityDao()
                .queryBuilder()
                .where(TaskEntityDao.Properties.TaskId.eq(taskId))
                .unique();
    }

    public List<TaskEntity> queryAll() {
        return DownloadManager
                .getInstance()
                .getDaoSession()
                .getTaskEntityDao()
                .loadAll();
    }

    public void update(TaskEntity entity) {
        DownloadManager
                .getInstance()
                .getDaoSession()
                .getTaskEntityDao()
                .update(entity);
    }

    public void delete(TaskEntity entity) {
        DownloadManager
                .getInstance()
                .getDaoSession()
                .getTaskEntityDao()
                .delete(entity);
    }
}
