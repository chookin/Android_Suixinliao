package com.tencent.qcloud.presentation.presenter;

import android.content.Context;
import android.widget.Toast;

import com.tencent.TIMCallBack;
import com.tencent.TIMFriendResult;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMValueCallBack;
import com.tencent.qcloud.presentation.viewfeatures.ManagerGroupView;

import java.util.ArrayList;
import java.util.List;


/**
 * 群组信息逻辑
 */
public class ManagerFriendGroupPresenter extends Presenter {

    private ManagerGroupView view;
    private Context mContext;
    private  List<String> users = new ArrayList<String>() ;
    public ManagerFriendGroupPresenter(ManagerGroupView view, Context context) {
        this.view = view;
        mContext = context;
    }

    /**
     * 创建一个新分组
     */
    public void createEmptyFriendGroup(String groupname) {
        List<String> createGroup = new ArrayList<String>() ;
        createGroup.clear();
        createGroup.add(groupname);
        TIMFriendshipManager.getInstance().createFriendGroup(createGroup, users, new TIMValueCallBack<List<TIMFriendResult>>() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess(List<TIMFriendResult> timFriendResults) {
                Toast.makeText(mContext, "create group success!!!!", Toast.LENGTH_SHORT).show();
                view.notifyGroupListChange();
            }
        });
    }

    /**
     * 删除一个分组
     * @param id
     */
    public void deleteFriendGroup(String id){
        List<String> deletegroup = new ArrayList<String>();
        deletegroup.clear();
        deletegroup.add(id);
        TIMFriendshipManager.getInstance().deleteFriendGroup(deletegroup, new TIMCallBack() {
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
