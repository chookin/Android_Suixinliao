package com.tencent.qcloud.presentation.viewfeatures;

import com.tencent.TIMUserProfile;

import java.util.List;

/**
 * 好友信息接口
 */
public interface FriendInfoView {


    /**
     * 显示好友信息
     *
     * @param friends 好友资料列表
     */
    void showFriendInfo(List<TIMUserProfile> friends);
}
