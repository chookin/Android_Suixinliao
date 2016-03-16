package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.tencent.TIMUserProfile;
import com.tencent.qcloud.presentation.presenter.ProfilePresenter;
import com.tencent.qcloud.presentation.viewfeatures.ProfileView;
import com.tencent.qcloud.timchat.R;

public class ProfileActivity extends Activity implements ProfileView {

    private ProfilePresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final String identify = getIntent().getStringExtra("identify");
        presenter = new ProfilePresenter(this,identify);
        presenter.start();
    }

    /**
     * 显示用户信息
     *
     * @param profile
     */
    @Override
    public void showProfile(TIMUserProfile profile) {
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(profile.getNickName());
        TextView id = (TextView) findViewById(R.id.id);
        id.setText(profile.getIdentifier());
        TextView remark = (TextView) findViewById(R.id.remark);
        remark.setText(profile.getRemark());
    }
}
