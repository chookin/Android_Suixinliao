package com.tencent.qcloud.presentation.presenter;

import android.util.Log;

import com.tencent.TIMFriendGroup;
import com.tencent.TIMFriendResult;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.tencent.qcloud.presentation.viewfeatures.MyFriendGroupInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取自己分组的数据，
 */
public class GetFriendGroupsPresenter {


    private MyFriendGroupInfo view;
    private static final String TAG = GetFriendGroupsPresenter.class.getSimpleName();

    public GetFriendGroupsPresenter(MyFriendGroupInfo view) {
        this.view = view;
    }

    /**
     * 获取自己所在群组
     */
    public void getFriendGroupList() {
        Log.i(TAG, "getFriendGroupList ");
        TIMFriendshipManager.getInstance().getFriendGroups(null, new TIMValueCallBack<List<TIMFriendGroup>>() {
            @Override
            public void onError(int i, String s) {
                Log.d("", "error" + s);

            }

            @Override
            public void onSuccess(List<TIMFriendGroup> timFriendGroups) {
                view.showMyGroupList(timFriendGroups);
                for (TIMFriendGroup group : timFriendGroups) {
                    getFriendProfile(group.getGroupName(), group.getUsers());

                }
            }
        });
//        TIMFriendshipManager.getInstance().getFriendGroups(null, new TIMValueCallBack<List<TIMFriendGroup>>() {
//            @Override
//            public void onError(int i, String s) {
//
//            }
//
//            @Override
//            public void onSuccess(List<TIMFriendGroup> timFriendGroups) {
//                view.showMyGroupList(timFriendGroups);
//                for (TIMFriendGroup group : timFriendGroups) {
//                    getFriendProfile(group.getGroupName(), group.getUsers());
//
//                }
//            }
//        });
    }


    public void getFriendProfile(final String groupname, List<String> users) {
        TIMFriendshipManager.getInstance().getFriendsProfile(users, new TIMValueCallBack<List<TIMUserProfile>>() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                view.showGroupMember(groupname, timUserProfiles);
            }
        });
    }



}
