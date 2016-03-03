package com.tencent.qcloud.timchat.model;

import com.tencent.TIMMessage;
import com.tencent.qcloud.timchat.adapters.ChatAdapter;

/**
 * 消息数据基类
 */
public abstract class Message {

    TIMMessage message;


    /**
     * 显示消息
     *
     * @param viewHolder 界面样式
     */
    public abstract void showMessage(ChatAdapter.ViewHolder viewHolder);
}
