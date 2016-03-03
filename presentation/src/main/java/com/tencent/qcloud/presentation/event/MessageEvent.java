package com.tencent.qcloud.presentation.event;


import com.tencent.TIMMessage;
import com.tencent.TIMMessageListener;

import java.util.List;
import java.util.Observable;

/**
 * 消息通知事件，上层界面可以订阅此事件
 */
public class MessageEvent extends Observable implements TIMMessageListener {


    private static final MessageEvent instance = new MessageEvent();

    private MessageEvent(){}

    public static MessageEvent getInstance(){
        return instance;
    }

    @Override
    public boolean onNewMessages(List<TIMMessage> list) {
        setChanged();
        for (TIMMessage item:list){
            notifyObservers(item);
        }
        return false;
    }
}
