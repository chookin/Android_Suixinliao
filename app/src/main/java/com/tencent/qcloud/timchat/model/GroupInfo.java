package com.tencent.qcloud.timchat.model;

import com.tencent.TIMGroupAssistantListener;
import com.tencent.TIMGroupDetailInfo;
import com.tencent.TIMGroupManager;
import com.tencent.TIMGroupMemberInfo;
import com.tencent.TIMGroupSettings;
import com.tencent.qcloud.presentation.presenter.GroupManagerPresenter;
import com.tencent.qcloud.presentation.viewfeatures.GroupInfoView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 群相关数据缓存，底层IMSDK会维护本地存储
 */
public class GroupInfo implements TIMGroupAssistantListener, GroupInfoView {

    private List<TIMGroupDetailInfo> groupList;
    private GroupManagerPresenter presenter;

    private GroupInfo(){
    }

    private static GroupInfo instance = new GroupInfo();

    public static GroupInfo getInstance(){
        return instance;
    }

    public void init(){
        groupList = new ArrayList<>();
        //开启IMSDK本地存储
        TIMGroupSettings settings = new TIMGroupSettings();
        settings.setIsStorageEnabled(true);
        settings.setGroupInfoOptions(settings.new Options());
        settings.setMemberInfoOptions(settings.new Options());
        TIMGroupManager.getInstance().getGroupAssistant().initGroupSettings(settings);
        //设置群资料变更监听
        TIMGroupManager.getInstance().getGroupAssistant().setListener(this);
        presenter = new GroupManagerPresenter(this);
        presenter.getGroupList();
    }

    @Override
    public void onMemberJoin(String s, List<TIMGroupMemberInfo> list) {

    }

    @Override
    public void onMemberQuit(String s, List<String> list) {

    }

    @Override
    public void onMemberUpdate(String s, List<TIMGroupMemberInfo> list) {

    }

    @Override
    public void onGroupAdd(TIMGroupDetailInfo timGroupDetailInfo) {
        groupList.add(timGroupDetailInfo);
    }

    @Override
    public void onGroupDelete(String s) {
        Iterator<TIMGroupDetailInfo> iterator =groupList.iterator();
        while (iterator.hasNext()){
            TIMGroupDetailInfo info = iterator.next();
            if (info.getGroupId().equals(s)){
                iterator.remove();
                break;
            }
        }

    }

    @Override
    public void onGroupUpdate(TIMGroupDetailInfo timGroupDetailInfo) {

    }

    /**
     * 判断是否在群内
     *
     * @param groupId 目标群ID
     */
    public boolean isInGroup(String groupId){
        for (TIMGroupDetailInfo info : groupList){
            if (info.getGroupId().equals(groupId))
                return true;
        }
        return false;
    }


    /**
     * 显示群资料
     *
     * @param groupInfos 群资料信息列表
     */
    @Override
    public void showGroupInfo(List<TIMGroupDetailInfo> groupInfos) {
        groupList.clear();
        groupList.addAll(groupInfos);
    }
}
