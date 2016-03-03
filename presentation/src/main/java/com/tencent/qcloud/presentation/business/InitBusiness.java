package com.tencent.qcloud.presentation.business;

import android.content.Context;

import com.tencent.TIMManager;
import com.tencent.qcloud.presentation.event.MessageEvent;


/**
 * 初始化
 * 包括imsdk等
 */
public class InitBusiness {

    private InitBusiness(){}

    public static void start(Context context){
        initImsdk(context);
    }


    /**
     * 初始化imsdk
     */
    private static void initImsdk(Context context){
        //初始化imsdk
        TIMManager.getInstance().init(context);
        //注册消息监听器
        TIMManager.getInstance().addMessageListener(MessageEvent.getInstance());
    }





}
