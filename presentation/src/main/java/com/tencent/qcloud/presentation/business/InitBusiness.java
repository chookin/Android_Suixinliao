package com.tencent.qcloud.presentation.business;

import android.content.Context;

import com.tencent.TIMManager;


/**
 * 初始化
 * 包括imsdk,tls等
 */
public class InitBusiness {

    private InitBusiness(){}

    public static void start(Context context){
        initImsdk(context);
    }

    private static void initImsdk(Context context){
        TIMManager.getInstance().init(context);
    }





}
