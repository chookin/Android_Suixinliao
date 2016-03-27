package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.tencent.TIMGroupBaseInfo;
import com.tencent.qcloud.presentation.presenter.ManagerGroupListPresenter;
import com.tencent.qcloud.presentation.viewfeatures.PulicGroupListView;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.ProfileAdapter;
import com.tencent.qcloud.timchat.model.ProfileItem;
import com.tencent.qcloud.timchat.ui.customview.TemplateTitle;

import java.util.ArrayList;
import java.util.List;


/**
 * 群组管理类 用于创建群
 */
public class GroupManagerActivity extends Activity implements PulicGroupListView {
    ManagerGroupListPresenter mManagerGroupListPresenter;
    ListView mOwnerGroup,mManagerGroup,mJoinGroup;
    ProfileAdapter mOwnerGroupAdapter,mManagerGroupAdapter,mJoinGroupAdapter;
    private ArrayList<ProfileItem> mOwnGroupData,mManagerGroupData,mJoinGroupData;
    private TextView mSubtitleCreate,mSubtitleManager,mSubtitleJoin;
    private String mGroupType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulicgroup);
        mGroupType = getIntent().getStringExtra("type");
        mOwnerGroup = (ListView)findViewById(R.id.created_group_list);
        mManagerGroup = (ListView)findViewById(R.id.hosted_group_list);
        mJoinGroup = (ListView)findViewById(R.id.joined_group_list);

        mSubtitleCreate = (TextView)findViewById(R.id.subtitle_owner);
        mSubtitleManager =(TextView)findViewById(R.id.subtitle_manager);
        mSubtitleJoin =(TextView)findViewById(R.id.subtitle_join);

        TemplateTitle title = (TemplateTitle) findViewById(R.id.public_group_actionbar);

        mOwnGroupData = new ArrayList<ProfileItem>();
        mManagerGroupData = new ArrayList<ProfileItem>();
        mJoinGroupData = new ArrayList<ProfileItem>();
        mOwnerGroupAdapter = new ProfileAdapter(this,mOwnGroupData);
        mManagerGroupAdapter = new ProfileAdapter(this,mManagerGroupData);
        mJoinGroupAdapter = new ProfileAdapter(this,mJoinGroupData);
        mOwnerGroup.setAdapter(mOwnerGroupAdapter);
        mManagerGroup.setAdapter(mManagerGroupAdapter);
        mJoinGroup.setAdapter(mJoinGroupAdapter);
        mManagerGroupListPresenter = new ManagerGroupListPresenter(this, this);
        if(mGroupType.equals("ChatRoom")){
            title.setTitleText(getResources().getString(R.string.title_chatroom));
            title.setMoreTextContext(getResources().getString(R.string.btn__create_chatroom));
            mSubtitleCreate.setText(getResources().getString(R.string.subtitle_owner_chatrrom));
            mSubtitleManager.setText(getResources().getString(R.string.subtitle_hosting_chatroom));
            mSubtitleJoin.setText(getResources().getString(R.string.subtitle_joined_chatroom));
            mManagerGroupListPresenter.getMyGroupList(mGroupType);
        }
        if(mGroupType.equals("Public")){
            title.setTitleText(getResources().getString(R.string.title_public_group));
            title.setMoreTextContext(getResources().getString(R.string.btn__create_public_group));
            mSubtitleCreate.setText(getResources().getString(R.string.subtitle_created_public_group));
            mSubtitleManager.setText(getResources().getString(R.string.subtitle_hosting_public_group));
            mSubtitleJoin.setText(getResources().getString(R.string.subtitle_joined_public_group));
            mManagerGroupListPresenter.getMyGroupList(mGroupType);
        }
        if(mGroupType.equals("Private")){
            title.setTitleText(getResources().getString(R.string.title_private_group));
            title.setMoreTextContext(getResources().getString(R.string.btn__create_private_group));
            mSubtitleCreate.setText(getResources().getString(R.string.subtitle_owner_private_group));
            mSubtitleManager.setText(getResources().getString(R.string.subtitle_hosting_private_group));
            mSubtitleJoin.setText(getResources().getString(R.string.subtitle_joined_private_group));
            mManagerGroupListPresenter.getMyGroupList(mGroupType);
        }
        title.setMoreTextAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupManagerActivity.this, CreateGroupActivity.class);
                intent.putExtra("type", mGroupType);
                startActivity(intent);
            }
        });


    }

    /**
     * 数据方法
     * @param createGroup
     * @param hostGroup
     * @param memberGroup
     */
    @Override
    public void showMyPublicGroupListByType(List<TIMGroupBaseInfo> createGroup, List<TIMGroupBaseInfo> hostGroup, List<TIMGroupBaseInfo> memberGroup) {
        mOwnGroupData.clear();
        mManagerGroupData.clear();
        mJoinGroupData.clear();
        for(TIMGroupBaseInfo groupinfo : createGroup){
            ProfileItem groupData = new ProfileItem();
            groupData.setID(groupinfo.getGroupId());
            groupData.setName(groupinfo.getGroupName());
            groupData.setAvatarUrl(groupinfo.getFaceUrl());
            mOwnGroupData.add(groupData);
        }
        mOwnerGroupAdapter.notifyDataSetChanged();
        for(TIMGroupBaseInfo groupinfo : hostGroup){
            ProfileItem groupData = new ProfileItem();
            groupData.setID(groupinfo.getGroupId());
            groupData.setName(groupinfo.getGroupName());
            groupData.setAvatarUrl(groupinfo.getFaceUrl());
            mManagerGroupData.add(groupData);
        }
        mManagerGroupAdapter.notifyDataSetChanged();
        for(TIMGroupBaseInfo groupinfo : memberGroup){
            ProfileItem groupData = new ProfileItem();
            groupData.setID(groupinfo.getGroupId());
            groupData.setName(groupinfo.getGroupName());
            groupData.setAvatarUrl(groupinfo.getFaceUrl());
            mJoinGroupData.add(groupData);
        }
        mJoinGroupAdapter.notifyDataSetChanged();

    }

}
