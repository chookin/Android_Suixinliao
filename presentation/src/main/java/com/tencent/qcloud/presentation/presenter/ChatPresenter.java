package com.tencent.qcloud.presentation.presenter;

import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMMessage;
import com.tencent.qcloud.presentation.event.MessageEvent;
import com.tencent.qcloud.presentation.viewfeatures.ChatView;

import java.util.Observable;
import java.util.Observer;

/**
 * 聊天界面逻辑
 */
public class ChatPresenter extends Presenter implements Observer {

    private ChatView view;
    private String identify;
    private TIMConversationType type;

    public ChatPresenter(ChatView view,String identify,TIMConversationType type){
        //注册消息监听
        MessageEvent.getInstance().addObserver(this);
        this.view = view;
        this.identify = identify;
        this.type = type;
    }


    /**
     * 加载页面逻辑
     */
    @Override
    public void start() {

    }


    /**
     * 中止页面逻辑
     */
    @Override
    public void stop() {

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
            if (msg.getConversation().getPeer().equals(identify)&&msg.getConversation().getType()==type){
                if (msg.getElementCount()!=0){
                    view.showMessage(msg);
                }
            }
        }
    }
}
