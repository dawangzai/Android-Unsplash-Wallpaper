package com.wangzai.lovesy.core.download.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wangzai on 2017/11/27
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sDatabaseHelper;
    private static final String DB_NAME = "download.db";
    private static final int DB_VERSION = 1;
    private static final String SQL_CREATE_TABLE_THREAD = "create table thread_entity(" +
            "_id integer primary key autoincrement," +
            "thread_id integer," +
            "url text," +
            "start integer," +
            "end integer," +
            "finished integer)";
    private static final String SQL_CREATE_TABLE_FILE = "create table file_entity(" +
            "_id integer primary key autoincrement," +
            "file_id integer," +
            "url text," +
            "dir text," +
            "file_name text," +
            "thread_count integer," +
            "length integer," +
            "progress integer," +
            "status integer)";
    private static final String SQL_DROP_THREAD0
            = "drop table if exists thread_entity";
    private static final String SQL_DROP_FILE = "drop table if exists thread_entity";

    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static DatabaseHelper getInstance(Context context) {
        if (sDatabaseHelper == null) {
            sDatabaseHelper = new DatabaseHelper(context);
        }

        return sDatabaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_FILE);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_THREAD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DROP_FILE);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_FILE);
//        sqLiteDatabase.execSQL(SQL_DROP_THREAD);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_THREAD);
    }
}
