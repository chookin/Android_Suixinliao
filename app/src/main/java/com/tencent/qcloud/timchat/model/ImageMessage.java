package com.tencent.qcloud.timchat.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.tencent.TIMImage;
import com.tencent.TIMImageElem;
import com.tencent.TIMImageType;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;
import com.tencent.qcloud.timchat.MyApplication;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.ChatAdapter;
import com.tencent.qcloud.timchat.utils.LogUtils;

/**
 * 图片消息数据
 */
public class ImageMessage extends Message {

    private static final String TAG = "ImageMessage";

    public ImageMessage(TIMMessage message){
        this.message = message;
    }

    public ImageMessage(String path){
        message = new TIMMessage();
        TIMImageElem elem = new TIMImageElem();
        elem.setPath(path);
        if (message.addElement(elem) != 0) return;
    }


    /**
     * 显示消息
     *
     * @param viewHolder 界面样式
     */
    @Override
    public void showMessage(final ChatAdapter.ViewHolder viewHolder) {
        TIMImageElem e = (TIMImageElem) message.getElement(0);
        for(TIMImage image : e.getImageList()) {
            if (image.getType() == TIMImageType.Thumb){
                image.getImage(new TIMValueCallBack<byte[]>() {
                    @Override
                    public void onError(int code, String desc) {//获取图片失败
                        //错误码code和错误描述desc，可用于定位请求失败原因
                        //错误码code含义请参见错误码表
                        LogUtils.d(TAG, "getImage failed. code: " + code + " errmsg: " + desc);
                    }

                    @Override
                    public void onSuccess(byte[] data) {//成功，参数为图片数据
                        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        ImageView imageView = new ImageView(MyApplication.getContext());
                        imageView.setImageBitmap(bitmap);
                        getBubbleView(viewHolder).removeAllViews();
                        getBubbleView(viewHolder).addView(imageView);
                    }
                });
            }
        }

    }

    /**
     * 获取消息摘要
     */
    @Override
    public String getSummary() {
        return MyApplication.getContext().getString(R.string.summary_image);
    }
}
