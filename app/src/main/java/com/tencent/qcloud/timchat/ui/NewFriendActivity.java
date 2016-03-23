package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tencent.TIMFriendFutureItem;
import com.tencent.qcloud.presentation.presenter.GetFutureFriListPresenter;
import com.tencent.qcloud.presentation.presenter.ResponseFriInvitePresenter;
import com.tencent.qcloud.presentation.viewfeatures.AgreeNewFriend;
import com.tencent.qcloud.presentation.viewfeatures.GetFutureFriListData;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.NewFriAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 查找添加新朋友
 */
public class NewFriendActivity extends Activity implements AdapterView.OnItemClickListener, AgreeNewFriend, GetFutureFriListData {
    private String mSelectId;
    private ListView mNewFriList;
    private NewFriAdapter mNewFriAdapter;
    private List<TIMFriendFutureItem> mNewFriListReuslt;
    private GetFutureFriListPresenter mGetFutureFriListPresenter;
    private ResponseFriInvitePresenter mResponseFriInvitePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfriend);
        mNewFriList = (ListView) findViewById(R.id.newfriend_list);
        mNewFriList.setOnItemClickListener(this);
        mNewFriListReuslt = new ArrayList<TIMFriendFutureItem>();
        mNewFriAdapter = new NewFriAdapter(this, mNewFriListReuslt, this);
        mNewFriList.setAdapter(mNewFriAdapter);
        mGetFutureFriListPresenter = new GetFutureFriListPresenter(getBaseContext(), this);
        mResponseFriInvitePresenter = new ResponseFriInvitePresenter(getBaseContext(), this);
        mGetFutureFriListPresenter.getFutureFriList();
        mNewFriList.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TIMFriendFutureItem person = mNewFriListReuslt.get(i);
        Intent intent = new Intent(NewFriendActivity.this, NewFriendDetailActivity.class);
        intent.putExtra("id", person.getIdentifier());
        intent.putExtra("name", person.getProfile().getNickName());
        intent.putExtra("detail", person.getAddWording());
        startActivity(intent);
    }

    @Override
    public void showFutureFriListData(List<TIMFriendFutureItem> mFutureFriList) {
        for (TIMFriendFutureItem newFri : mFutureFriList) {
            mNewFriListReuslt.add(newFri);
        }
        mNewFriAdapter.notifyDataSetChanged();
    }

    public void confirmItemStatus(String id) {
        mSelectId = id;
        mResponseFriInvitePresenter.answerFriInvite(id, true);
    }

    @Override
    public void jumpIntoProfileActicity() {
        Intent intent = new Intent(NewFriendActivity.this, ProfileActivity.class);
        intent.putExtra("identify", mSelectId);
        startActivity(intent);
    }
}
