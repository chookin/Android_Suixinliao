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
import android.widget.Toast;

import com.tencent.TIMFriendGroup;
import com.tencent.TIMUserProfile;
import com.tencent.qcloud.presentation.presenter.GetFriendGroupsPresenter;
import com.tencent.qcloud.presentation.viewfeatures.MyFriendGroupInfo;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.ExpandGroupListAdapter;
import com.tencent.qcloud.timchat.ui.customview.TemplateTitle;

import java.util.ArrayList;
import java.util.List;

/**
 * 联系人界面
 */
public class ContactFragment extends Fragment implements MyFriendGroupInfo, View.OnClickListener, AdapterView.OnItemClickListener {
    private GetFriendGroupsPresenter mGetFriendGroupsPresenter;//获取分组的数据Presenter

    public ContactFragment() {
    }

    private List<TIMFriendGroup> mGroupTitleList = new ArrayList<TIMFriendGroup>();
    private List<String> mGroupName = new ArrayList<String>();

    private List<TIMUserProfile> mOneGroupMembers = new ArrayList<TIMUserProfile>();
    private List<List<TIMUserProfile>> mAllGroupMembers = new ArrayList<List<TIMUserProfile>>();
    private ExpandGroupListAdapter mGroupListAdapter;
    private ExpandableListView mGroupListView;
    private TextView mMoreBtn;
    private FrameLayout mNewFriBtn, mPublicGroupBtn, mChatRoomBtn,mPrivateGroupBtn;
    private int mSelectItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contactLayout = inflater.inflate(R.layout.fragment_contact, container, false);
        mGroupListView = (ExpandableListView) contactLayout.findViewById(R.id.grouplist);
        mGroupListView.setOnItemClickListener(this);
        mNewFriBtn = (FrameLayout) contactLayout.findViewById(R.id.newfriend_btn);
        mNewFriBtn.setOnClickListener(this);
        mPublicGroupBtn = (FrameLayout) contactLayout.findViewById(R.id.btn_public_group);
        mPublicGroupBtn.setOnClickListener(this);
        mChatRoomBtn = (FrameLayout) contactLayout.findViewById(R.id.btn_chatroom);
        mChatRoomBtn.setOnClickListener(this);
        mPrivateGroupBtn = (FrameLayout) contactLayout.findViewById(R.id.btn_private_group);
        mPrivateGroupBtn.setOnClickListener(this);
        TemplateTitle title = (TemplateTitle) contactLayout.findViewById(R.id.contact_antionbar);
        title.setMoreImgAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoveDialog();
            }
        });
        mGroupListAdapter = new ExpandGroupListAdapter(getActivity(), mGroupTitleList, mAllGroupMembers);
        mGroupListView.setAdapter(mGroupListAdapter);
        mGroupListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                Toast.makeText(getActivity(), "" + mAllGroupMembers.get(groupPosition).get(childPosition).getIdentifier(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra("identify", mAllGroupMembers.get(groupPosition).get(childPosition).getIdentifier());
                startActivity(intent);
                return false;
            }
        });
        mGetFriendGroupsPresenter = new GetFriendGroupsPresenter(this);
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
//        if (view.getId() == R.id.contact_add) {
//            showMoveDialog();
//        }
        if (view.getId() == R.id.newfriend_btn) {
            Intent intent = new Intent(getActivity(), NewFriendActivity.class);
            getActivity().startActivity(intent);

        }
        if (view.getId() == R.id.btn_public_group) {
            Intent intent = new Intent(getActivity(), GroupManagerActivity.class);
            intent.putExtra("type", "Public");
            getActivity().startActivity(intent);
        }
        if (view.getId() == R.id.btn_chatroom) {
            Intent intent = new Intent(getActivity(), GroupManagerActivity.class);
            intent.putExtra("type", "ChatRoom");
            getActivity().startActivity(intent);
        }
        if (view.getId() == R.id.btn_private_group) {
            Intent intent = new Intent(getActivity(), GroupManagerActivity.class);
            intent.putExtra("type", "Private");
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
                Intent intent = new Intent(getActivity(), ManagerFriendGroupActivity.class);
                getActivity().startActivity(intent);
                inviteDialog.dismiss();
            }
        });
        Window window = inviteDialog.getWindow();
        window.setGravity(Gravity.TOP | Gravity.RIGHT);
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
            if (index != -1)
                mAllGroupMembers.get(index).add(member);
        }
        mGroupListAdapter.notifyDataSetChanged();
    }
}
