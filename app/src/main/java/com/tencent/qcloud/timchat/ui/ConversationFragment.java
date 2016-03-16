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
import com.tencent.TIMConversationType;
import com.tencent.TIMGroupDetailInfo;
import com.tencent.TIMMessage;
import com.tencent.qcloud.presentation.presenter.ConversationPresenter;
import com.tencent.qcloud.presentation.presenter.GroupInfoPresenter;
import com.tencent.qcloud.presentation.viewfeatures.ConversationView;
import com.tencent.qcloud.presentation.viewfeatures.GroupInfoView;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.ConversationAdapter;
import com.tencent.qcloud.timchat.model.Conversation;
import com.tencent.qcloud.timchat.model.MessageFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 会话列表界面
 */
public class ConversationFragment extends Fragment implements ConversationView,GroupInfoView {

    private List<Conversation> conversationList = new LinkedList<>();
    private ConversationAdapter adapter;
    private ListView listView;
    private ConversationPresenter presenter;
    private GroupInfoPresenter groupInfoPresenter;
    private List<String> groupList;


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
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("identify", conversationList.get(position).getIdentify());
                intent.putExtra("name", conversationList.get(position).getName());
                intent.putExtra("type", conversationList.get(position).getType());
                conversationList.get(position).readAllMessage();
                adapter.notifyDataSetChanged();
                startActivity(intent);
            }
        });
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
                    this.conversationList.add(new Conversation(item));
                    groupList.add(item.getPeer());
                    break;
                case System:
                    updateSystemConversation(item);
                    break;
            }
        }
        groupInfoPresenter = new GroupInfoPresenter(this,groupList,true);
        groupInfoPresenter.getGroupDetailInfo();
    }

    /**
     * 更新最新消息显示
     *
     * @param message 最后一条消息
     */
    @Override
    public void updateMessage(TIMMessage message) {
        Conversation conversation = new Conversation(message.getConversation());
        Iterator<Conversation> iterator =conversationList.iterator();
        while (iterator.hasNext()){
            Conversation c = iterator.next();
            if (conversation.equals(c)){
                c.setLastMessage(MessageFactory.getMessage(message));
                iterator.remove();
                conversation = c;
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
    private void updateSystemConversation(TIMConversation conversation){
        for (Conversation item : conversationList){
            if (item.getType() == TIMConversationType.System){
                break;
            }
        }
        conversationList.add(new Conversation(conversation));
    }


    /**
     * 将会话设置会话列表第一个
     */
    private void upateToFirst(Conversation conversation){


    }


}
