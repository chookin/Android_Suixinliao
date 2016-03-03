package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.tencent.TIMConversationType;
import com.tencent.TIMMessage;
import com.tencent.qcloud.presentation.presenter.ChatPresenter;
import com.tencent.qcloud.presentation.viewfeatures.ChatView;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.ChatAdapter;
import com.tencent.qcloud.timchat.model.Message;
import com.tencent.qcloud.timchat.model.TextMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends Activity implements ChatView {

    private List<Message> messageList = new ArrayList<>();
    private ChatAdapter adapter;
    private ListView listView;
    private ChatPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        final String identify = getIntent().getStringExtra("identify");
        final TIMConversationType type = (TIMConversationType) getIntent().getSerializableExtra("type");
        presenter = new ChatPresenter(this,identify,type);
        adapter = new ChatAdapter(this, R.layout.item_message, messageList);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        presenter.start();
    }

    /**
     * 显示消息
     *
     * @param message
     */
    @Override
    public void showMessage(TIMMessage message) {
        Message mMessage = null;
        switch (message.getElement(0).getType()){
            case Text:
            case Face:
                mMessage = new TextMessage(message);
                break;
        }
        if (mMessage != null){
            messageList.add(mMessage);
            adapter.notifyDataSetChanged();
        }
    }

}
