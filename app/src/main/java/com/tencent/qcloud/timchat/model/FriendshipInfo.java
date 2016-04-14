package com.tencent.qcloud.timchat.model;


import com.tencent.TIMFriendGroup;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.tencent.qcloud.presentation.event.FriendshipEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * 好友列表缓存数据结构
 */
public class FriendshipInfo implements Observer, TIMValueCallBack<List<TIMFriendGroup>> {

    private List<String> groups;
    private Map<String, List<FriendProfile>> friends;

    private static FriendshipInfo instance = new FriendshipInfo();

    private FriendshipInfo(){
        groups = new ArrayList<>();
        friends = new HashMap<>();
        refresh();
    }

    public static FriendshipInfo getInstance(){
        return instance;
    }

    /**
     * This method is called if the specified {@code Observable} object's
     * {@code notifyObservers} method is called (because the {@code Observable}
     * object has been updated.
     *
     * @param observable the {@link Observable} object.
     * @param data       the data passed to {@link Observable#notifyObservers(Object)}.
     */
    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof FriendshipEvent){

        }
    }

    @Override
    public void onError(int i, String s) {

    }

    @Override
    public void onSuccess(List<TIMFriendGroup> timFriendGroups) {
        for (TIMFriendGroup group : timFriendGroups){
            groups.add(group.getGroupName());
            List<FriendProfile> friendItemList = new ArrayList<>();
            for (TIMUserProfile profile : group.getProfiles()){
                friendItemList.add(new FriendProfile(profile));
            }
            friends.put(group.getGroupName(), friendItemList);
        }
    }

    private void refresh(){
        if (FriendshipEvent.getInstance().isInit()){
            TIMFriendshipManager.getInstance().getFriendshipProxy().getFriendGroups(null, this);
        }
    }

    /**
     * 获取分组列表
     */
    public List<String> getGroups(){
        return groups;
    }

    public String[] getGroupsArray(){
        return groups.toArray(new String[groups.size()]);
    }


    /**
     * 获取好友列表摘要
     */
    public Map<String, List<FriendProfile>> getFriendSummaries(){
        return friends;
    }

    /**
     * 判断是否是好友
     *
     * @param identify 需判断的identify
     */
    public boolean isFriend(String identify){
        for (String key : friends.keySet()){
            for (FriendProfile profile : friends.get(key)){
                if (identify.equals(profile.getIdentify())) return true;
            }
        }
        return false;
    }


    /**
     * 获取好友资料
     *
     * @param identify 好友id
     */
    public FriendProfile getProfile(String identify){
        for (String key : friends.keySet()){
            for (FriendProfile profile : friends.get(key)){
                if (identify.equals(profile.getIdentify())) return profile;
            }
        }
        return null;
    }


}
