package com.tencent.qcloud.presentation.presenter;

import com.tencent.TIMConversation;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.qcloud.presentation.event.MessageEvent;
import com.tencent.qcloud.presentation.viewfeatures.ConversationView;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof MessageEvent){
            TIMMessage msg = (TIMMessage) data;

        }
    }



    public void getConversation(){
        List<TIMConversation> list = new ArrayList<>();
        //获取会话个数
        long cnt = TIMManager.getInstance().getConversationCount();
        //遍历会话列表
        for(long i = 0; i < cnt; ++i) {
            //根据索引获取会话
            TIMConversation conversation = TIMManager.getInstance().getConversationByIndex(i);
            list.add(conversation);
        }
        view.initView(list);
    }

}
