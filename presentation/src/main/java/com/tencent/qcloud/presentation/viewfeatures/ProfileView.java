package com.tencent.qcloud.presentation.viewfeatures;

import com.tencent.TIMUserProfile;

/**
 * 用户资料页面接口
 */
public interface ProfileView extends MvpView {

    /**
     * 显示用户信息
     */
    void showProfile(TIMUserProfile profile);
}
