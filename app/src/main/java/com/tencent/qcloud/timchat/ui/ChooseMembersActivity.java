package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.tencent.TIMFriendGroup;
import com.tencent.TIMGroupBaseInfo;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.tencent.qcloud.presentation.presenter.GetFriendGroupsPresenter;
import com.tencent.qcloud.presentation.presenter.ManagerGroupListPresenter;
import com.tencent.qcloud.presentation.viewfeatures.MyFriendGroupInfo;
import com.tencent.qcloud.presentation.viewfeatures.PulicGroupListView;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.ExpandGroupListAdapter;
import com.tencent.qcloud.timchat.ui.customview.TemplateTitle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 16/3/17.
 */
public class ChooseMembersActivity extends Activity implements MyFriendGroupInfo,PulicGroupListView {
    private List<TIMFriendGroup> mGroupTitleList = new ArrayList<TIMFriendGroup>();
    private List<TIMUserProfile> mOneGroupMembers = new ArrayList<TIMUserProfile>();
    private List<List<TIMUserProfile>> mAllGroupMembers = new ArrayList<List<TIMUserProfile>>();
    private ExpandGroupListAdapter mGroupListAdapter;
    private ExpandableListView mGroupListView;
    private GetFriendGroupsPresenter mGetFriendGroupsPresenter;
    private ManagerGroupListPresenter mManagerGroupListPresenter;
    private List<String> mGroupName = new ArrayList<String>();
    private String mGName,mType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosemembers);
        mGName = getIntent().getStringExtra("groupname");
        mType = getIntent().getStringExtra("type");
        mGroupListView = (ExpandableListView) findViewById(R.id.friendlist);
        mGroupListAdapter = new ExpandGroupListAdapter(this, mGroupTitleList, mAllGroupMembers,R.layout.item_choose_childmember);
        mGroupListView.setAdapter(mGroupListAdapter);
        mGetFriendGroupsPresenter = new GetFriendGroupsPresenter(this);
        mGetFriendGroupsPresenter.getFriendGroupList();
        mManagerGroupListPresenter = new ManagerGroupListPresenter(this,this);

        TemplateTitle title = (TemplateTitle) findViewById(R.id.choose_members_actionbar);
        title.setMoreTextAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mManagerGroupListPresenter.createGroup(mGName, mType, mGroupListAdapter.getChooseList(), new TIMValueCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        Toast.makeText(ChooseMembersActivity.this, "create room fail!!: "+s+" code:"+i, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(ChooseMembersActivity.this, "create room succ!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    @Override
    public void showMyGroupList(List<TIMFriendGroup> timFriendGroups) {
        mGroupTitleList.clear();
        mGroupName.clear();
        mAllGroupMembers.clear();
        for (TIMFriendGroup group : timFriendGroups) {
            mGroupTitleList.add(group);
            mGroupName.add(group.getGroupName());
            mAllGroupMembers.add(new ArrayList<TIMUserProfile>());

        }
        mGroupListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showGroupMember(String groupname, List<TIMUserProfile> timUserProfiles) {
        int index = mGroupName.indexOf(groupname);
        mOneGroupMembers.clear();
        for (TIMUserProfile member : timUserProfiles) {
            mOneGroupMembers.add(member);
            if(index !=-1)
                mAllGroupMembers.get(index).add(member);
        }
        mGroupListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMyPublicGroupListByType(List<TIMGroupBaseInfo> createGroup, List<TIMGroupBaseInfo> hostGroup, List<TIMGroupBaseInfo> memberGroup) {}




}
