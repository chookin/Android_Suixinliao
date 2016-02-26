package com.tencent.qcloud.presentation.business;

import android.content.Context;

import com.tencent.TIMManager;
import com.tencent.qcloud.presentation.model.UserInfo;
import com.tencent.qcloud.presentation.util.C;
import com.tencent.qcloud.tlslibrary.service.TLSConfiguration;
import com.tencent.qcloud.tlslibrary.service.TLSService;


/**
 * 初始化
 * 包括imsdk,tls等
 */
public class InitBusiness {

    private InitBusiness(){}

    public static void start(Context context){
        initImsdk(context);
        initTLS(context);
    }

    private static void initImsdk(Context context){
        TIMManager.getInstance().init(context);
    }


    private static void initTLS(Context context){
        TLSConfiguration.setSdkAppid(C.SDK_APPID);
        TLSConfiguration.setAccountType(C.ACCOUNT_TYPE);
        TLSConfiguration.setTimeout(8000);
        TLSConfiguration.setQqAppIdAndAppKey("222222", "CXtj4p63eTEB2gSu");
        TLSConfiguration.setWxAppIdAndAppSecret("wx65f71c2ea2b122da", "1d30d40f8db6d3ad0ee6492e62ad5d57");
        TLSService tlsService = TLSService.getInstance();
        tlsService.initTlsSdk(context);
        UserInfo.getInstance().setId(tlsService.getLastUserIdentifier());
    }


}
