package com.great.ramenbird.timemoney;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2018/1/21.
 */

public class BaseDbOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "basic.db";
    private static final int DB_VERSION = 1;

    public BaseDbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        this(context, name, factory, version, null);
    }

    public BaseDbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, DB_NAME, null, DB_VERSION, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("drop table if exists moneyrecord");
        db.execSQL("create table moneyrecord(_id integer primary key, recordingtime integer not null, " +
                "amount decimal(32, 2) not null," +
                "occurtime date not null," +
                "description nchar(256))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
