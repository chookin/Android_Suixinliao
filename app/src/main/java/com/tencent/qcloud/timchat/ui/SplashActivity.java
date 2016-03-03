package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.tencent.TIMCallBack;
import com.tencent.qcloud.presentation.business.LoginBusiness;
import com.tencent.qcloud.presentation.presenter.SplashPresenter;
import com.tencent.qcloud.presentation.viewfeatures.SplashView;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.model.UserInfo;
import com.tencent.qcloud.tlslibrary.activity.HostLoginActivity;
import com.tencent.qcloud.tlslibrary.service.Constants;
import com.tencent.qcloud.tlslibrary.service.TLSService;

public class SplashActivity extends Activity implements SplashView,TIMCallBack{

    SplashPresenter presenter;
    private int LOGIN_RESULT_CODE = 0;


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
        Intent intent = new Intent(SplashActivity.this, HostLoginActivity.class);
        // 传入应用的包名
        intent.putExtra(Constants.EXTRA_THIRDAPP_PACKAGE_NAME_SUCC, "com.tencent.qcloud.timchat");
        // 传入跳转的Activity的完整类名
        intent.putExtra(Constants.EXTRA_THIRDAPP_CLASS_NAME_SUCC, "com.tencent.qcloud.timchat.ui.HomeActivity");
        startActivityForResult(intent, 0);
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
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (LOGIN_RESULT_CODE == resultCode) {
            navToHome();
        }
    }
}
