package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.TIMCallBack;
import com.tencent.TIMConversationType;
import com.tencent.TIMGroupDetailInfo;
import com.tencent.qcloud.presentation.presenter.GroupInfoPresenter;
import com.tencent.qcloud.presentation.presenter.GroupManagerPresenter;
import com.tencent.qcloud.presentation.viewfeatures.GroupInfoView;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.presentation.event.GroupEvent;
import com.tencent.qcloud.timchat.model.GroupInfo;
import com.tencent.qcloud.timchat.model.UserInfo;
import com.tencent.qcloud.timchat.ui.customview.LineControllerView;

import java.util.Collections;
import java.util.List;

public class GroupProfileActivity extends Activity implements GroupInfoView, View.OnClickListener {

    private final String TAG = "GroupProfileActivity";

    private String identify;
    private GroupInfoPresenter groupInfoPresenter;
    private boolean isInGroup;
    private boolean isGroupOwner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat_setting);
        identify = getIntent().getStringExtra("identify");
        isInGroup = GroupInfo.getInstance().isInGroup(identify);
        groupInfoPresenter = new GroupInfoPresenter(this, Collections.singletonList(identify), isInGroup);
        groupInfoPresenter.getGroupDetailInfo();
        LinearLayout controlInGroup = (LinearLayout) findViewById(R.id.controlInGroup);
        controlInGroup.setVisibility(isInGroup? View.VISIBLE:View.GONE);
        TextView controlOutGroup = (TextView) findViewById(R.id.controlOutGroup);
        controlOutGroup.setVisibility(isInGroup? View.GONE:View.VISIBLE);
    }

    /**
     * 显示群资料
     *
     * @param groupInfos 群资料信息列表
     */
    @Override
    public void showGroupInfo(List<TIMGroupDetailInfo> groupInfos) {
        TIMGroupDetailInfo info = groupInfos.get(0);
        LineControllerView member = (LineControllerView) findViewById(R.id.member);
        member.setContent(String.valueOf(info.getMemberNum()));
        LineControllerView name = (LineControllerView) findViewById(R.id.nameText);
        name.setContent(info.getGroupName());
        LineControllerView id = (LineControllerView) findViewById(R.id.idText);
        id.setContent(info.getGroupId());
        LineControllerView intro = (LineControllerView) findViewById(R.id.groupIntro);
        intro.setContent(info.getGroupIntroduction());
        LineControllerView opt = (LineControllerView) findViewById(R.id.addOpt);
        switch (info.getGroupAddOpt()){
            case TIM_GROUP_ADD_AUTH:
                opt.setContent(getResources().getString(R.string.chat_setting_group_auth));
                break;
            case TIM_GROUP_ADD_ANY:
                opt.setContent(getResources().getString(R.string.chat_setting_group_all_accept));
                break;
            case TIM_GROUP_ADD_FORBID:
                opt.setContent(getResources().getString(R.string.chat_setting_group_all_reject));
                break;
        }
        TextView btnDel = (TextView) findViewById(R.id.btnDel);
        isGroupOwner = info.getGroupOwner().equals(UserInfo.getInstance().getId());
        btnDel.setText(isGroupOwner?getString(R.string.chat_setting_dismiss):getString(R.string.chat_setting_quit));

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnChat:
                ChatActivity.navToChat(this,identify, TIMConversationType.Group);
                break;
            case R.id.btnDel:
                if (isGroupOwner){
                    GroupManagerPresenter.dismissGroup(identify, new TIMCallBack() {
                        @Override
                        public void onError(int i, String s) {
                            Log.i(TAG, "onError code" + i + " msg " + s);
                        }

                        @Override
                        public void onSuccess() {
                            Toast.makeText(GroupProfileActivity.this, getString(R.string.chat_setting_dismiss_succ),Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }else{
                    GroupManagerPresenter.quitGroup(identify, new TIMCallBack() {
                        @Override
                        public void onError(int i, String s) {
                            Log.i(TAG, "onError code" + i + " msg " + s);
                        }

                        @Override
                        public void onSuccess() {
                            Toast.makeText(GroupProfileActivity.this, getString(R.string.chat_setting_quit_succ),Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
                break;
            case R.id.controlOutGroup:
                Intent intent = new Intent(this, ApplyGroupActivity.class);
                intent.putExtra("identify", identify);
                startActivity(intent);
                break;
        }
    }
}
