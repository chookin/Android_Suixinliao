package com.tencent.qcloud.timchat.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tencent.TIMConversation;
import com.tencent.TIMFriendFutureItem;
import com.tencent.TIMGroupDetailInfo;
import com.tencent.TIMMessage;
import com.tencent.qcloud.presentation.presenter.ConversationPresenter;
import com.tencent.qcloud.presentation.presenter.FriendshipManagerPresenter;
import com.tencent.qcloud.presentation.presenter.GroupInfoPresenter;
import com.tencent.qcloud.presentation.viewfeatures.ConversationView;
import com.tencent.qcloud.presentation.viewfeatures.FriendshipMessageView;
import com.tencent.qcloud.presentation.viewfeatures.GroupInfoView;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.ConversationAdapter;
import com.tencent.qcloud.timchat.model.Conversation;
import com.tencent.qcloud.timchat.model.FriendshipConversation;
import com.tencent.qcloud.timchat.model.MessageFactory;
import com.tencent.qcloud.timchat.model.NomalConversation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 会话列表界面
 */
public class ConversationFragment extends Fragment implements ConversationView,GroupInfoView,FriendshipMessageView {

    private List<Conversation> conversationList = new LinkedList<>();
    private ConversationAdapter adapter;
    private ListView listView;
    private ConversationPresenter presenter;
    private GroupInfoPresenter groupInfoPresenter;
    private FriendshipManagerPresenter friendshipManagerPresenter;
    private List<String> groupList;
    private FriendshipConversation friendshipConversation;


    public ConversationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversation, container, false);
        listView = (ListView) view.findViewById(R.id.list);
        adapter = new ConversationAdapter(getActivity(), R.layout.item_conversation, conversationList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                conversationList.get(position).navToDetail(getActivity());
                adapter.notifyDataSetChanged();
            }
        });
        friendshipManagerPresenter = new FriendshipManagerPresenter(this);
        presenter = new ConversationPresenter(this);
        presenter.getConversation();
        return view;

    }


    /**
     * 初始化界面或刷新界面
     *
     * @param conversationList
     */
    @Override
    public void initView(List<TIMConversation> conversationList) {
        this.conversationList.clear();
        groupList = new ArrayList<>();
        for (TIMConversation item:conversationList){
            switch (item.getType()){
                case C2C:
                case Group:
                    this.conversationList.add(new NomalConversation(item));
                    groupList.add(item.getPeer());
                    break;
            }
        }
        groupInfoPresenter = new GroupInfoPresenter(this,groupList,true);
        groupInfoPresenter.getGroupDetailInfo();
        updateSystemConversation();
    }

    /**
     * 更新最新消息显示
     *
     * @param message 最后一条消息
     */
    @Override
    public void updateMessage(TIMMessage message) {
        if (message == null){
            adapter.notifyDataSetChanged();
            return;
        }
        Conversation conversation = new NomalConversation(message.getConversation());
        Iterator<Conversation> iterator =conversationList.iterator();
        while (iterator.hasNext()){
            Conversation c = iterator.next();
            if (conversation.equals(c)){
                NomalConversation nomalConversation = (NomalConversation) c;
                nomalConversation.setLastMessage(MessageFactory.getMessage(message));
                iterator.remove();
                conversation = nomalConversation;
                break;
            }
        }
        conversationList.add(conversation);
        Collections.sort(conversationList);
        adapter.notifyDataSetChanged();
    }

    /**
     * 显示群资料
     *
     * @param groupInfos 群资料信息列表
     */
    @Override
    public void showGroupInfo(List<TIMGroupDetailInfo> groupInfos) {
        for(Conversation item:conversationList){
            for (TIMGroupDetailInfo info:groupInfos){
                if (info.getGroupId().equals(item.getIdentify())){
                    item.setName(info.getGroupName());
                }
            }
        }
        adapter.notifyDataSetChanged();
    }


    /**
     * 更新系统消息
     */
    private void updateSystemConversation(){
        friendshipManagerPresenter.getFriendshipLastMessage();

    }


    /**
     * 获取好友关系链管理系统消息未读数的回调
     *
     * @param unreadCount 未读数量
     */
    @Override
    public void onGetFriendshipUnreadCount(long unreadCount) {
        friendshipConversation.setUnreadCount(unreadCount);
        adapter.notifyDataSetChanged();
    }

    /**
     * 获取好友关系链管理系统最后一条消息的回调
     *
     * @param message 最后一条消息
     * @param unreadCount 未读数
     */
    @Override
    public void onGetFriendshipLastMessage(TIMFriendFutureItem message, long unreadCount) {
        if (friendshipConversation == null){
            friendshipConversation = new FriendshipConversation(message);
            conversationList.add(friendshipConversation);
        }else{
            friendshipConversation.setLastMessage(message);
        }
        friendshipConversation.setUnreadCount(unreadCount);
        Collections.sort(conversationList);
        adapter.notifyDataSetChanged();
    }
}
