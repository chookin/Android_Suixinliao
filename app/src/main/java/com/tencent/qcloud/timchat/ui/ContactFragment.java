package com.tencent.qcloud.timchat.ui;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tencent.TIMFriendGroup;
import com.tencent.TIMUserProfile;
import com.tencent.qcloud.presentation.presenter.GetFriendGroupsPresenter;
import com.tencent.qcloud.presentation.viewfeatures.MyFriendGroupInfo;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.ExpandGroupListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 联系人界面
 */
public class ContactFragment extends Fragment implements MyFriendGroupInfo, View.OnClickListener, AdapterView.OnItemClickListener {
    private GetFriendGroupsPresenter mGetFriendGroupsPresenter;//获取分组的数据Presenter

    public ContactFragment() {
    }

    private List<TIMFriendGroup> mGroupName = new ArrayList<TIMFriendGroup>();
    private List<TIMUserProfile> mOneGroupMembers = new ArrayList<TIMUserProfile>();
    private List<List<TIMUserProfile>> mAllGroupMembers = new ArrayList<List<TIMUserProfile>>();
    private ExpandGroupListAdapter mGroupListAdapter;
    private ExpandableListView mGroupListView;
    private TextView mMoreBtn;
    private FrameLayout mNewFriBtn;
    private int mSelectItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contactLayout = inflater.inflate(R.layout.fragment_contact, container, false);
        mGroupListView = (ExpandableListView) contactLayout.findViewById(R.id.grouplist);
        mGroupListView.setOnItemClickListener(this);
        mNewFriBtn =(FrameLayout)contactLayout.findViewById(R.id.newfriend_btn);
        mNewFriBtn.setOnClickListener(this);
        mMoreBtn = (TextView) contactLayout.findViewById(R.id.contact_add);
        mMoreBtn.setOnClickListener(this);
        mGroupListAdapter = new ExpandGroupListAdapter(getActivity(),mGroupName, mAllGroupMembers);
        mGroupListView.setAdapter(mGroupListAdapter);
        mGetFriendGroupsPresenter = new GetFriendGroupsPresenter(this,getActivity());
        return contactLayout;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGetFriendGroupsPresenter.getFriendGroupList();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.contact_add) {
            showMoveDialog();
        }
        if(view.getId() == R.id.newfriend_btn){
            Intent intent = new Intent(getActivity(),NewFriendActivity.class);
            getActivity().startActivity(intent);

        }
    }

    private Dialog inviteDialog;
    private TextView addFriend, managerGroup;
    private void showMoveDialog() {
        inviteDialog = new Dialog(getActivity(), R.style.dialog);
        inviteDialog.setContentView(R.layout.contact_more);
        addFriend = (TextView) inviteDialog.findViewById(R.id.add_friend);
        managerGroup = (TextView) inviteDialog.findViewById(R.id.manager_group);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddNewFriendActivity.class);
                getActivity().startActivity(intent);
                inviteDialog.dismiss();
            }
        });
        managerGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ManagerGroupActivity.class);
                getActivity().startActivity(intent);
                inviteDialog.dismiss();
            }
        });
        Window window = inviteDialog.getWindow();
        window.setGravity(Gravity.TOP|Gravity.RIGHT);
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setAttributes(lp);
        inviteDialog.show();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        mSelectItem = position;
    }

    @Override
    public void showMyGroupList(List<TIMFriendGroup> timFriendGroups) {
        mGroupName.clear();
        for(TIMFriendGroup group: timFriendGroups){
            mGroupName.add(group);
        }
        mGroupListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showGroupMember(List<TIMUserProfile> timUserProfiles) {
        mOneGroupMembers.clear();
        for(TIMUserProfile member: timUserProfiles){
            mOneGroupMembers.add(member);

        }
        mAllGroupMembers.add(mOneGroupMembers);
        mGroupListAdapter.notifyDataSetChanged();
    }
}
