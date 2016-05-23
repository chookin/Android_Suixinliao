package com.tencent.qcloud.timchat.model;

import android.content.Context;
import android.util.TypedValue;
import android.widget.TextView;

import com.tencent.TIMFileElem;
import com.tencent.TIMMessage;
import com.tencent.qcloud.timchat.MyApplication;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.ChatAdapter;

/**
 * 文件消息
 */
public class FileMessage extends Message {


    public FileMessage(TIMMessage message){
        this.message = message;
    }

    public FileMessage(String filePath){
        message = new TIMMessage();
        TIMFileElem elem = new TIMFileElem();
        elem.setPath(filePath);
        elem.setFileName(filePath.substring(filePath.lastIndexOf("/")+1));
        message.addElement(elem);
    }



    /**
     * 显示消息
     *
     * @param viewHolder 界面样式
     * @param context    显示消息的上下文
     */
    @Override
    public void showMessage(ChatAdapter.ViewHolder viewHolder, Context context) {
        clearView(viewHolder);
        TIMFileElem e = (TIMFileElem) message.getElement(0);
        TextView tv = new TextView(MyApplication.getContext());
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        tv.setTextColor(MyApplication.getContext().getResources().getColor(isSelf() ? R.color.white : R.color.black));
        tv.setText(e.getFileName());
        getBubbleView(viewHolder).addView(tv);
        showStatus(viewHolder);
    }

    /**
     * 获取消息摘要
     */
    @Override
    public String getSummary() {
        return MyApplication.getContext().getString(R.string.summary_file);
    }
}
