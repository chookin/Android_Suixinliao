package com.tencent.qcloud.presentation.presenter;

import android.os.Handler;

import com.tencent.qcloud.presentation.view.SplashView;
import com.tencent.qcloud.tlslibrary.service.TLSService;

import model.UserInfo;


/**
 * 闪屏界面逻辑
 */
public class SplashPresenter extends Presenter {
    SplashView view;
    public SplashPresenter(SplashView view){
        this.view = view;
    }


    /**
     * 加载页面逻辑
     */
    @Override
    public void start() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(UserInfo.getInstance().getId()!= null && (!TLSService.getInstance().needLogin(UserInfo.getInstance().getId())) ){
                    view.navToHome();
                }else{
                    view.navToLogin();
                }
            }
        }, 3000);
    }

}
