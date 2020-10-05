package com.example.managementandcalander;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OperationDB {


    private static DBHelper helper;

    static OperationDB ins = new OperationDB();
    public static OperationDB getOperations(){
        return OperationDB.ins;
    }

    private OperationDB(){
        if(helper == null){
            helper = new DBHelper(MessageApplication.getContext());
        }
    }

    public void close(){
        if(helper != null){
            helper.close();
        }
    }

    public Messages.MessageDescription getSingleMessage(String id){
        Messages.MessageDescription result = new Messages.MessageDescription();
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from words where _id = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{id});
        if(cursor.moveToFirst()){
            result.setFever(cursor.getString(cursor.getColumnIndex("fever")));
            result.setTem(cursor.getString(cursor.getColumnIndex("tem")));
            result.setElsees(cursor.getString(cursor.getColumnIndex("elsees")));
            result.setTime(cursor.getString(cursor.getColumnIndex("time")));

            return result;
        }

        return null;

    }

    public ArrayList<Map<String,String>> getAllMessage(){

        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select * from messages";
        Cursor cursor = db.rawQuery(sql,null);
        Log.v("test","行数:"+cursor.getCount()+"");
        return ConvertCursorToWordList(cursor);
    }

    //疑难
    public ArrayList<Map<String,String>> ConvertCursorToWordList(Cursor cursor){
        ArrayList<Map<String,String>> list = new ArrayList<Map<String, String>>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Map<String,String> map = new HashMap<String,String>();
            System.out.println("dsl:"+cursor.getString(cursor.getColumnIndex("_id"))+ "word:"+cursor.getString(cursor.getColumnIndex("fever")));
            map.put(Messages.Message._ID, cursor.getString(cursor.getColumnIndex("_id")));
            map.put(Messages.Message.COLUMN_NAME_FEVER, cursor.getString(cursor.getColumnIndex("fever")));
            map.put(Messages.Message.COLUMN_NAME_TEM, cursor.getString(cursor.getColumnIndex("tem")));
            map.put(Messages.Message.COLUMN_NAME_ELSEES, cursor.getString(cursor.getColumnIndex("elsees")));
            Log.v("test",cursor.getString(cursor.getColumnIndexOrThrow("time")));
            map.put(Messages.Message.COLUMN_NAME_TIME, cursor.getString(cursor.getColumnIndex("time")));

            list.add(map);
        }


        return list;
    }

    public void InsertMessage(String tem, String fever, String elsees){
        String sql="insert into  messages(tem,fever,elsees) values(?,?,?)";

        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(sql,new String[]{tem,fever,elsees});
    }

    public void Insert(String tem, String fever, String elsees){
        String sql="insert into  messages(tem,fever,elsees) values(?,?,?)";

        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(sql,new String[]{tem+"℃",fever,elsees});
    }

    public void DeleteMessage(String strID){
        String sql="delete from messages where _id='"+strID+"'";

        SQLiteDatabase db = helper.getReadableDatabase();
        db.execSQL(sql);
    }
    public void Delete(String strID){
        String sql="delete from messages where _id='"+strID+"'";

        SQLiteDatabase db = helper.getReadableDatabase();
        db.execSQL(sql);
    }

    //查找
    public ArrayList<Map<String, String>> SearchUseSql() {

        SQLiteDatabase db = helper.getReadableDatabase();

        String sql="select * from messages where fever like ? ";
        Cursor c=db.rawQuery(sql,new String[]{"发烧"});

        return ConvertCursorToWordList(c);
    }




}
