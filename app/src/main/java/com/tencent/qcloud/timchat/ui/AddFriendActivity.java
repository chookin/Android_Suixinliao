package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.TIMFriendStatus;
import com.tencent.qcloud.presentation.presenter.FriendshipManagerPresenter;
import com.tencent.qcloud.presentation.viewfeatures.FriendshipManageView;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.ui.customview.LineControllerView;

/**
 * 申请添加好友界面
 */
public class AddFriendActivity extends Activity implements View.OnClickListener, FriendshipManageView {


    private TextView tvName, btnAdd;
    private EditText editRemark, editMessage;
    private LineControllerView idField;
    private FriendshipManagerPresenter presenter;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        tvName = (TextView) findViewById(R.id.name);
        idField = (LineControllerView) findViewById(R.id.id);
        id = getIntent().getStringExtra("id");
        tvName.setText(getIntent().getStringExtra("name"));
        idField.setContent(id);
        btnAdd = (TextView) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        editMessage = (EditText) findViewById(R.id.editMessage);
        editRemark = (EditText) findViewById(R.id.editNickname);
        presenter = new FriendshipManagerPresenter(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnAdd) {
            presenter.addFriend(id, editRemark.getText().toString(), editMessage.getText().toString());
        }
    }

    /**
     * 添加好友结果回调
     *
     * @param status 返回状态
     */
    @Override
    public void onAddFriend(TIMFriendStatus status) {
        switch (status){
            case TIM_ADD_FRIEND_STATUS_PENDING:
                Toast.makeText(this, getResources().getString(R.string.add_friend_succeed), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case TIM_FRIEND_STATUS_SUCC:
                Toast.makeText(this, getResources().getString(R.string.add_friend_added), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case TIM_ADD_FRIEND_STATUS_FRIEND_SIDE_FORBID_ADD:
                Toast.makeText(this, getResources().getString(R.string.add_friend_refuse_all), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case TIM_ADD_FRIEND_STATUS_IN_OTHER_SIDE_BLACK_LIST:
                Toast.makeText(this, getResources().getString(R.string.add_friend_to_blacklist), Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

    }

    /**
     * 删除好友结果回调
     *
     * @param status 返回状态
     */
    @Override
    public void onDelFriend(TIMFriendStatus status) {

    }

}
