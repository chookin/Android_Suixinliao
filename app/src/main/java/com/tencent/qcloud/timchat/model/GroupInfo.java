package com.tencent.qcloud.timchat.model;

import android.content.Context;
import android.content.Intent;

import com.tencent.TIMGroupDetailInfo;
import com.tencent.TIMGroupMemberRoleType;
import com.tencent.qcloud.presentation.event.GroupEvent;
import com.tencent.qcloud.presentation.presenter.GroupManagerPresenter;
import com.tencent.qcloud.presentation.viewfeatures.GroupInfoView;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.ui.GroupProfileActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * 群数据结构
 */
public class GroupInfo implements GroupInfoView,Observer {


    private Map<String, List<GroupItem>> groups;
    private GroupManagerPresenter presenter;
    public static final String publicGroup = "Public", privateGroup = "Private", chatRoom = "ChatRoom";

    private GroupInfo(){
        groups = new HashMap<>();
        groups.put(publicGroup,new ArrayList<GroupItem>());
        groups.put(privateGroup, new ArrayList<GroupItem>());
        groups.put(chatRoom, new ArrayList<GroupItem>());
        //注册群关系监听
        GroupEvent.getInstance().addObserver(this);
        presenter = new GroupManagerPresenter(this);
        if (GroupEvent.getInstance().isInit()){
            presenter.getGroupList();
        }
    }

    private static GroupInfo instance = new GroupInfo();

    public static GroupInfo getInstance(){
        return instance;
    }

    /**
     * 显示群资料
     *
     * @param groupInfos 群资料信息列表
     */
    @Override
    public void showGroupInfo(List<TIMGroupDetailInfo> groupInfos) {
        for (TIMGroupDetailInfo item : groupInfos){
            groups.get(item.getGroupType()).add(new GroupItem(item));
        }
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
        if (observable instanceof GroupEvent){
            if (data == null){
                presenter.getGroupList();
            }else{
                if (data instanceof TIMGroupDetailInfo){
                    updateGroup((TIMGroupDetailInfo) data);
                }else if (data instanceof String){
                    delGroup((String) data);
                }
            }
        }
    }

    private void updateGroup(TIMGroupDetailInfo info){
        for (GroupItem item : groups.get(info.getGroupType())){
            if (item.identify.equals(info.getGroupId())){
                item.update(info);
                return;
            }
        }
        groups.get(info.getGroupType()).add(new GroupItem(info));
    }

    private void delGroup(String id){
        for (String key : groups.keySet()){
            Iterator<GroupItem> iterator = groups.get(key).iterator();
            while(iterator.hasNext()){
                GroupItem item = iterator.next();
                if (item.identify.equals(id)){
                    iterator.remove();
                    return;
                }
            }
        }
    }

    /**
     * 是否在群内
     *
     * @param id 群identify
     */
    public boolean isInGroup(String id){
        for (String key : groups.keySet()){
            for (GroupItem item : groups.get(key)){
                if (item.identify.equals(id)) return true;
            }
        }
        return false;
    }

    /**
     * 按照群类型获取群
     *
     * @param type 群类型
     */
    public List<? extends ProfileSummary> getGroupListByType(String type){
        return groups.get(type);
    }


    public class GroupItem implements ProfileSummary{

        String name;
        String identify;
        //群类型, 目前支持三种群类型："Public", "Private", "ChatRoom"
        String type;
        TIMGroupMemberRoleType roleType;

        public GroupItem(TIMGroupDetailInfo data){
            name = data.getGroupName();
            identify = data.getGroupId();
            type = data.getGroupType();
        }

        public void update(TIMGroupDetailInfo data){
            name = data.getGroupName();
        }

        /**
         * 获取头像资源
         */
        @Override
        public int getAvatarRes() {
            return R.drawable.head_group;
        }

        /**
         * 获取头像地址
         */
        @Override
        public String getAvatarUrl() {
            return null;
        }

        /**
         * 获取名字
         */
        @Override
        public String getName() {
            return name;
        }

        /**
         * 获取描述信息
         */
        @Override
        public String getDescription() {
            return null;
        }

        /**
         * 显示详情
         *
         * @param context 上下文
         */
        @Override
        public void showDetail(Context context) {
            Intent intent = new Intent(context, GroupProfileActivity.class);
            intent.putExtra("identify", identify);
            context.startActivity(intent);
        }
    }
}
