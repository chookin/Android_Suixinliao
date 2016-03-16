package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tencent.qcloud.presentation.presenter.ResponseFriInvitePresenter;
import com.tencent.qcloud.presentation.viewfeatures.AgreeNewFriend;
import com.tencent.qcloud.timchat.R;

/**
 * 新朋友信息
 */
public class NewFriendDetailActivity extends Activity implements View.OnClickListener ,AgreeNewFriend{

    private TextView mIdView, mBtnAgree, mBtnRefuse;
    private ResponseFriInvitePresenter mResponseFriInvitePresenter;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfri_detail);
        mIdView = (TextView) findViewById(R.id.newfri_info_id);
        mBtnAgree = (TextView) findViewById(R.id.agree_btn);
        mBtnRefuse = (TextView) findViewById(R.id.refuse_btn);
        mBtnAgree.setOnClickListener(this);
        mBtnRefuse.setOnClickListener(this);
        mResponseFriInvitePresenter = new ResponseFriInvitePresenter(this,this);
        id = getIntent().getStringExtra("id");
        mIdView.setText(id);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.agree_btn) {
            mResponseFriInvitePresenter.answerFriInvite(id, true);
            Intent intent = new Intent(NewFriendDetailActivity.this, ProfileActivity.class);
            intent.putExtra("identify", id);
            startActivity(intent);
        }
        if (view.getId() == R.id.refuse_btn) {
            mResponseFriInvitePresenter.answerFriInvite(id, false);

        }
    }

    @Override
    public void jumpIntoProfileActicity() {

    }
}
