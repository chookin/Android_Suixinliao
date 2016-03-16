package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.tencent.TIMFriendGroup;
import com.tencent.TIMUserProfile;
import com.tencent.qcloud.presentation.presenter.GetFriendGroupsPresenter;
import com.tencent.qcloud.presentation.presenter.ProfilePresenter;
import com.tencent.qcloud.presentation.viewfeatures.MyFriendGroupInfo;
import com.tencent.qcloud.presentation.viewfeatures.ProfileView;
import com.tencent.qcloud.timchat.R;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends Activity implements ProfileView,MyFriendGroupInfo {

    private ProfilePresenter presenter;
    private GetFriendGroupsPresenter mGetFriendGroupsPresenter;
    private ArrayList<String> groups = new ArrayList<String>();
    private Spinner mGroupList;
    ArrayAdapter<String> mAdapter;
    private String Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mGroupList = (Spinner)findViewById(R.id.set_group);
        Id = getIntent().getStringExtra("identify");
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, groups);
        mGroupList.setAdapter(mAdapter);
        presenter = new ProfilePresenter(this, Id);
        presenter.getProfile();
        mGetFriendGroupsPresenter = new GetFriendGroupsPresenter(this,this);
        mGetFriendGroupsPresenter.getFriendGroupList();
    }

    /**
     * 显示用户信息
     *
     * @param profile
     */
    @Override
    public void showProfile(TIMUserProfile profile) {
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(profile.getNickName());
//        TextView id = (TextView) findViewById(R.id.id);
//        id.setText(profile.getIdentifier());
//        TextView remark = (TextView) findViewById(R.id.remark);
//        remark.setText(profile.getRemark());
    }

    @Override
    public void showMyGroupList(List<TIMFriendGroup> timFriendGroups) {
        for(TIMFriendGroup groupItem : timFriendGroups){
            groups.add(groupItem.getGroupName());
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showGroupMember(List<TIMUserProfile> timUserProfiles) {}
}
