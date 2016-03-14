package com.tencent.qcloud.presentation.presenter;

import android.content.Context;

import com.tencent.TIMFriendGroup;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.tencent.qcloud.presentation.viewfeatures.MyFriendGroupInfo;

import java.util.List;

/**
 * 获取自己分组的数据，
 */
public class GetFriendGroupsPresenter extends Presenter {


    private MyFriendGroupInfo view;
    private Context mContext;

    public GetFriendGroupsPresenter(MyFriendGroupInfo view, Context context) {
        this.view = view;
        mContext = context;
    }

    /**
     * 获取自己所在群组
     */
    public void getFriendGroupList() {
        TIMFriendshipManager.getInstance().getFriendGroups(null, new TIMValueCallBack<List<TIMFriendGroup>>() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess(List<TIMFriendGroup> timFriendGroups) {
                view.showMyGroupList(timFriendGroups);
                for (TIMFriendGroup group : timFriendGroups) {
                    getFriendProfile(group.getUsers());

                }
            }
        });
    }


    public void getFriendProfile(List<String> users) {
        TIMFriendshipManager.getInstance().getFriendsProfile(users, new TIMValueCallBack<List<TIMUserProfile>>() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                view.showGroupMember(timUserProfiles);
            }
        });
    }

}
