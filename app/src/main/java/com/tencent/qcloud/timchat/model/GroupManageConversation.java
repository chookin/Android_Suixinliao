package com.tencent.qcloud.timchat.model;

import android.content.Context;

import com.tencent.TIMGroupPendencyItem;
import com.tencent.qcloud.timchat.MyApplication;
import com.tencent.qcloud.timchat.R;

/**
 * 群管理会话
 */
public class GroupManageConversation extends Conversation {


    private TIMGroupPendencyItem lastMessage;

    private long unreadCount;


    public GroupManageConversation(TIMGroupPendencyItem message){
        lastMessage = message;
        name = MyApplication.getContext().getString(R.string.conversation_system_group);
    }


    /**
     * 获取最后一条消息的时间
     */
    @Override
    public long getLastMessageTime() {
        return lastMessage.getAddTime();
    }

    /**
     * 获取未读消息数量
     */
    @Override
    public long getUnreadNum() {
        return 0;
    }

    /**
     * 将所有消息标记为已读
     */
    @Override
    public void readAllMessage() {

    }

    /**
     * 获取头像
     */
    @Override
    public int getAvatar() {
        return R.drawable.ic_news;
    }

    /**
     * 跳转到聊天界面或会话详情
     *
     * @param context 跳转上下文
     */
    @Override
    public void navToDetail(Context context) {

    }

    /**
     * 获取最后一条消息摘要
     */
    @Override
    public String getLastMessageSummary() {
        if (lastMessage == null) return "";
        switch (lastMessage.getPendencyType()){
            case INVITED_BY_OTHER:
            case APPLY_BY_SELF:
            case BOTH_SELFAPPLY_AND_INVITED:
            default:
                    return lastMessage.getFromUser();
        }
    }


    /**
     * 设置最后一条消息
     */
    public void setLastMessage(TIMGroupPendencyItem message){
        lastMessage = message;
    }


    /**
     * 设置未读数量
     *
     * @param count 未读数量
     */
    public void setUnreadCount(long count){
        unreadCount = count;
    }
}
