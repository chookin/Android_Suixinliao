package com.tencent.qcloud.timchat.model;

import com.tencent.TIMMessage;

/**
 * 文本消息数据
 */
public class TextMessage extends Message {

    public TextMessage(TIMMessage message){
        this.message = message;
    }
}
