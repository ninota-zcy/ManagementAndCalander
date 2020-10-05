package com.example.managementandcalander;

import android.app.Application;
import android.content.Context;

public class MessageApplication extends Application {
    private static Context context;
    public static Context getContext(){
        return MessageApplication.context;
    }

    public void onCreate(){
        super.onCreate();
        MessageApplication.context = getApplicationContext();
    }
}
