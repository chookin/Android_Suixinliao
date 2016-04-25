package com.tencent.qcloud.timchat.model;

import android.content.Context;

import com.tencent.TIMGroupMemberInfo;
import com.tencent.TIMGroupMemberRoleType;
import com.tencent.qcloud.timchat.R;

/**
 * 群成员数据
 */
public class GroupMemberProfile implements ProfileSummary {

    TIMGroupMemberInfo timGroupMemberInfo;

    public GroupMemberProfile(TIMGroupMemberInfo info){
        timGroupMemberInfo = info;
    }

    /**
     * 获取头像资源
     */
    @Override
    public int getAvatarRes() {
        return R.drawable.head_other;
    }

    /**
     * 获取头像地址
     */
    @Override
    public String getAvatarUrl() {
        return null;
    }

    /**
     * 获取名字
     */
    @Override
    public String getName() {
        if (timGroupMemberInfo.getNameCard().equals("")){
            return timGroupMemberInfo.getUser();
        }
        return timGroupMemberInfo.getNameCard();
    }

    /**
     * 获取描述信息
     */
    @Override
    public String getDescription() {
        return null;
    }

    /**
     * 获取id
     */
    @Override
    public String getIdentify() {
        return timGroupMemberInfo.getUser();
    }

    /**
     * 显示详情等点击事件
     *
     * @param context 上下文
     */
    @Override
    public void onClick(Context context) {

    }

    /**
     * 获取身份
     */
    public TIMGroupMemberRoleType getRole(){
        return timGroupMemberInfo.getRole();
    }


    /**
     * 获取群名片
     */
    public String getNameCard(){
        if (timGroupMemberInfo.getNameCard() == null) return "";
        return timGroupMemberInfo.getNameCard();
    }

    public long getQuietTime(){
        return timGroupMemberInfo.getSilenceSeconds();
    }
}
