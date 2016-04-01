package com.tencent.qcloud.presentation.event;


import com.tencent.TIMCallBack;
import com.tencent.TIMGroupAssistantListener;
import com.tencent.TIMGroupDetailInfo;
import com.tencent.TIMGroupManager;
import com.tencent.TIMGroupMemberInfo;
import com.tencent.TIMGroupSettings;

import java.util.List;
import java.util.Observable;

/**
 * 群相关数据缓存，底层IMSDK会维护本地存储
 */
public class GroupEvent extends Observable implements TIMGroupAssistantListener {

    private final String TAG = "GroupInfo";

    private boolean isInit;

    private GroupEvent(){
    }

    private static GroupEvent instance = new GroupEvent();

    public static GroupEvent getInstance(){
        return instance;
    }

    public void init(){
        //开启IMSDK本地存储
        TIMGroupSettings settings = new TIMGroupSettings();
        settings.setIsStorageEnabled(true);
        settings.setGroupInfoOptions(settings.new Options());
        settings.setMemberInfoOptions(settings.new Options());
        TIMGroupManager.getInstance().getGroupAssistant().initGroupSettings(settings, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess() {
                isInit = true;
            }
        });
        //设置群资料变更监听
        TIMGroupManager.getInstance().getGroupAssistant().setListener(this);

    }

    public boolean isInit() {
        return isInit;
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
        setChanged();
        notifyObservers(timGroupDetailInfo);
    }

    @Override
    public void onGroupDelete(String s) {
        setChanged();
        notifyObservers(s);
    }

    @Override
    public void onGroupUpdate(TIMGroupDetailInfo timGroupDetailInfo) {
        setChanged();
        notifyObservers(timGroupDetailInfo);
    }


}
