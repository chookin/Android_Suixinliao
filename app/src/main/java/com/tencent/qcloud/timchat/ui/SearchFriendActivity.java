package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.tencent.TIMCallBack;
import com.tencent.TIMFriendAllowType;
import com.tencent.TIMUserProfile;
import com.tencent.qcloud.presentation.presenter.FriendshipManagerPresenter;
import com.tencent.qcloud.presentation.presenter.SearchFriendPresenter;
import com.tencent.qcloud.presentation.viewfeatures.FriendInfoView;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.ProfileAdapter1;
import com.tencent.qcloud.timchat.adapters.ProfileSummaryAdapter;
import com.tencent.qcloud.timchat.model.FriendProfile;
import com.tencent.qcloud.timchat.model.ProfileItem;
import com.tencent.qcloud.timchat.model.ProfileSummary;

import java.util.ArrayList;
import java.util.List;

/**
 * 查找添加新朋友
 */
public class SearchFriendActivity extends Activity implements FriendInfoView, View.OnClickListener, AdapterView.OnItemClickListener, KeyEvent.Callback {

    private final static String TAG = "SearchFriendActivity";

    private FriendshipManagerPresenter presenter;
    ListView mSearchList;
    EditText mSearchInput;
    ProfileSummaryAdapter adapter;
    List<ProfileSummary> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew);
        mSearchInput = (EditText) findViewById(R.id.inputSearch);
        mSearchList =(ListView) findViewById(R.id.list);
        adapter = new ProfileSummaryAdapter(this, R.layout.item_profile_summary, list);
        mSearchList.setAdapter(adapter);
        mSearchList.setOnItemClickListener(this);
        presenter = new FriendshipManagerPresenter(this);

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancel:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        list.get(i).showDetail(this);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                list.clear();
                adapter.notifyDataSetChanged();
                presenter.searchFriendByName(mSearchInput.getText().toString());
                presenter.searchFriendById(mSearchInput.getText().toString());
                return true;

            default:
                return super.onKeyUp(keyCode, event);
        }
    }

    /**
     * 显示好友信息
     *
     * @param users 好友资料列表
     */
    @Override
    public void showUserInfo(List<TIMUserProfile> users) {
        if (users == null) return;
        for (TIMUserProfile item : users){
            list.add(new FriendProfile(item));
        }
        adapter.notifyDataSetChanged();
    }
}
