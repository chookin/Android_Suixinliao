package com.tencent.qcloud.presentation.viewfeatures;

import com.tencent.TIMGroupBaseInfo;

import java.util.List;

/**
 * Created by admin on 16/3/8.
 */
public interface ManagerGroupView extends MvpView{

    void showMyGroupList(List<TIMGroupBaseInfo> timGroupBaseInfos);

    void notifyGroupListChange();
}
