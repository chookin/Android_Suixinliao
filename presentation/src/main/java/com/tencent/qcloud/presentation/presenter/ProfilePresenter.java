package com.tencent.qcloud.presentation.presenter;

import com.tencent.TIMFriendshipManager;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.tencent.qcloud.presentation.viewfeatures.ProfileView;

import java.util.Collections;
import java.util.List;

/**
 * 用户资料页面逻辑
 */
public class ProfilePresenter extends Presenter {

    private ProfileView view;
    private String identify;

    public ProfilePresenter(ProfileView view, String identify){
        this.view = view;
        this.identify = identify;
    }


    /**
     * 加载页面逻辑
     */
    public void start() {
        if (identify == null) return;
        TIMFriendshipManager.getInstance().getFriendsProfile(Collections.singletonList(identify), new TIMValueCallBack<List<TIMUserProfile>>(){
            @Override
            public void onError(int code, String desc){

            }

            @Override
            public void onSuccess(List<TIMUserProfile> result){
                view.showProfile(result.get(0));
            }
        });

    }
}
