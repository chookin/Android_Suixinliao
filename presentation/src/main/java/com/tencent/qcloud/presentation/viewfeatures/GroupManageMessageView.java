package com.tencent.qcloud.presentation.viewfeatures;

import com.tencent.TIMGroupPendencyItem;

/**
 * 群管理消息接口
 */
public interface GroupManageMessageView {

    /**
     * 获取群管理最后一条系统消息的回调
     *
     * @param message 最后一条消息
     * @param unreadCount 未读数
     */
    void onGetGroupManageLastMessage(TIMGroupPendencyItem message, long unreadCount);
}
