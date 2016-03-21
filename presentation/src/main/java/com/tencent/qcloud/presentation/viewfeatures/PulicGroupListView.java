package com.tencent.qcloud.presentation.viewfeatures;

import com.tencent.TIMGroupBaseInfo;

import java.util.List;

/**
 * Created by admin on 16/3/17.
 */
public interface PulicGroupListView extends MvpView {

    void showMyPublicGroupListByType(List<TIMGroupBaseInfo> createGroup, List<TIMGroupBaseInfo> hostGroup, List<TIMGroupBaseInfo> memberGroup);
}
