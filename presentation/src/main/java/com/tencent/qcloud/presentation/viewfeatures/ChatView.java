package com.tencent.qcloud.presentation.viewfeatures;

import com.tencent.TIMMessage;

/**
 * 聊天界面的接口
 */
public interface ChatView extends MvpView {

    /**
     * 显示消息
     */
    void showMessage(TIMMessage message);
}
