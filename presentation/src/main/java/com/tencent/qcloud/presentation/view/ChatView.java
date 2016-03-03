package com.tencent.qcloud.presentation.view;

import com.tencent.TIMMessage;

/**
 * 聊天界面的接口
 */
public interface ChatView {

    /**
     * 显示消息
     */
    void showMessage(TIMMessage message);
}
