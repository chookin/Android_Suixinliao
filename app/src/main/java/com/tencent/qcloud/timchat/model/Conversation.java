package com.tencent.qcloud.timchat.model;

import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;

import java.io.Serializable;

/**
 * 会话数据
 */
public class Conversation implements Serializable {

    //会话对象id
    private String id;

    //会话类型
    private TIMConversationType type;

    //最后一条消息
    private Message lastMessage;

    private TIMConversation conversation;

    public Conversation(TIMConversation conversation){
        this.conversation = conversation;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TIMConversationType getType() {
        return type;
    }

    public void setType(TIMConversationType type) {
        this.type = type;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    public TIMConversation getConversation() {
        return conversation;
    }

    public void setConversation(TIMConversation conversation) {
        this.conversation = conversation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conversation that = (Conversation) o;
        if (!id.equals(that.id)) return false;
        return type == that.type;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }




}
