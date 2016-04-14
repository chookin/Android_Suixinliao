package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.tencent.TIMCallBack;
import com.tencent.TIMFriendFutureItem;
import com.tencent.qcloud.presentation.presenter.FriendshipManagerPresenter;
import com.tencent.qcloud.presentation.viewfeatures.FriendshipMessageView;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.FriendManageMessageAdapter;
import com.tencent.qcloud.timchat.model.FriendFuture;

import java.util.ArrayList;
import java.util.List;

public class FriendshipManageMessageActivity extends Activity implements FriendshipMessageView {


    private final String TAG = FriendshipManageMessageActivity.class.getSimpleName();
    private FriendshipManagerPresenter presenter;
    private ListView listView;
    private List<FriendFuture> list= new ArrayList<>();
    private FriendManageMessageAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendship_manage_message);
        listView = (ListView) findViewById(R.id.list);
        adapter = new FriendManageMessageAdapter(this, R.layout.item_two_line, list);
        listView.setAdapter(adapter);
        presenter = new FriendshipManagerPresenter(this);
        presenter.getFriendshipMessage();
    }

    /**
     * 获取好友关系链管理最后一条系统消息的回调
     *
     * @param message     最后一条消息
     * @param unreadCount 未读数
     */
    @Override
    public void onGetFriendshipLastMessage(TIMFriendFutureItem message, long unreadCount) {

    }

    /**
     * 获取好友关系链管理最后一条系统消息的回调
     *
     * @param message 消息列表
     */
    @Override
    public void onGetFriendshipMessage(List<TIMFriendFutureItem> message) {
        if (message != null && message.size() != 0){
            for (TIMFriendFutureItem item : message){
                list.add(new FriendFuture(item));
            }
            presenter.readFriendshipMessage(message.get(0).getAddTime());
        }
        adapter.notifyDataSetChanged();
    }
}
