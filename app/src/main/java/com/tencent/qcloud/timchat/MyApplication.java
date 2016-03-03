package com.tencent.qcloud.timchat;

import android.app.Application;
import android.content.Context;

import com.tencent.qcloud.presentation.business.InitBusiness;
import com.tencent.qcloud.timchat.model.UserInfo;
import com.tencent.qcloud.tlslibrary.service.InitService;
import com.tencent.qcloud.tlslibrary.service.TLSService;




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
            String id = tlsService.getLastUserIdentifier();
            UserInfo.getInstance().setId(id);
            UserInfo.getInstance().setUserSig(tlsService.getUserSig(id));
        }
    }

    public static Context getContext() {
        return context;
    }
}
