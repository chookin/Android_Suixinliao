package com.tencent.qcloud.presentation.presenter;

import com.tencent.qcloud.presentation.viewfeatures.JoinGroupsInfo;

/**
 * 获取自己分组的数据，
 */
public class GetMyGroupsPresenter extends Presenter {


    JoinGroupsInfo view;
    public GetMyGroupsPresenter(JoinGroupsInfo view) {
        this.view = view;
    }

}
