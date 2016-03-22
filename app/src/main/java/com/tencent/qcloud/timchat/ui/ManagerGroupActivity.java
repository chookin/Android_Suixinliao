package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.TIMFriendGroup;
import com.tencent.TIMUserProfile;
import com.tencent.qcloud.presentation.presenter.GetFriendGroupsPresenter;
import com.tencent.qcloud.presentation.presenter.ManagerFriendGroupPresenter;
import com.tencent.qcloud.presentation.viewfeatures.ManagerGroupView;
import com.tencent.qcloud.presentation.viewfeatures.MyFriendGroupInfo;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.GroupListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 查找添加新朋友
 */
public class ManagerGroupActivity extends Activity implements ManagerGroupView, MyFriendGroupInfo,View.OnClickListener {
    ManagerFriendGroupPresenter mManagerMyGroupPresenter;
    GetFriendGroupsPresenter mGetFriendGroupsPresenter;
    private ListView mMyGroupList;
    private List<TIMFriendGroup> mMyListTitle;
    private GroupListAdapter mGroupListAdapter;
    private LinearLayout mAddGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_group);
        mManagerMyGroupPresenter = new ManagerFriendGroupPresenter(this, getBaseContext());
        mMyGroupList = (ListView) findViewById(R.id.group_list);
        mAddGroup = (LinearLayout) findViewById(R.id.add_group);
        mAddGroup.setOnClickListener(this);
        mMyListTitle = new ArrayList<TIMFriendGroup>();
        mGroupListAdapter = new GroupListAdapter(this, mMyListTitle, this);
        mMyGroupList.setAdapter(mGroupListAdapter);
        mGetFriendGroupsPresenter =new GetFriendGroupsPresenter(this, getBaseContext());
        mGetFriendGroupsPresenter.getFriendGroupList();
    }


    @Override
    public void notifyGroupListChange() {
        mGetFriendGroupsPresenter.getFriendGroupList();
    }




    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_group) {
            addDialog();
        }
    }


    private Dialog addGroupDialog;
    private void addDialog() {
        addGroupDialog = new Dialog(this, R.style.dialog);
        addGroupDialog.setContentView(R.layout.dialog_addgroup);
        TextView btnYes = (TextView) addGroupDialog.findViewById(R.id.confirm_btn);
        TextView btnNo = (TextView) addGroupDialog.findViewById(R.id.cancel_btn);
        final EditText inputView = (EditText) addGroupDialog.findViewById(R.id.input_group_name);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addGroupDialog.dismiss();
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupname = inputView.getText().toString();
                if (groupname == null || groupname.equals("")) {
                    Toast.makeText(ManagerGroupActivity.this, "empty input", Toast.LENGTH_SHORT).show();
                } else {
                    mManagerMyGroupPresenter.createEmptyFriendGroup(groupname);
                }
                addGroupDialog.dismiss();
            }
        });
        Window window = addGroupDialog.getWindow();
        window.setGravity(Gravity.TOP);
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setAttributes(lp);
        addGroupDialog.show();
    }

    public void deleteGroup(int position) {
        deleteDialog(position);
    }


    private Dialog deleteGroupDialog;
    private void deleteDialog(final int position) {
        final TIMFriendGroup groupinfo = mMyListTitle.get(position);
        deleteGroupDialog = new Dialog(this, R.style.dialog);
        deleteGroupDialog.setContentView(R.layout.dialog_delete_group);
        TextView btnYes = (TextView) deleteGroupDialog.findViewById(R.id.confirm_btn);
        TextView btnNo = (TextView) deleteGroupDialog.findViewById(R.id.cancel_btn);
        TextView deleteGroup = (TextView) deleteGroupDialog.findViewById(R.id.select_delete_group);
        deleteGroup.setText(groupinfo.getGroupName());
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteGroupDialog.dismiss();
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mManagerMyGroupPresenter.deleteFriendGroup(groupinfo.getGroupName());
                deleteGroupDialog.dismiss();
            }
        });
        deleteGroupDialog.show();
    }


    @Override
    public void showMyGroupList(List<TIMFriendGroup> timFriendGroups) {
        mMyListTitle.clear();
        for(TIMFriendGroup group : timFriendGroups){
            mMyListTitle.add(group);
        }
        mGroupListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showGroupMember(String groupname,List<TIMUserProfile> timUserProfiles) {

    }
}
