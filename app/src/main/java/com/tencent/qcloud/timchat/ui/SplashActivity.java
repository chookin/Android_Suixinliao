package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.tencent.qcloud.presentation.presenter.SplashPresenter;
import com.tencent.qcloud.presentation.view.SplashView;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.tlslibrary.activity.HostLoginActivity;
import com.tencent.qcloud.tlslibrary.service.Constants;

public class SplashActivity extends Activity implements SplashView {

    SplashPresenter presenter;

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
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
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
}
