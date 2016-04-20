package com.tencent.qcloud.timchat;

import android.app.Application;
import android.content.Context;





/**
 * 全局Application
 */
public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
//        InitBusiness.start(context);
//        TlsBusiness.init(context);
//        String id =  TLSService.getInstance().getLastUserIdentifier();
//        UserInfo.getInstance().setId(id);
//        UserInfo.getInstance().setUserSig(TLSService.getInstance().getUserSig(id));
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(this);
    }

    public static Context getContext() {
        return context;
    }

}
