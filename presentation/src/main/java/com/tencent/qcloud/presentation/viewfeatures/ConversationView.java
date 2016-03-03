package com.tencent.qcloud.presentation.viewfeatures;

import com.tencent.TIMConversation;

import java.util.List;

/**
 * 会话列表界面的接口
 */
public interface ConversationView extends MvpView {

    /**
     * 初始化界面或刷新界面
     */
    public void initView(List<TIMConversation> conversationList);
}
