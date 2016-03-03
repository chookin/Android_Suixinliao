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
import com.tencent.qcloud.presentation.presenter.ConversationPresenter;
import com.tencent.qcloud.presentation.viewfeatures.ConversationView;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.ConversationAdapter;
import com.tencent.qcloud.timchat.model.Conversation;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversationFragment extends Fragment implements ConversationView {

    private List<Conversation> conversationList = new ArrayList<>();
    private ConversationAdapter adapter;
    private ListView listView;
    private ConversationPresenter presenter;


    public ConversationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversation, container, false);
        listView = (ListView) view.findViewById(R.id.list);
        adapter = new ConversationAdapter(getActivity(), R.layout.item_conversation, conversationList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),ChatActivity.class);
                intent.putExtra("identify",conversationList.get(position).getIdentify());
                intent.putExtra("type",conversationList.get(position).getType());
                startActivity(intent);
            }
        });
        presenter = new ConversationPresenter(this);
        presenter.start();
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
        for (TIMConversation item:conversationList){
            this.conversationList.add(new Conversation(item));
        }
        adapter.notifyDataSetChanged();
    }
}
