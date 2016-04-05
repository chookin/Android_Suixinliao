package com.tencent.qcloud.presentation.event;

import android.util.Log;

import com.tencent.TIMCallBack;
import com.tencent.TIMFriendGroup;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMFriendshipProxyListener;
import com.tencent.TIMFriendshipProxyStatus;
import com.tencent.TIMSNSChangeInfo;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * 好友关系链数据缓存，维持更新状态，底层IMSDK会维护本地存储
 * 由于IMSDK有内存缓存，所以每次关系链变更时全量同步数据，此处也可以只更新变量数据
 */
public class FriendshipEvent extends Observable implements TIMCallBack, TIMFriendshipProxyListener, TIMValueCallBack<List<TIMFriendGroup>> {

    private final String TAG = "FriendshipEvent";

    private boolean isInit;
    private List<TIMFriendGroup> friendGroupList;


    private FriendshipEvent(){}

    private static FriendshipEvent instance = new FriendshipEvent();

    public static FriendshipEvent getInstance(){
        return instance;
    }

    public void init(){
        friendGroupList = new ArrayList<>();
        TIMFriendshipManager.getInstance().getFriendshipProxy().syncWithFlags(0xff, null, this);
        TIMFriendshipManager.getInstance().getFriendshipProxy().setListener(this);
    }



    @Override
    public void onError(int i, String s) {
        Log.e(TAG, "sync friendship error " + s);
    }

    @Override
    public void onSuccess(List<TIMFriendGroup> timFriendGroups) {
        friendGroupList.clear();
        friendGroupList.addAll(timFriendGroups);
        setChanged();
        notifyObservers();
    }

    @Override
    public void onSuccess() {
        isInit = true;
        syncFriendship();
    }

    @Override
    public void OnProxyStatusChange(TIMFriendshipProxyStatus timFriendshipProxyStatus) {

    }

    @Override
    public void OnAddFriends(List<TIMUserProfile> list) {
        syncFriendship();
    }

    @Override
    public void OnDelFriends(List<String> list) {
        syncFriendship();
    }

    @Override
    public void OnFriendProfileUpdate(List<TIMUserProfile> list) {
        syncFriendship();
    }

    @Override
    public void OnAddFriendReqs(List<TIMSNSChangeInfo> list) {

    }

    @Override
    public void OnAddFriendGroups(List<TIMFriendGroup> list) {
        syncFriendship();
    }

    @Override
    public void OnDelFriendGroups(List<String> list) {
        syncFriendship();
    }

    @Override
    public void OnFriendGroupUpdate(List<TIMFriendGroup> list) {
        syncFriendship();
    }

    private void syncFriendship(){
        if (isInit){
            TIMFriendshipManager.getInstance().getFriendshipProxy().getFriendGroups(null, this);
        }
    }


    /**
     * 判断是否是好友
     *
     * @param identify 需判断的identify
     */
    public boolean isFriend(String identify){
        for (TIMFriendGroup group : friendGroupList){
            for (TIMUserProfile user : group.getProfiles()){
                if (user.getIdentifier().equals(identify)) return true;
            }
        }
        return false;
    }


    /**
     * 获取好友资料
     *
     * @param identify 好友id
     */
    public TIMUserProfile getProfile(String identify){
        for (TIMFriendGroup group : friendGroupList){
            for (TIMUserProfile user : group.getProfiles()){
                if (user.getIdentifier().equals(identify)) return user;
            }
        }
        return null;
    }


    /**
     * 获取好友分组列表
     *
     */
    public String[] getGroupList(){
        String[] groups = new String[friendGroupList.size()];
        for (int i = 0; i < friendGroupList.size(); ++i) groups[i] = friendGroupList.get(i).getGroupName();
        return groups;
    }

    public boolean isInit() {
        return isInit;
    }
}
