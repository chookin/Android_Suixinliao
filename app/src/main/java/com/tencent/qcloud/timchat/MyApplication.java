package com.tencent.qcloud.timchat;

import android.app.Application;
import android.content.Context;

import com.tencent.qcloud.presentation.business.InitBusiness;
import com.tencent.qcloud.tlslibrary.service.InitService;


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
        InitService.init(context);
    }

    public static Context getContext() {
        return context;
    }
}
