package com.tencent.qcloud.presentation.presenter;

import android.os.Handler;
import android.util.Log;

import com.tencent.TIMCallBack;
import com.tencent.TIMFriendshipManager;
import com.tencent.qcloud.presentation.viewfeatures.SplashView;


/**
 * 闪屏界面逻辑
 */
public class SplashPresenter extends Presenter {
    SplashView view;
    private static final String TAG = SplashPresenter.class.getSimpleName();

    public SplashPresenter(SplashView view) {
        this.view = view;
    }


    /**
     * 加载页面逻辑
     */
    public void start() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (view.isUserLogin()) {
                    view.navToHome();
                } else {
                    view.navToLogin();
                }
            }
        }, 1000);
    }


    /**
     * IMSDK数据同步接口
     */
    public void syncImsdk() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                syncWithFlags();
            }
        });
    }

    private void syncWithFlags() {
        TIMFriendshipManager.getInstance().getFriendshipProxy().syncWithFlags(0xff, null, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                Log.e(TAG, "init fail! code:" + i + "         " + s);
            }

            @Override
            public void onSuccess() {
//                Toast.makeText(HomeActivity.this, "sync succ!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
