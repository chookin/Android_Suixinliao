package com.tencent.qcloud.timchat.model;

import android.widget.TextView;

import com.tencent.TIMMessage;
import com.tencent.TIMTextElem;
import com.tencent.qcloud.timchat.MyApplication;
import com.tencent.qcloud.timchat.adapters.ChatAdapter;

/**
 * 文本消息数据
 */
public class TextMessage extends Message {

    public TextMessage(TIMMessage message){
        this.message = message;
    }

    /**
     * 在聊天界面显示消息
     *
     * @param viewHolder 界面样式
     */
    @Override
    public void showMessage(ChatAdapter.ViewHolder viewHolder) {
        TextView tv = new TextView(MyApplication.getContext());
        tv.setTextSize(30);
        tv.setText(((TIMTextElem) message.getElement(0)).getText());
        viewHolder.leftMessage.addView(tv);
    }
}
