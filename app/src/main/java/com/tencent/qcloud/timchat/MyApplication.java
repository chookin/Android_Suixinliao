package com.tencent.qcloud.timchat;

import android.app.Application;
import android.content.Context;

import com.tencent.qcloud.presentation.business.InitBusiness;


/**
 * 全局Application
 */
public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        InitBusiness.start(context);
    }

    public static Context getContext() {
        return context;
    }
}
