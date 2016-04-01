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
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.TIMConversationType;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.ExpandGroupListAdapter;
import com.tencent.qcloud.presentation.event.FriendshipInfo;
import com.tencent.qcloud.timchat.model.GroupInfo;
import com.tencent.qcloud.timchat.ui.customview.TemplateTitle;

import java.util.Observable;
import java.util.Observer;

/**
 * 联系人界面
 */
public class ContactFragment extends Fragment implements  View.OnClickListener, Observer {


    private ExpandGroupListAdapter mGroupListAdapter;
    private ExpandableListView mGroupListView;
    private LinearLayout mNewFriBtn, mPublicGroupBtn, mChatRoomBtn,mPrivateGroupBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contactLayout = inflater.inflate(R.layout.fragment_contact, container, false);
        mGroupListView = (ExpandableListView) contactLayout.findViewById(R.id.grouplist);
        mNewFriBtn = (LinearLayout) contactLayout.findViewById(R.id.btnNewFriend);
        mNewFriBtn.setOnClickListener(this);
        mPublicGroupBtn = (LinearLayout) contactLayout.findViewById(R.id.btnPublicGroup);
        mPublicGroupBtn.setOnClickListener(this);
        mChatRoomBtn = (LinearLayout) contactLayout.findViewById(R.id.btnChatroom);
        mChatRoomBtn.setOnClickListener(this);
        mPrivateGroupBtn = (LinearLayout) contactLayout.findViewById(R.id.btnPrivateGroup);
        mPrivateGroupBtn.setOnClickListener(this);
        TemplateTitle title = (TemplateTitle) contactLayout.findViewById(R.id.contact_antionbar);
        title.setMoreImgAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoveDialog();
            }
        });
        mGroupListAdapter = new ExpandGroupListAdapter(getActivity(), FriendshipInfo.getInstance().getFriends());
        mGroupListView.setAdapter(mGroupListAdapter);
        mGroupListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                ProfileActivity.navToProfile(getActivity(),FriendshipInfo.getInstance().getFriends().get(groupPosition).getProfiles().get(childPosition).getIdentifier());
                return false;
            }
        });
        mGroupListAdapter.notifyDataSetChanged();
        return contactLayout;
    }



    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnNewFriend) {
            Intent intent = new Intent(getActivity(), FriendshipManageMessageActivity.class);
            getActivity().startActivity(intent);
        }
        if (view.getId() == R.id.btnPublicGroup) {
            showGroups(GroupInfo.publicGroup);
        }
        if (view.getId() == R.id.btnChatroom) {
            showGroups(GroupInfo.chatRoom);
        }
        if (view.getId() == R.id.btnPrivateGroup) {
            showGroups(GroupInfo.privateGroup);
        }
    }

    private Dialog inviteDialog;
    private TextView addFriend, managerGroup,addGroup;

    private void showMoveDialog() {
        inviteDialog = new Dialog(getActivity(), R.style.dialog);
        inviteDialog.setContentView(R.layout.contact_more);
        addFriend = (TextView) inviteDialog.findViewById(R.id.add_friend);
        managerGroup = (TextView) inviteDialog.findViewById(R.id.manager_group);
        addGroup = (TextView) inviteDialog.findViewById(R.id.add_group);
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchFriendActivity.class);
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
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchGroupActivity.class);
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


    /**
     * This method is called if the specified {@code Observable} object's
     * {@code notifyObservers} method is called (because the {@code Observable}
     * object has been updated.
     *
     * @param observable the {@link Observable} object.
     * @param data       the data passed to {@link Observable#notifyObservers(Object)}.
     */
    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof FriendshipInfo){
            mGroupListAdapter.notifyDataSetChanged();
        }
    }

    private void showGroups(String type){
        Intent intent = new Intent(getActivity(), GroupListActivity.class);
        intent.putExtra("type", type);
        getActivity().startActivity(intent);
    }
}
