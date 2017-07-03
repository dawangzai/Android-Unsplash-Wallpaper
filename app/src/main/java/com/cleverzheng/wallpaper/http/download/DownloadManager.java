package com.cleverzheng.wallpaper.http.download;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import okhttp3.OkHttpClient;

/**
 * Created by wangzai on 2017/7/3.
 */

public class DownloadManager {
    private static DownloadManager mInstance;

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
//        init(context, threadCount, getOkHttpClient());
    }

    public void init(Context context, int threadCount, OkHttpClient client) {
        setupDatabase(context);
//        recoveryTaskState();
//
//        mClient = client;
//        mThreadCount = threadCount < 1 ? 1 : threadCount <= EasyConstants.MAX_THREAD_COUNT ? threadCount : EasyConstants.MAX_THREAD_COUNT;
//        mExecutor = new ThreadPoolExecutor(
//                mThreadCount,
//                mThreadCount,
//                20,
//                TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<Runnable>()
//        );
//        mCurrentTaskList = new HashMap<>();
//        mQueue = (LinkedBlockingQueue<Runnable>) mExecutor.getQueue();
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
}
