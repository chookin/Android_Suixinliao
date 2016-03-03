package com.tencent.qcloud.timchat.ui;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.tencent.qcloud.presentation.presenter.GetMyGroupsPresenter;
import com.tencent.qcloud.presentation.viewfeatures.JoinGroupsInfo;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.GroupListAdapter;

/**
 * 联系人界面
 */
public class ContactFragment extends Fragment implements JoinGroupsInfo, View.OnClickListener {
    private GetMyGroupsPresenter mGetMyGroupsPresenter;//获取分组的数据Presenter

    public ContactFragment() {
        mGetMyGroupsPresenter = new GetMyGroupsPresenter(this);
    }


    private GroupListAdapter mGroupListAdapter;
    private ExpandableListView mGroupListView;
    private TextView mMoreBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contactLayout = inflater.inflate(R.layout.fragment_contact, container, false);
        mGroupListView = (ExpandableListView) contactLayout.findViewById(R.id.grouplist);
        mMoreBtn = (TextView) contactLayout.findViewById(R.id.contact_add);
        mMoreBtn.setOnClickListener(this);
        mGroupListAdapter = new GroupListAdapter(getActivity());
        mGroupListView.setAdapter(mGroupListAdapter);
        return contactLayout;
    }

    @Override
    public void onStart() {
        super.onStart();
        mGetMyGroupsPresenter.start();
    }

    @Override
    public void onStop() {
        mGetMyGroupsPresenter.stop();
        super.onStop();
    }

    /**
     * 显示自己的分组信息
     */
    @Override
    public void showJoinGroupList() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.contact_add) {
            showMoveDialog();
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

            }
        });

        managerGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Window window = inviteDialog.getWindow();
        window.setGravity(Gravity.TOP|Gravity.RIGHT);
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setAttributes(lp);
        inviteDialog.show();
    }



}
