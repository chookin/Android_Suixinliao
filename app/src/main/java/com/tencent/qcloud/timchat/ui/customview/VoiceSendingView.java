package com.tencent.qcloud.timchat.ui.customview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tencent.qcloud.timchat.R;

/**
 * 发送语音提示控件
 */
public class VoiceSendingView extends RelativeLayout {

    public VoiceSendingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.voice_sending, this);
        ImageView img = (ImageView)findViewById(R.id.microphone);
        img.setBackgroundResource(R.drawable.animation_voice);
        AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
        frameAnimation.start();
    }
}
