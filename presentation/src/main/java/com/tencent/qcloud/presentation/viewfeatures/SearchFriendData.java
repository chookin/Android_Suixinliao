package com.tencent.qcloud.presentation.viewfeatures;

import com.tencent.TIMUserProfile;

import java.util.ArrayList;

/**
 * Created by admin on 16/3/4.
 */
public interface SearchFriendData extends MvpView {

    void showSearchReuslt(TIMUserProfile userProfile);

    void showSearchReusltList(ArrayList<TIMUserProfile> mSearchResult);
}
