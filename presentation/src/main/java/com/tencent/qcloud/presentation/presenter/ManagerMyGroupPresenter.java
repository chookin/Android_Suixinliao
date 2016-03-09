package com.tencent.qcloud.presentation.presenter;

import android.content.Context;
import android.widget.Toast;

import com.tencent.TIMCallBack;
import com.tencent.TIMGroupBaseInfo;
import com.tencent.TIMGroupManager;
import com.tencent.TIMValueCallBack;
import com.tencent.qcloud.presentation.viewfeatures.ManagerGroupView;

import java.util.List;


/**
 * 群信息逻辑
 */
public class ManagerMyGroupPresenter extends Presenter {

    private ManagerGroupView view;
    private Context mContext;
    public ManagerMyGroupPresenter(ManagerGroupView view,Context context) {
        this.view = view;
        mContext = context;
    }


    /**
     * 获取自己所在群组
     */
    public void getMyGroupList() {
        TIMGroupManager.getInstance().getGroupList(new TIMValueCallBack<List<TIMGroupBaseInfo>>() {
            @Override
            public void onError(int i, String s) {

            }
            @Override
            public void onSuccess(List<TIMGroupBaseInfo> timGroupBaseInfos) {
                view.showMyGroupList(timGroupBaseInfos);
            }
        });
    }

    /**
     * 创建一个新群
     */
    public void createEmptyGroup(String groupname) {
        TIMGroupManager.CreateGroupParam groupParam = TIMGroupManager.getInstance().new CreateGroupParam();
        groupParam.setGroupType("Public");
        groupParam.setGroupName(groupname);
        TIMGroupManager.getInstance().createGroup(groupParam, new TIMValueCallBack<String>() {
            @Override
            public void onError(int i, String s) {
                Toast.makeText(mContext, "create group failed!!!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String s) {
                Toast.makeText(mContext, "create group success!!!!", Toast.LENGTH_SHORT).show();
                view.notifyGroupListChange();
            }
        });

    }


    public void deleteGroup(String id){
        TIMGroupManager.getInstance().deleteGroup(id, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess() {
                view.notifyGroupListChange();
                Toast.makeText(mContext, "delete group success!!!!", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
