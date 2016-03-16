package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.TIMFriendGroup;
import com.tencent.TIMFriendResult;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.tencent.qcloud.presentation.presenter.GetFriendGroupsPresenter;
import com.tencent.qcloud.presentation.presenter.ProfilePresenter;
import com.tencent.qcloud.presentation.viewfeatures.MyFriendGroupInfo;
import com.tencent.qcloud.presentation.viewfeatures.ProfileView;
import com.tencent.qcloud.timchat.R;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends Activity implements ProfileView,MyFriendGroupInfo {
    private static final String TAG = ProfileActivity.class.getSimpleName();
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
        presenter.start();
        mGetFriendGroupsPresenter = new GetFriendGroupsPresenter(this,this);
        mGetFriendGroupsPresenter.getFriendGroupList();
        mGroupList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mGetFriendGroupsPresenter.addFriendsToFriendGroup(groups.get(i).toString(), Id, new TIMValueCallBack<List<TIMFriendResult>>() {
                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onSuccess(List<TIMFriendResult> timFriendResults) {
                        Toast.makeText(ProfileActivity.this, "add to group succ!!!", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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
