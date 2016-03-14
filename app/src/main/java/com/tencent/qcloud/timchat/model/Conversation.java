package com.tencent.qcloud.timchat.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.qcloud.timchat.MyApplication;
import com.tencent.qcloud.timchat.R;

import java.io.Serializable;

/**
 * 会话数据
 */
public class Conversation implements Serializable {

    //会话对象id
    private String identify;

    //会话类型
    private TIMConversationType type;

    //会话对象名称
    private String name;

    //最后一条消息
    private Message lastMessage;

    //头像图片
    private Bitmap avatar;


    public Conversation(TIMConversation conversation){
        this.type = conversation.getType();
        this.identify = conversation.getPeer();
        if (type == TIMConversationType.System){
            name=MyApplication.getContext().getString(R.string.conversation_system);
        }else{
            name=conversation.getPeer();
        }
    }



    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getAvatar() {
        if (type == TIMConversationType.System){
            return BitmapFactory.decodeResource(MyApplication.getContext().getResources(), R.drawable.ic_news);
        }else if (type == TIMConversationType.C2C||type == TIMConversationType.Group){
            return BitmapFactory.decodeResource(MyApplication.getContext().getResources(), R.drawable.ic_head);
        }
        return null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conversation that = (Conversation) o;
        if (!identify.equals(that.identify)) return false;
        return type == that.type;

    }

    @Override
    public int hashCode() {
        int result = identify.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }




}
