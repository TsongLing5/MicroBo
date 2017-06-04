package com.example.tsongling5.microbo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * Created by TsongLing5 on 2016/8/7.
 */
public class MyApplication extends Application {
    private static Context context;
    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate(){
        context=getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}
