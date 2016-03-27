package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.tencent.TIMGroupDetailInfo;
import com.tencent.qcloud.presentation.presenter.GroupManagerPresenter;
import com.tencent.qcloud.presentation.viewfeatures.GroupInfoView;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.ProfileAdapter;
import com.tencent.qcloud.timchat.model.ProfileItem;
import com.tencent.qcloud.timchat.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchGroupActivity extends Activity implements GroupInfoView, View.OnClickListener, KeyEvent.Callback{

    private final String TAG = "SearchGroupActivity";

    private GroupManagerPresenter groupManagerPresenter;
    private List<ProfileItem> searchResult= new ArrayList<>();
    private ProfileAdapter adapter;
    private EditText searchInput;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_group);
        searchInput = (EditText) findViewById(R.id.inputSearch);
        listView =(ListView) findViewById(R.id.list);
        adapter = new ProfileAdapter(this, searchResult);
        listView.setAdapter(adapter);
        groupManagerPresenter = new GroupManagerPresenter(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchGroupActivity.this, GroupProfileActivity.class);
                intent.putExtra("identify", searchResult.get(position).getID());
                startActivity(intent);
            }
        });
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel:
                finish();
                break;
        }
    }



    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
//                groupManagerPresenter.searchGroupByID(searchInput.getText().toString());
                groupManagerPresenter.searchGroupByName(searchInput.getText().toString());
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }

    /**
     * 显示群资料
     *
     * @param groupInfos 群资料信息列表
     */
    @Override
    public void showGroupInfo(List<TIMGroupDetailInfo> groupInfos) {
        LogUtils.i(TAG, "get group info size " + groupInfos.size());
        searchResult.clear();
        for (TIMGroupDetailInfo item : groupInfos){
            ProfileItem profileItem = new ProfileItem();
            profileItem.setAvatarRes(R.drawable.head_group);
            profileItem.setID(item.getGroupId());
            profileItem.setName(item.getGroupName());
            profileItem.setDescription(item.getGroupId());
            searchResult.add(profileItem);
        }
        adapter.notifyDataSetChanged();
    }
}
