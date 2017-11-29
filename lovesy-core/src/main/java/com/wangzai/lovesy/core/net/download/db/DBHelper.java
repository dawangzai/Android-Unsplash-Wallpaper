package com.wangzai.lovesy.core.net.download.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wangzai on 2017/11/27
 */

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper sDBHelper;
    private static final String DB_NAME = "download.db";
    private static final int DB_VERSION = 1;
    private static final String SQL_CREATE = "create table thread_entity(" +
            "_id integer primary key autoincrement," +
            "thread_id integer," +
            "url text," +
            "start integer," +
            "end integer," +
            "finished integer)";
    private static final String SQL_DROP = "drop table if exists thread_entity";

    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static DBHelper getInstance(Context context) {
        if (sDBHelper == null) {
            sDBHelper = new DBHelper(context);
        }

        return sDBHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DROP);
        sqLiteDatabase.execSQL(SQL_CREATE);
    }
}
