package com.tencent.qcloud.presentation.presenter;

import android.content.Context;
import android.util.Log;

import com.tencent.TIMGroupBaseInfo;
import com.tencent.TIMGroupManager;
import com.tencent.TIMGroupMemberInfo;
import com.tencent.TIMGroupMemberRoleType;
import com.tencent.TIMGroupSelfInfo;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.tencent.qcloud.presentation.viewfeatures.PulicGroupListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 16/3/17.
 */
public class ManagerGroupListPresenter extends Presenter {
    private Context mContext;
    private List<TIMGroupBaseInfo> mPublicGroupCreateList = new ArrayList<TIMGroupBaseInfo>();
    private List<TIMGroupBaseInfo> mPublicGroupHostList = new ArrayList<TIMGroupBaseInfo>();
    private List<TIMGroupBaseInfo> mPublicGroupMemberList = new ArrayList<TIMGroupBaseInfo>();
    private PulicGroupListView mPulicGroupListView;
    private static final String TAG = ManagerGroupListPresenter.class.getSimpleName();

    public ManagerGroupListPresenter(Context context, PulicGroupListView view) {
        mContext = context;
        mPulicGroupListView = view;
    }

    public void getMyGroupList(final String type) {
        TIMGroupManager.getInstance().getGroupList(new TIMValueCallBack<List<TIMGroupBaseInfo>>() {
            @Override
            public void onError(int i, String s) {
                Log.e(TAG, "onError code :" + i + "     " + s);
            }

            @Override
            public void onSuccess(List<TIMGroupBaseInfo> timGroupBaseInfos) {
                mPublicGroupCreateList.clear();
                mPublicGroupHostList.clear();
                mPublicGroupMemberList.clear();
                for (TIMGroupBaseInfo groupinfo : timGroupBaseInfos) {
                    if (groupinfo.getGroupType().equals(type)) {
                        definePulicGroupType(groupinfo);
                    }
                }
            }
        });
    }




    public void definePulicGroupType(final TIMGroupBaseInfo group) {
        TIMGroupManager.getInstance().getSelfInfo(group.getGroupId(), new TIMValueCallBack<TIMGroupSelfInfo>() {
            @Override
            public void onError(int i, String s) {
            }

            @Override
            public void onSuccess(TIMGroupSelfInfo timGroupSelfInfo) {
                String type = timGroupSelfInfo.getRole().toString();
                Log.i(TAG, "onSuccess type " + type);
                if (timGroupSelfInfo.getRole() == TIMGroupMemberRoleType.Owner) {
                    mPublicGroupCreateList.add(group);
                }
                if (timGroupSelfInfo.getRole() == TIMGroupMemberRoleType.Admin) {
                    mPublicGroupHostList.add(group);
                }
                if (timGroupSelfInfo.getRole() == TIMGroupMemberRoleType.Normal) {
                    mPublicGroupMemberList.add(group);
                }
                mPulicGroupListView.showMyPublicGroupListByType(mPublicGroupCreateList, mPublicGroupHostList, mPublicGroupMemberList);
            }
        });
    }


    /**
     * 创建群
     *
     * @param groupname 群名称
     * @param type      群类型
     * @param list
     * @param cb
     */
    public void createGroup(String groupname, String type, List<TIMUserProfile> list, TIMValueCallBack cb) {
        List<TIMGroupMemberInfo> memberinfos = new ArrayList<TIMGroupMemberInfo>();
        TIMGroupMemberInfo newMember;
        for (TIMUserProfile member : list) {
            newMember = new TIMGroupMemberInfo();
            newMember.setUser(member.getIdentifier());
//            newMember.setRoleType(TIMGroupMemberRoleType.Normal);
            memberinfos.add(newMember);
        }
        TIMGroupManager.CreateGroupParam groupGroupParam = TIMGroupManager.getInstance().new CreateGroupParam();
        groupGroupParam.setGroupName(groupname);
        groupGroupParam.setMembers(memberinfos);
        groupGroupParam.setGroupType(type);
        TIMGroupManager.getInstance().createGroup(groupGroupParam, cb);
    }
}
