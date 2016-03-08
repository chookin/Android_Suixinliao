package com.tencent.qcloud.timchat.model;

/**
 * Created by admin on 16/3/4.
 */
public class PersonInfo {
    private String id;
    private String name;
    private boolean needVerify;

    public PersonInfo() {
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
