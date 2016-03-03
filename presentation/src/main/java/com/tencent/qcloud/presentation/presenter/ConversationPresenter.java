package com.tencent.qcloud.presentation.presenter;

import com.tencent.TIMConversation;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.qcloud.presentation.event.MessageEvent;
import com.tencent.qcloud.presentation.viewfeatures.ConversationView;

import java.util.Observable;
import java.util.Observer;

/**
 * 会话界面逻辑
 */
public class ConversationPresenter extends Presenter implements Observer {

    private ConversationView view;

    public ConversationPresenter(ConversationView view){
        //注册消息监听
        MessageEvent.getInstance().addObserver(this);
        this.view = view;
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

        }
    }

    /**
     * 加载页面逻辑
     */
    @Override
    public void start() {
        //获取会话个数
        long cnt = TIMManager.getInstance().getConversationCount();
        //遍历会话列表
        for(long i = 0; i < cnt; ++i) {
            //根据索引获取会话
            TIMConversation conversation = TIMManager.getInstance().getConversationByIndex(i);

        }
    }

    /**
     * 中止页面逻辑
     */
    @Override
    public void stop() {

    }
}
