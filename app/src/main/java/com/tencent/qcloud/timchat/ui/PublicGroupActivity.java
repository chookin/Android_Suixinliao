package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.tencent.TIMGroupBaseInfo;
import com.tencent.qcloud.presentation.presenter.ManagerGroupListPresenter;
import com.tencent.qcloud.presentation.viewfeatures.PulicGroupListView;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.ui.customview.TemplateTitle;
import java.util.List;


public class PublicGroupActivity extends Activity implements PulicGroupListView {
    ManagerGroupListPresenter mManagerGroupListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulicgroup);
        TemplateTitle title = (TemplateTitle) findViewById(R.id.public_group_actionbar);
        title.setMoreTextAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublicGroupActivity.this, CreateGroupActivity.class);
                startActivity(intent);
            }
        });



        mManagerGroupListPresenter = new ManagerGroupListPresenter(this, this);
        mManagerGroupListPresenter.getMyPulicGroupList();

    }

    @Override
    public void showMyPublicGroupListByType(List<TIMGroupBaseInfo> createGroup, List<TIMGroupBaseInfo> hostGroup, List<TIMGroupBaseInfo> memberGroup) {

    }

}
