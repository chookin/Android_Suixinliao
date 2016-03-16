package com.tencent.qcloud.timchat.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.qcloud.timchat.MyApplication;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.utils.TimeUtil;

import java.io.Serializable;

/**
 * 会话数据
 */
public class Conversation implements Comparable {

    private TIMConversation conversation;

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
        this.conversation = conversation;
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

    /**
     * 获取最后一条消息摘要
     */
    public String getLastMessageSummary(){
        if (lastMessage == null) return "";
        return lastMessage.getSummary();
    }

    /**
     * 获取最后一条消息时间
     */
    public String getLastMessageTime(){
        if (lastMessage == null) return "";
        return TimeUtil.getTimeStr(lastMessage.getMessage().timestamp());
    }

    /**
     * 获取未读消息数量
     */
    public long getUnreadNum(){
        if (conversation == null) return 0;
        return conversation.getUnreadMessageNum();
    }

    /**
     * 将所有消息标记为已读
     */
    public void readAllMessage(){
        if (conversation != null){
            conversation.setReadMessage();
        }
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


    /**
     * Compares this object to the specified object to determine their relative
     * order.
     *
     * @param another the object to compare to this instance.
     * @return a negative integer if this instance is less than {@code another};
     * a positive integer if this instance is greater than
     * {@code another}; 0 if this instance has the same order as
     * {@code another}.
     * @throws ClassCastException if {@code another} cannot be converted into something
     *                            comparable to {@code this} instance.
     */
    @Override
    public int compareTo(Object another) {
        if (another instanceof Conversation){
            Conversation anotherConversation = (Conversation) another;
            if (anotherConversation.lastMessage ==null && lastMessage == null) return 0;
            if (anotherConversation.lastMessage == null) return -1;
            if (lastMessage == null) return 1;
            long timeGap = anotherConversation.lastMessage.getMessage().timestamp() - lastMessage.getMessage().timestamp();
            if (timeGap > 0) return  1;
            else if (timeGap < 0) return -1;
            return 0;
        }else{
            throw new ClassCastException();
        }
    }
}
