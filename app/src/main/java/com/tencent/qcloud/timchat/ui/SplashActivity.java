package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.tencent.TIMCallBack;
import com.tencent.openqq.protocol.imsdk.im_open_common;
import com.tencent.qcloud.presentation.business.LoginBusiness;
import com.tencent.qcloud.presentation.presenter.SplashPresenter;
import com.tencent.qcloud.presentation.viewfeatures.SplashView;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.model.FriendshipInfo;
import com.tencent.qcloud.timchat.model.GroupInfo;
import com.tencent.qcloud.timchat.model.UserInfo;
import com.tencent.qcloud.tlslibrary.activity.HostLoginActivity;
import com.tencent.qcloud.tlslibrary.service.TLSService;

public class SplashActivity extends Activity implements SplashView,TIMCallBack{

    SplashPresenter presenter;
    private int LOGIN_RESULT_CODE = 100;
    private static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        presenter = new SplashPresenter(this);
        presenter.start();
    }


    /**
     * 跳转到主界面
     */
    @Override
    public void navToHome() {
        LoginBusiness.LoginIm(UserInfo.getInstance().getId(), UserInfo.getInstance().getUserSig(), this);
    }

    /**
     * 跳转到登录界面
     */
    @Override
    public void navToLogin() {
        Intent intent = new Intent(this, HostLoginActivity.class);
        startActivityForResult(intent, LOGIN_RESULT_CODE);
    }

    /**
     * 是否已有用户登录
     */
    @Override
    public boolean isUserLogin() {
        return UserInfo.getInstance().getId()!= null && (!TLSService.getInstance().needLogin(UserInfo.getInstance().getId()));
    }

    /**
     * imsdk登录失败后回调
     */
    @Override
    public void onError(int i, String s) {

    }

    /**
     * imsdk登录成功后回调
     */
    @Override
    public void onSuccess() {
        GroupInfo.getInstance().init();
        FriendshipInfo.getInstance().init();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (LOGIN_RESULT_CODE == requestCode) {
            String id = TLSService.getInstance().getLastUserIdentifier();
            UserInfo.getInstance().setId(id);
            UserInfo.getInstance().setUserSig(TLSService.getInstance().getUserSig(id));
            navToHome();
        }
    }

}
