package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.os.Bundle;

import com.tencent.TIMGroupDetailInfo;
import com.tencent.qcloud.presentation.presenter.GroupInfoPresenter;
import com.tencent.qcloud.presentation.viewfeatures.GroupInfoView;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.ui.customview.LineControllerView;

import java.util.ArrayList;
import java.util.List;

public class GroupChatSettingActivity extends Activity implements GroupInfoView {

    private String identify;
    private GroupInfoPresenter groupInfoPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat_setting);
        identify = getIntent().getStringExtra("identify");
        groupInfoPresenter = new GroupInfoPresenter(this, new ArrayList<String>() {
            {add(identify);}}, true);
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
    }
}
