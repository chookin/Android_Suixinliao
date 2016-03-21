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
import com.tencent.qcloud.timchat.model.SearchResult;
import com.tencent.qcloud.timchat.ui.customview.TemplateTitle;

import java.util.ArrayList;
import java.util.List;


public class PublicGroupActivity extends Activity implements PulicGroupListView {
    ManagerGroupListPresenter mManagerGroupListPresenter;
    ListView mOwnerGroup,mManagerGroup,mJoinGroup;
    MySimpleAdapter mOwnerGroupAdapter;
    private ArrayList<SearchResult> mOwnGroupData;

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

        mOwnGroupData = new ArrayList<SearchResult>();
        mOwnerGroupAdapter = new MySimpleAdapter(this,mOwnGroupData);
        mOwnerGroup.setAdapter(mOwnerGroupAdapter);
        mManagerGroupListPresenter = new ManagerGroupListPresenter(this, this);
        mManagerGroupListPresenter.getMyPulicGroupList();

    }

    @Override
    public void showMyPublicGroupListByType(List<TIMGroupBaseInfo> createGroup, List<TIMGroupBaseInfo> hostGroup, List<TIMGroupBaseInfo> memberGroup) {
        for(TIMGroupBaseInfo groupinfo : createGroup){
            SearchResult groupData = new SearchResult();
            groupData.setID(groupinfo.getGroupId());
            groupData.setName(groupinfo.getGroupName());
            groupData.setAvatar(groupinfo.getFaceUrl());
            mOwnGroupData.add(groupData);
        }
        mOwnerGroupAdapter.notifyDataSetChanged();
    }

}
