package com.tencent.qcloud.timchat.model;

import android.view.View;
import android.widget.RelativeLayout;

import com.tencent.TIMMessage;
import com.tencent.qcloud.timchat.adapters.ChatAdapter;

/**
 * 消息数据基类
 */
public abstract class Message {

    TIMMessage message;

    public TIMMessage getMessage() {
        return message;
    }


    /**
     * 显示消息
     *
     * @param viewHolder 界面样式
     */
    public abstract void showMessage(ChatAdapter.ViewHolder viewHolder);

    /**
     * 获取显示气泡
     *
     * @param viewHolder 界面样式
     */
    public RelativeLayout getBubbleView(ChatAdapter.ViewHolder viewHolder){
        if (message.isSelf()){
            viewHolder.leftPanel.setVisibility(View.GONE);
            viewHolder.rightPanel.setVisibility(View.VISIBLE);
            return viewHolder.rightMessage;
        }else{
            viewHolder.leftPanel.setVisibility(View.VISIBLE);
            viewHolder.rightPanel.setVisibility(View.GONE);
            return viewHolder.leftMessage;
        }

    }

    /**
     * 判断是否是自己发的
     *
     */
    public boolean isSelf(){
        return message.isSelf();
    }

    /**
     * 获取消息摘要
     *
     */
    public abstract String getSummary();

}
