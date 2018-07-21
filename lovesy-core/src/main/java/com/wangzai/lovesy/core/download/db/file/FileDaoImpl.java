package com.wangzai.lovesy.core.download.db.file;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wangzai.lovesy.core.download.db.DatabaseHelper;
import com.wangzai.lovesy.core.download.entities.FileInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangzai on 2017/12/27
 */

public class FileDaoImpl implements FileDao {
    private static final String SQL_INSERT = "insert into file_entity(file_id,url,dir,file_name,thread_count,length,progress,status) values(?,?,?,?,?,?,?,?,?)";
    private static final String SQL_DELETE = "delete from file_entity where url = ?";
    private static final String SQL_DELETE_ALL = "delete from file_entity";
    private static final String SQL_UPDATE = "update file_entity set progress = ?,status = ? where url = ?";
    private static final String SQL_QUERY_ALL = "select * from file_entity";
    private static final String SQL_QUERY_SINGLE = "select * from file_entity where url = ?";
    private DatabaseHelper mHelper;

    public FileDaoImpl(Context context) {
        mHelper = DatabaseHelper.getInstance(context);
    }

    @Override
    public void insertFile(FileInfo fileInfo) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL(SQL_INSERT, new Object[]{
                fileInfo.getId(),
                fileInfo.getUrl(),
                fileInfo.getDir(),
                fileInfo.getFileName(),
                fileInfo.getThreadCount(),
                fileInfo.getLength(),
                fileInfo.getProgress(),
                fileInfo.getStatus()});
        db.close();
    }

    @Override
    public void deleteFile(String url) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL(SQL_DELETE, new Object[]{url});
        db.close();
    }

    @Override
    public void deleteAllFile() {
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL(SQL_DELETE_ALL);
        db.close();
    }

    @Override
    public void updateFile(String url, int progress, int status) {
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        db.execSQL(SQL_UPDATE, new Object[]{progress, status, url});
        db.close();
    }

    @Override
    public List<FileInfo> queryAllFile() {
        final SQLiteDatabase db = mHelper.getReadableDatabase();
        final Cursor cursor = db.rawQuery(SQL_QUERY_ALL, new String[]{});
        final List<FileInfo> fileList = new ArrayList<>();
        while (cursor.moveToNext()) {
            FileInfo fileInfo = new FileInfo();
            fileInfo.setId(cursor.getInt(cursor.getColumnIndex("file_id")));
            fileInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            fileInfo.setDir(cursor.getString(cursor.getColumnIndex("dir")));
            fileInfo.setFileName(cursor.getString(cursor.getColumnIndex("file_name")));
            fileInfo.setThreadCount(cursor.getInt(cursor.getColumnIndex("thread_count")));
            fileInfo.setLength(cursor.getInt(cursor.getColumnIndex("length")));
            fileInfo.setProgress(cursor.getInt(cursor.getColumnIndex("progress")));
            fileInfo.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
            fileList.add(fileInfo);
        }
        cursor.close();
        db.close();
        return fileList;
    }
}
