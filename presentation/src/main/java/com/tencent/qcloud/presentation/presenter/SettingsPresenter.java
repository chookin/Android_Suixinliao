package com.tencent.qcloud.presentation.presenter;

import android.util.Log;

import com.tencent.TIMCallBack;
import com.tencent.TIMFriendAllowType;
import com.tencent.TIMFriendshipManager;

/**
 * 设置界面Presenter dadfadafdfafdadfa
 */
public class SettingsPresenter extends Presenter {
    private static final String TAG = SettingsPresenter.class.getSimpleName();

    public void setDefaultAllowType(){
        TIMFriendshipManager.getInstance().setAllowType(TIMFriendAllowType.TIM_FRIEND_NEED_CONFIRM, new TIMCallBack() {

            @Override
            public void onError(int arg0, String arg1) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onSuccess() {
                Log.i(TAG, "onSuccess ");

            }

        });
    }
}
