package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.TIMGroupBaseInfo;
import com.tencent.qcloud.presentation.presenter.ManagerMyGroupPresenter;
import com.tencent.qcloud.presentation.viewfeatures.ManagerGroupView;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.GroupListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 查找添加新朋友
 */
public class ManagerGroupActivity extends Activity implements ManagerGroupView, View.OnClickListener {
    ManagerMyGroupPresenter managerMyGroupPresenter;
    private ListView mMyGroupList;
    private List<TIMGroupBaseInfo> mMyListTitle;
    private GroupListAdapter mGroupListAdapter;
    private TextView mAddGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_group);
        managerMyGroupPresenter = new ManagerMyGroupPresenter(this, getBaseContext());
        mMyGroupList = (ListView) findViewById(R.id.group_list);
        mAddGroup = (TextView) findViewById(R.id.add_group);
        mAddGroup.setOnClickListener(this);
        managerMyGroupPresenter.getMyGroupList();
        mMyListTitle = new ArrayList<TIMGroupBaseInfo>();
        mGroupListAdapter = new GroupListAdapter(this, mMyListTitle, this);
        mMyGroupList.setAdapter(mGroupListAdapter);
    }

    @Override
    public void showMyGroupList(List<TIMGroupBaseInfo> timGroupBaseInfos) {
        mMyListTitle.clear();
        for (TIMGroupBaseInfo title : timGroupBaseInfos) {
            mMyListTitle.add(title);
        }
        mGroupListAdapter.notifyDataSetChanged();

    }

    @Override
    public void notifyGroupListChange() {
        managerMyGroupPresenter.getMyGroupList();
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
                    managerMyGroupPresenter.createEmptyGroup(groupname);
                }
                addGroupDialog.dismiss();
            }
        });
        addGroupDialog.show();
    }

    public void deleteGroup(int position) {
        deleteDialog(position);
    }


    private Dialog deleteGroupDialog;
    private void deleteDialog(final int position) {
        final TIMGroupBaseInfo groupinfo = mMyListTitle.get(position);
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
                managerMyGroupPresenter.deleteGroup(groupinfo.getGroupId());
                deleteGroupDialog.dismiss();
            }
        });
        deleteGroupDialog.show();
    }



}
