package com.tencent.qcloud.timchat;

import android.app.Application;
import android.content.Context;

import com.tencent.qcloud.presentation.business.InitBusiness;
import com.tencent.qcloud.tlslibrary.service.InitService;
import com.tencent.qcloud.tlslibrary.service.TLSService;

import model.UserInfo;


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
        TLSService tlsService = InitService.init(context);
        if (tlsService != null){
            UserInfo.getInstance().setId(tlsService.getLastUserIdentifier());
        }
    }

    public static Context getContext() {
        return context;
    }
}
