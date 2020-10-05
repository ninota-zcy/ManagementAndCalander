package com.example.managementandcalander;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME= "messagesdb";//数据库名字
    private final static int DATABASE_VERSION = 1;//数据库版本
    //建表语句
    private final static String SQL_CREATE_DATABASE = "CREATE TABLE "
            + Messages.Message.TABLE_NAME + " (" +
            Messages.Message._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Messages.Message.COLUMN_NAME_TEM + " TEXT" + "," +
            Messages.Message.COLUMN_NAME_FEVER + " TEXT" + "," +
            Messages.Message.COLUMN_NAME_ELSEES + " TEXT" + " ,"+
            Messages.Message.COLUMN_NAME_TIME+" default (datetime('now', 'localtime'))"+")";

    //删表语句
    private final static String SQL_DELETE_DATABASE = "DROP TABLE IF EXISTS " + Messages.Message.TABLE_NAME;


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    //建表
    public void onCreate(SQLiteDatabase db) {
        Log.v("test","建表成功");
        db.execSQL(SQL_CREATE_DATABASE);
    }

    @Override
    //更新，先删后建
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_DATABASE);
        onCreate(db);
    }
}
