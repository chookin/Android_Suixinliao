package com.tencent.qcloud.presentation.viewfeatures;

import com.tencent.TIMGroupDetailInfo;

import java.util.List;

/**
 * 群资料界面接口
 */
public interface GroupInfoView {


    /**
     * 显示群资料
     *
     * @param groupInfos 群资料信息列表
     */
    public void showGroupInfo(List<TIMGroupDetailInfo> groupInfos);
}
