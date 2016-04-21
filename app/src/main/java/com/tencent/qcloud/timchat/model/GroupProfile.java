package com.tencent.qcloud.timchat.model;

import android.content.Context;
import android.content.Intent;

import com.tencent.TIMGroupDetailInfo;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.ui.GroupProfileActivity;

/**
 * 群资料
 */
public class GroupProfile implements ProfileSummary {


    private TIMGroupDetailInfo profile;

    public GroupProfile(TIMGroupDetailInfo profile){
        this.profile = profile;
    }

    /**
     * 获取群ID
     */
    @Override
    public String getIdentify(){
        return profile.getGroupId();
    }

    public TIMGroupDetailInfo getProfile() {
        return profile;
    }

    public void setProfile(TIMGroupDetailInfo profile) {
        this.profile = profile;
    }

    /**
     * 获取头像资源
     */
    @Override
    public int getAvatarRes() {
        return R.drawable.head_group;
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
        return profile.getGroupName();
    }

    /**
     * 获取描述信息
     */
    @Override
    public String getDescription() {
        return null;
    }

    /**
     * 显示详情
     *
     * @param context 上下文
     */
    @Override
    public void onClick(Context context) {
        Intent intent = new Intent(context, GroupProfileActivity.class);
        intent.putExtra("identify", profile.getGroupId());
        context.startActivity(intent);
    }
}
