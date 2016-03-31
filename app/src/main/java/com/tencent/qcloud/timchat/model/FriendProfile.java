package com.tencent.qcloud.timchat.model;

import android.content.Context;
import android.content.Intent;

import com.tencent.TIMUserProfile;
import com.tencent.qcloud.presentation.event.FriendshipInfo;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.ui.AddFriendActivity;
import com.tencent.qcloud.timchat.ui.ProfileActivity;

/**
 * 好友资料
 */
public class FriendProfile implements ProfileSummary {


    private TIMUserProfile profile;

    public FriendProfile(TIMUserProfile profile){
        this.profile = profile;
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
        if (!profile.getRemark().equals("")){
            return profile.getRemark();
        }else if (!profile.getNickName().equals("")){
            return profile.getNickName();
        }
        return profile.getIdentifier();
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
    public void showDetail(Context context) {
        if (FriendshipInfo.getInstance().isFriend(profile.getIdentifier())){
            ProfileActivity.navToProfile(context, profile.getIdentifier());
        }else{
            Intent person = new Intent(context,AddFriendActivity.class);
            person.putExtra("id",profile.getIdentifier());
            person.putExtra("name",getName());
            context.startActivity(person);
        }
    }
}
