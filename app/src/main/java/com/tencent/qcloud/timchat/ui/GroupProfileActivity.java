package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.TIMConversationType;
import com.tencent.TIMGroupDetailInfo;
import com.tencent.qcloud.presentation.presenter.GroupInfoPresenter;
import com.tencent.qcloud.presentation.viewfeatures.GroupInfoView;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.presentation.event.GroupEvent;
import com.tencent.qcloud.timchat.model.GroupInfo;
import com.tencent.qcloud.timchat.ui.customview.LineControllerView;

import java.util.Collections;
import java.util.List;

public class GroupProfileActivity extends Activity implements GroupInfoView, View.OnClickListener {

    private final String TAG = "GroupProfileActivity";

    private String identify;
    private GroupInfoPresenter groupInfoPresenter;
    private boolean isInGroup;


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
                break;
            case R.id.controlOutGroup:
                Intent intent = new Intent(this, ApplyGroupActivity.class);
                intent.putExtra("identify", identify);
                startActivity(intent);
                break;
        }
    }
}
