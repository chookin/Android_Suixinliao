package com.tencent.qcloud.timchat.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.tencent.qcloud.presentation.presenter.GetMyGroupsPresenter;
import com.tencent.qcloud.presentation.viewfeatures.JoinGroupsInfo;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.GroupListAdapter;

/**
 * 联系人界面
 */
public class ContactFragment extends Fragment implements JoinGroupsInfo {
    private GetMyGroupsPresenter mGetMyGroupsPresenter;//获取分组的数据Presenter
    public ContactFragment() {
        mGetMyGroupsPresenter = new GetMyGroupsPresenter(this);
    }




    private GroupListAdapter mGroupListAdapter;
    private ExpandableListView mGroupListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View Contactlayout = (View) inflater.inflate(R.layout.fragment_contact, container, false);
        mGroupListView = (ExpandableListView)Contactlayout.findViewById(R.id.grouplist);
        mGroupListAdapter = new GroupListAdapter(getActivity());
        mGroupListView.setAdapter(mGroupListAdapter);
        return Contactlayout;
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
}
