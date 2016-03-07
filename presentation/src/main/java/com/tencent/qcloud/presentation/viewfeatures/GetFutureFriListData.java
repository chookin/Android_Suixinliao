package com.tencent.qcloud.presentation.viewfeatures;

import com.tencent.TIMFriendFutureItem;

import java.util.List;

/**
 *
 */
public interface GetFutureFriListData extends MvpView{

    void showFutureFriListData(List<TIMFriendFutureItem> mFutureFriList);
}
