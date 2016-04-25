package com.tencent.qcloud.timchat.model;

import android.content.Context;
import android.view.View;

import com.tencent.TIMGroupTipsElem;
import com.tencent.TIMMessage;
import com.tencent.qcloud.timchat.MyApplication;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.ChatAdapter;

/**
 * 群tips消息
 */
public class GroupTipMessage extends Message {


    public GroupTipMessage(TIMMessage message){
        this.message = message;
    }


    /**
     * 显示消息
     *
     * @param viewHolder 界面样式
     * @param context    显示消息的上下文
     */
    @Override
    public void showMessage(ChatAdapter.ViewHolder viewHolder, Context context) {
        viewHolder.leftPanel.setVisibility(View.GONE);
        viewHolder.rightPanel.setVisibility(View.GONE);
        viewHolder.systemMessage.setVisibility(View.VISIBLE);
        viewHolder.systemMessage.setText(getSummary());
    }

    /**
     * 获取消息摘要
     */
    @Override
    public String getSummary() {
        final TIMGroupTipsElem e = (TIMGroupTipsElem) message.getElement(0);
        switch (e.getTipsType()){
            case CancelAdmin:
            case SetAdmin:
                return MyApplication.getContext().getString(R.string.summary_group_admin_change);
            case Join:
            case Kick:
            case ModifyMemberInfo:
            case Quit:
                return MyApplication.getContext().getString(R.string.summary_group_mem_change);
            case ModifyGroupInfo:
                return MyApplication.getContext().getString(R.string.summary_group_info_change);
        }
        return "";
    }
}
