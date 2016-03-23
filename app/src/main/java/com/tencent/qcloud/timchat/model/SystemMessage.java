package com.tencent.qcloud.timchat.model;

import android.content.Context;
import android.view.View;

import com.tencent.qcloud.timchat.adapters.ChatAdapter;
import com.tencent.qcloud.timchat.utils.TimeUtil;

/**
 * 系统消息
 */
public class SystemMessage extends Message {

    private long time;

    public SystemMessage(long time){
        this.time = time;
    }

//    /**
//     * 显示消息
//     *
//     * @param viewHolder 界面样式
//     */
//    @Override
//    public void showMessage(ChatAdapter.ViewHolder viewHolder) {
//        viewHolder.leftPanel.setVisibility(View.GONE);
//        viewHolder.rightPanel.setVisibility(View.GONE);
//        viewHolder.systemMessage.setVisibility(View.VISIBLE);
//        if (time != 0){
//            viewHolder.systemMessage.setText(TimeUtil.getChatTimeStr(time));
//
//        }
//    }

    /**
     * 显示消息
     *
     * @param viewHolder 界面样式
     * @param context    显示消息的上下文
     */
    @Override
    public void showMessage(ChatAdapter.ViewHolder viewHolder, Context context) {

    }

    /**
     * 获取消息摘要
     */
    @Override
    public String getSummary() {
        return "";
    }
}
