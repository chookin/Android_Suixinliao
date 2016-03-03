package com.tencent.qcloud.timchat.model;

/**
 * 用户数据
 */
public class UserInfo {

    private String id;

    private static UserInfo ourInstance = new UserInfo();

    public static UserInfo getInstance() {
        return ourInstance;
    }

    private UserInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}