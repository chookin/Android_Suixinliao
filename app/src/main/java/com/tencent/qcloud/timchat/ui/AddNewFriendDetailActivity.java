package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tencent.qcloud.presentation.presenter.InvitePresenter;
import com.tencent.qcloud.timchat.R;

/**
 * 查找添加新朋友
 */
public class AddNewFriendDetailActivity extends Activity implements View.OnClickListener {
    private TextView mNameText, mIDText, mInviteButton;
    private InvitePresenter mInvitePresenter;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew_detail);
        mNameText = (TextView) findViewById(R.id.nametext);
        mIDText = (TextView) findViewById(R.id.detail_id);
        mInviteButton = (TextView) findViewById(R.id.btn_invite_addnew);
        id = getIntent().getStringExtra("Id");
        String name = getIntent().getStringExtra("name");
        mNameText.setText(name);
        mIDText.setText(id);
        mInviteButton.setOnClickListener(this);
        mInvitePresenter = new InvitePresenter(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_invite_addnew) {
            mInvitePresenter.onInviteFriend(id,"","","qq");
        }
    }
}
