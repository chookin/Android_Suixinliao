package com.tencent.qcloud.presentation.presenter;

import android.util.Log;

import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;
import com.tencent.qcloud.presentation.event.MessageEvent;
import com.tencent.qcloud.presentation.viewfeatures.ChatView;

import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * 聊天界面逻辑
 */
public class ChatPresenter extends Presenter implements Observer {

    private ChatView view;
    private TIMConversation conversation;
    private final int LAST_MESSAGE_NUM = 20;
    private final static String TAG = "ChatPresenter";

    public ChatPresenter(ChatView view,String identify,TIMConversationType type){
        this.view = view;
        conversation = TIMManager.getInstance().getConversation(type,identify);
    }


    /**
     * 加载页面逻辑
     */
    public void start() {
        //注册消息监听
        MessageEvent.getInstance().addObserver(this);
        getMessage();
    }


    /**
     * 中止页面逻辑
     */
    public void stop() {
        //注销消息监听
        MessageEvent.getInstance().deleteObserver(this);
    }

    /**
     * 中止页面逻辑
     *
     * @param message 发送的消息
     */
    public void sendMessage(TIMMessage message) {
        conversation.sendMessage(message, new TIMValueCallBack<TIMMessage>() {//发送消息回调
            @Override
            public void onError(int code, String desc) {//发送消息失败
                //错误码code和错误描述desc，可用于定位请求失败原因
                //错误码code含义请参见错误码表
                view.onSendMessageFail(code, desc);
            }

            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功
                view.onSendMessageSuccess(msg);
            }
        });
    }



    /**
     * This method is called if the specified {@code Observable} object's
     * {@code notifyObservers} method is called (because the {@code Observable}
     * object has been updated.
     *
     * @param observable the {@link Observable} object.
     * @param data       the data passed to {@link Observable#notifyObservers(Object)}.
     */
    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof MessageEvent){
            TIMMessage msg = (TIMMessage) data;
            if (msg.getConversation().getPeer().equals(conversation.getPeer())&&msg.getConversation().getType()==conversation.getType()){
                if (msg.getElementCount()!=0){
                    view.showMessage(msg);
                }
            }
        }
    }

    private void getMessage(){
        conversation.getMessage(LAST_MESSAGE_NUM, null, new TIMValueCallBack<List<TIMMessage>>() {
            @Override
            public void onError(int i, String s) {
                Log.e(TAG,"get message error"+s);
            }

            @Override
            public void onSuccess(List<TIMMessage> timMessages) {
                Collections.reverse(timMessages);
                for (TIMMessage msg:timMessages){
                    view.showMessage(msg);
                }
            }
        });
    }




}
