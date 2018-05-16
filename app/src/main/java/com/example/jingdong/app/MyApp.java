package com.example.jingdong.app;

import android.app.Application;

import com.dash.zxinglibrary.activity.ZXingLibrary;

public class MyApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ZXingLibrary.initDisplayOpinion(this);
    }
}
