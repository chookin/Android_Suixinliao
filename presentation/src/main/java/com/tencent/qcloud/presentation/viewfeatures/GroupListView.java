package com.tencent.qcloud.presentation.viewfeatures;

import com.tencent.TIMGroupBaseInfo;

import java.util.List;

/**
 * Created by admin on 16/3/17.
 */
public interface GroupListView extends MvpView {

    void showMyGroupList(List<TIMGroupBaseInfo> timGroupBaseInfos);

}
