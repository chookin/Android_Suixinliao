package com.tencent.qcloud.presentation.presenter;

import com.tencent.TIMCallBack;
import com.tencent.TIMFriendAllowType;
import com.tencent.TIMFriendshipManager;

/**
 * Created by admin on 16/3/9.
 */
public class SettingsPresenter extends Presenter {

    public void setDefaultAllowType(){
        TIMFriendshipManager.getInstance().setAllowType(TIMFriendAllowType.TIM_FRIEND_NEED_CONFIRM, new TIMCallBack() {

            @Override
            public void onError(int arg0, String arg1) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onSuccess() {
            }

        });
    }
}
