package com.example.managementandcalander;

import android.net.Uri;
import android.provider.BaseColumns;

public class Messages {

    public static class MessageItem {
        public String id;

        public MessageItem(String id) {
            this.id = id;


        }

    }

    public static class MessageDescription{
        public String id;
        public String fever;
        public String tem;
        public String elsees;



        public String time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFever() {
            return fever;
        }

        public void setFever(String fever) {
            this.fever = fever;
        }

        public String getTem() {
            return tem;
        }

        public void setTem(String tem) {
            this.tem = tem;
        }

        public String getElsees() {
            return elsees;
        }

        public void setElsees(String elsees) {
            this.elsees = elsees;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public MessageDescription(String id, String fever, String tem, String elsees, String time) {
            this.id = id;
            this.fever = fever;
            this.tem = tem;
            this.elsees = elsees;
            this.time = time;
        }
        public MessageDescription(){

        }

    }


    public static abstract class Message implements BaseColumns {

        public static final String TABLE_NAME = "messages";//表名
        // _ID字段：主键，从接口BaseColumn而来
        public static final String COLUMN_NAME_FEVER = "fever";//字段：单词
        public static final String COLUMN_NAME_TEM = "tem";//字段：单词含义
        public static final String COLUMN_NAME_ELSEES= "elsees";//字段：单词示例
        public static final String COLUMN_NAME_TIME= "time";//字段：单词示例


    }

}
