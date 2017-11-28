package com.wangzai.lovesy.core.net.download.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wangzai.lovesy.core.net.download.entities.ThreadEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzai on 2017/11/28
 */

public class ThreadDAOImpl implements ThreadDAO {

    private static final String SQL_INSERT = "insert into thread_entity(thread_id,url,start,end,finished) values(?,?,?,?,?)";
    private static final String SQL_DELETE = "delete from thread_entity where url = ? and thread_id = ?";
    private static final String SQL_UPDATE = "update thread_entity set finished = ? where url = ? and thread_id = ?";
    private static final String SQL_QUERY_ALL = "select * from thread_entity where url = ?";
    private static final String SQL_QUERY_SINGLE = "select * from thread_entity where url = ? and thread_id = ?";
    private DBHelper mHelper;

    public ThreadDAOImpl(Context context) {
        mHelper = new DBHelper(context);
    }

    @Override
    public void insertThread(ThreadEntity threadEntity) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL(SQL_INSERT, new Object[]{threadEntity.getId(),
                threadEntity.getUrl(),
                threadEntity.getStart(),
                threadEntity.getEnd(),
                threadEntity.getFinished()});
        db.close();
    }

    @Override
    public void deleteThread(String url, int threadId) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL(SQL_DELETE, new Object[]{url, threadId});
        db.close();
    }

    @Override
    public void updateThread(String url, int threadId, int finished) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL(SQL_UPDATE, new Object[]{url, threadId});
        db.close();
    }

    @Override
    public List<ThreadEntity> queryAllThread(String url) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        @SuppressLint("Recycle") final Cursor cursor = db.rawQuery(SQL_QUERY_ALL, new String[]{url});
        final List<ThreadEntity> threadList = new ArrayList<>();
        while (cursor.moveToNext()) {
            ThreadEntity thread = new ThreadEntity();
            thread.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            thread.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            thread.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            thread.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
            thread.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            threadList.add(thread);
        }
        cursor.close();
        db.close();
        return threadList;
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
