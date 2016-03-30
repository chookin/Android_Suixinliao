package com.tencent.qcloud.timchat.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 好友关系链数据缓存，底层IMSDK会维护本地存储
 */
public class FriendshipInfo {

    private final String TAG = "FriendshipInfo";




    private FriendshipInfo(){
    }

    private static FriendshipInfo instance = new FriendshipInfo();

    public static FriendshipInfo getInstance(){
        return instance;
    }

    public void init(){

    }
}
