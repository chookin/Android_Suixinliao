package com.tencent.qcloud.tlslibrary.service;

import android.content.Context;

import com.tencent.qcloud.sdk.Constant;

/**
 * 初始化tls登录模块
 */
public class InitService {

    private InitService(){}

    public static void init(Context context){
        TLSConfiguration.setSdkAppid(Constant.SDK_APPID);
        TLSConfiguration.setAccountType(Constant.ACCOUNT_TYPE);
        TLSConfiguration.setTimeout(8000);
        TLSConfiguration.setQqAppIdAndAppKey("222222", "CXtj4p63eTEB2gSu");
        TLSConfiguration.setWxAppIdAndAppSecret("wx65f71c2ea2b122da", "1d30d40f8db6d3ad0ee6492e62ad5d57");
        TLSService tlsService = TLSService.getInstance();
        tlsService.initTlsSdk(context);
    }
}
