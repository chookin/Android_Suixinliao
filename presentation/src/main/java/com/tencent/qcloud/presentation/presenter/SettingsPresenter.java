package com.tencent.qcloud.presentation.presenter;

import android.content.Context;
import android.util.Log;

import com.tencent.TIMCallBack;
import com.tencent.TIMFriendAllowType;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMManager;
import com.tencent.qcloud.presentation.viewfeatures.SettingsFeature;

/**
 * 设置界面Presenter
 */
public class SettingsPresenter extends Presenter {
    private static final String TAG = SettingsPresenter.class.getSimpleName();
    private SettingsFeature view;
    private Context mContext;
    public SettingsPresenter(SettingsFeature view, Context context) {
        this.view = view;
        mContext = context;
    }


    


    public void setDefaultAllowType(){
        TIMFriendshipManager.getInstance().setAllowType(TIMFriendAllowType.TIM_FRIEND_NEED_CONFIRM, new TIMCallBack() {

            @Override
            public void onError(int arg0, String arg1) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onSuccess() {
                Log.i(TAG, "onSuccess ");
                view.showMyAllowedStatus(1);
            }

        });
    }


    public void Logout(){
        TIMManager.getInstance().logout(new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                view.onLogoutResult(false);
            }

            @Override
            public void onSuccess() {
                view.onLogoutResult(true);
            }
        });
    }
}
