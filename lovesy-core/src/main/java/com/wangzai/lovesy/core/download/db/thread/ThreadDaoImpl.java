package com.wangzai.lovesy.core.download.db.thread;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wangzai.lovesy.core.download.db.DatabaseHelper;
import com.wangzai.lovesy.core.download.entities.ThreadInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzai on 2017/11/28
 */

public class ThreadDaoImpl implements ThreadDao {

    private static final String SQL_INSERT = "insert into thread_entity(thread_id,url,start,end,finished) values(?,?,?,?,?)";
    private static final String SQL_DELETE = "delete from thread_entity where url = ? and thread_id = ?";
    private static final String SQL_DELETE_ALL = "delete from thread_entity where url = ?";
    private static final String SQL_UPDATE = "update thread_entity set finished = ? where url = ? and thread_id = ?";
    private static final String SQL_QUERY_ALL = "select * from thread_entity where url = ?";
    private static final String SQL_QUERY_SINGLE = "select * from thread_entity where url = ? and thread_id = ?";
    private DatabaseHelper mHelper;

    public ThreadDaoImpl(Context context) {
        mHelper = DatabaseHelper.getInstance(context);
    }

    @Override
    public synchronized void insertThread(ThreadInfo threadInfo) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL(SQL_INSERT, new Object[]{threadInfo.getId(),
                threadInfo.getUrl(),
                threadInfo.getStart(),
                threadInfo.getEnd(),
                threadInfo.getProgress()});
        db.close();
    }

    @Override
    public synchronized void deleteThread(String url, int threadId) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL(SQL_DELETE, new Object[]{url, threadId});
        db.close();
    }

    @Override
    public synchronized void deleteAllThread(String url) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL(SQL_DELETE_ALL, new Object[]{url});
        db.close();
    }

    @Override
    public synchronized void updateThread(String url, int threadId, int finished) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL(SQL_UPDATE, new Object[]{finished, url, threadId});
        db.close();
    }

    @Override
    public List<ThreadInfo> queryAllThread(String url) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        @SuppressLint("Recycle") final Cursor cursor = db.rawQuery(SQL_QUERY_ALL, new String[]{url});
        final List<ThreadInfo> threadList = new ArrayList<>();
        while (cursor.moveToNext()) {
            ThreadInfo thread = new ThreadInfo();
            thread.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            thread.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            thread.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            thread.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
            thread.setProgress(cursor.getInt(cursor.getColumnIndex("finished")));
            threadList.add(thread);
        }
        cursor.close();
        db.close();
        return threadList;
    }

    @Override
    public boolean isExistsThread(String url) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        @SuppressLint("Recycle") final Cursor cursor = db.rawQuery(SQL_QUERY_ALL, new String[]{url});
        boolean result = cursor.moveToFirst();
        cursor.close();
        db.close();
        return result;
    }

    @Override
    public boolean isThreadExists(String url, int threadId) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        @SuppressLint("Recycle") final Cursor cursor = db.rawQuery(SQL_QUERY_SINGLE, new String[]{url, String.valueOf(threadId)});
        final boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return exists;
    }
}
