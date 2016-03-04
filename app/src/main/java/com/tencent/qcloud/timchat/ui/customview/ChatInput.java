package com.tencent.qcloud.timchat.ui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.tencent.qcloud.timchat.R;

/**
 * 聊天界面输入控件
 */
public class ChatInput extends RelativeLayout {

    public ChatInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.chat_input, this);
    }
}
