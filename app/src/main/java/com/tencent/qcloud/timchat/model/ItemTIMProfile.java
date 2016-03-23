package com.tencent.qcloud.timchat.model;

/**
 * Created by admin on 16/3/4.
 */
public class ItemTIMProfile {
    private String id;
    private String name="";
    private boolean needVerify;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    private String avatar;

    public ItemTIMProfile() {
        // TODO Auto-generated constructor stub
    }

    public void setID(String id){
        this.id = id;
    }

    public String getID(){
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setNeedVerify(boolean needVerify){
        this.needVerify = needVerify;
    }

    public boolean getNeddVerify(){
        return needVerify;
    }
}
