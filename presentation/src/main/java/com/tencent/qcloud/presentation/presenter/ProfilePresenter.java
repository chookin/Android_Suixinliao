package com.tencent.qcloud.presentation.presenter;

import com.tencent.TIMCallBack;
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
     * 获取自己资料时，identify可以为空
     */
    public ProfilePresenter(ProfileView view){
        this.view = view;
    }


    /**
     * 获取用户资料
     */
    public void getProfile() {
        if (identify == null){
            TIMFriendshipManager.getInstance().getSelfProfile(new TIMValueCallBack<TIMUserProfile>() {
                @Override
                public void onError(int i, String s) {

                }

                @Override
                public void onSuccess(TIMUserProfile timUserProfile) {
                    view.showProfile(timUserProfile);
                }
            });
        }else{
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

    /**
     * 修改自己的昵称
     */
    public void changeNickName(String newName){
        //设置新昵称为cat
        TIMFriendshipManager.getInstance().setNickName(newName, new TIMCallBack(){
            @Override
            public void onError(int code, String desc){
                //错误码code和错误描述desc，可用于定位请求失败原因
                //错误码code列表请参见错误码表
            }

            @Override
            public void onSuccess(){

            }
        });
    }


}
