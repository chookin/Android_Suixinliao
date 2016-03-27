package com.tencent.qcloud.timchat.model;

/**
 * Created by admin on 16/3/4.
 */
public class ProfileItem {

    private String id;
    private String name="";
    private String avatarUrl;
    private String description;
    private boolean needVerify;
    private int avatarRes;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getAvatarRes() {
        return avatarRes;
    }

    public void setAvatarRes(int avatarRes) {
        this.avatarRes = avatarRes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProfileItem() {
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
