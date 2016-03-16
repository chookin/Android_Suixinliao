package com.tencent.qcloud.presentation.viewfeatures;

/**
 * Created by admin on 16/3/10.
 */
public interface SettingsFeature extends MvpView {


    void showMyAllowedStatus(int status);

    /**
     * 退出回调
     *
     * @param isSuccess 是否成功
     */
    void onLogoutResult(boolean isSuccess);
}
