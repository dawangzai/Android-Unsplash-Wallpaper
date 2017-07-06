package com.cleverzheng.wallpaper.http.download;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.cleverzheng.wallpaper.http.HttpClientManager;
import com.cleverzheng.wallpaper.http.db.DaoManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by wangzai on 2017/7/3.
 */

public class DownloadManager {
    private static DownloadManager mInstance;
    private static final int MAX_THREAD_COUNT = 16;

    private OkHttpClient mClient;
    private int mThreadCount = 1;
    private ThreadPoolExecutor mExecutor;
    private Map<String, DownloadTask> mCurrentTaskList;
    private LinkedBlockingQueue<Runnable> mQueue;

    private DaoSession mDaoSession;

    private DownloadManager() {
    }

    public static synchronized DownloadManager getInstance() {
        if (mInstance == null) {
            mInstance = new DownloadManager();
        }

        return mInstance;
    }

    public void init(Context context) {
//        init(context, getAppropriateThreadCount());
    }

    public void init(Context context, int threadCount) {
        init(context, threadCount, getOkHttpClient());
    }

    private OkHttpClient getOkHttpClient() {
        return HttpClientManager.getInstance().getOkHttpClient();
    }

    public void init(Context context, int threadCount, OkHttpClient client) {
        setupDatabase(context);
        recoveryTaskState();

        mClient = client;
        mThreadCount = threadCount < 1 ? 1 : threadCount <= MAX_THREAD_COUNT ? threadCount : MAX_THREAD_COUNT;
        mExecutor = new ThreadPoolExecutor(
                mThreadCount,
                mThreadCount,
                20,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>()
        );
        mCurrentTaskList = new HashMap<>();
        mQueue = (LinkedBlockingQueue<Runnable>) mExecutor.getQueue();
    }

    private void setupDatabase(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "download.db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster master = new DaoMaster(db);
        mDaoSession = master.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public void addTask(DownloadTask task) {
        TaskEntity taskEntity = task.getTaskEntity();

        if (taskEntity != null && taskEntity.getTaskStatus() != TaskStatus.TASK_STATUS_DOWNLOADING) {
            task.init();
            task.setClient(mClient);
            mCurrentTaskList.put(taskEntity.getTaskId(), task);

            if (!mQueue.contains(task)) {
                mExecutor.execute(task);
            }

            if (mExecutor.getTaskCount() > mThreadCount) {
                task.queue();
            }
        }
    }

    private void recoveryTaskState() {
        List<TaskEntity> entities = DaoManager.instance().queryAll();

        for (TaskEntity entity : entities) {
            long completedSize = entity.getCompletedSize();
            long totalSize = entity.getTotalSize();
            if (completedSize > 0 &&
                    completedSize < totalSize &&
                    entity.getTaskStatus() != TaskStatus.TASK_STATUS_PAUSE) {
                entity.setTaskStatus(TaskStatus.TASK_STATUS_PAUSE);
                DaoManager.instance().update(entity);
            }
        }
    }
}
