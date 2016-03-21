package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.tencent.TIMGroupBaseInfo;
import com.tencent.qcloud.presentation.presenter.ManagerGroupListPresenter;
import com.tencent.qcloud.presentation.viewfeatures.PulicGroupListView;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.MySimpleAdapter;
import com.tencent.qcloud.timchat.model.ItemData;
import com.tencent.qcloud.timchat.ui.customview.TemplateTitle;

import java.util.ArrayList;
import java.util.List;


public class PublicGroupActivity extends Activity implements PulicGroupListView {
    ManagerGroupListPresenter mManagerGroupListPresenter;
    ListView mOwnerGroup,mManagerGroup,mJoinGroup;
    MySimpleAdapter mOwnerGroupAdapter,mManagerGroupAdapter,mJoinGroupAdapter;
    private ArrayList<ItemData> mOwnGroupData,mManagerGroupData,mJoinGroupData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulicgroup);
        mOwnerGroup = (ListView)findViewById(R.id.created_group_list);
        mManagerGroup = (ListView)findViewById(R.id.hosted_group_list);
        mJoinGroup = (ListView)findViewById(R.id.joined_group_list);
        TemplateTitle title = (TemplateTitle) findViewById(R.id.public_group_actionbar);
        title.setMoreTextAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublicGroupActivity.this, CreateGroupActivity.class);
                startActivity(intent);
            }
        });
        mOwnGroupData = new ArrayList<ItemData>();
        mManagerGroupData = new ArrayList<ItemData>();
        mJoinGroupData = new ArrayList<ItemData>();
        mOwnerGroupAdapter = new MySimpleAdapter(this,mOwnGroupData);
        mManagerGroupAdapter = new MySimpleAdapter(this,mManagerGroupData);
        mJoinGroupAdapter = new MySimpleAdapter(this,mJoinGroupData);
        mOwnerGroup.setAdapter(mOwnerGroupAdapter);
        mManagerGroup.setAdapter(mManagerGroupAdapter);
        mJoinGroup.setAdapter(mJoinGroupAdapter);
        mManagerGroupListPresenter = new ManagerGroupListPresenter(this, this);
        mManagerGroupListPresenter.getMyPulicGroupList();

    }

    @Override
    public void showMyPublicGroupListByType(List<TIMGroupBaseInfo> createGroup, List<TIMGroupBaseInfo> hostGroup, List<TIMGroupBaseInfo> memberGroup) {
        mOwnGroupData.clear();
        mManagerGroupData.clear();
        mJoinGroupData.clear();
        for(TIMGroupBaseInfo groupinfo : createGroup){
            ItemData groupData = new ItemData();
            groupData.setID(groupinfo.getGroupId());
            groupData.setName(groupinfo.getGroupName());
            groupData.setAvatar(groupinfo.getFaceUrl());
            mOwnGroupData.add(groupData);
        }
        mOwnerGroupAdapter.notifyDataSetChanged();
        for(TIMGroupBaseInfo groupinfo : hostGroup){
            ItemData groupData = new ItemData();
            groupData.setID(groupinfo.getGroupId());
            groupData.setName(groupinfo.getGroupName());
            groupData.setAvatar(groupinfo.getFaceUrl());
            mManagerGroupData.add(groupData);
        }
        mManagerGroupAdapter.notifyDataSetChanged();
        for(TIMGroupBaseInfo groupinfo : memberGroup){
            ItemData groupData = new ItemData();
            groupData.setID(groupinfo.getGroupId());
            groupData.setName(groupinfo.getGroupName());
            groupData.setAvatar(groupinfo.getFaceUrl());
            mJoinGroupData.add(groupData);
        }
        mJoinGroupAdapter.notifyDataSetChanged();

    }

}
