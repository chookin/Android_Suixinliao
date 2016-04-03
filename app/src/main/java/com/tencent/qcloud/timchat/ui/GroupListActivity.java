package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.ProfileSummaryAdapter;
import com.tencent.qcloud.timchat.model.GroupInfo;
import com.tencent.qcloud.timchat.model.ProfileSummary;
import com.tencent.qcloud.timchat.ui.customview.TemplateTitle;

import java.util.List;

public class GroupListActivity extends Activity {

    private ProfileSummaryAdapter adapter;
    private ListView listView;
    private String type;
    private List<ProfileSummary> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);
        type = getIntent().getStringExtra("type");
        setTitle(type);
        listView =(ListView) findViewById(R.id.list);
        list = (List<ProfileSummary>) GroupInfo.getInstance().getGroupListByType(type);
        adapter = new ProfileSummaryAdapter(this, R.layout.item_profile_summary, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                list.get(position).onClick(GroupListActivity.this);
            }
        });

    }

    private void setTitle(String type){
        TemplateTitle title = (TemplateTitle) findViewById(R.id.groupListTitle);
        if (type.equals(GroupInfo.publicGroup)){
            title.setTitleText(getString(R.string.public_group));
        }else if (type.equals(GroupInfo.privateGroup)){
            title.setTitleText(getString(R.string.discuss_group));
        }else if (type.equals(GroupInfo.chatRoom)){
            title.setTitleText(getString(R.string.chatroom));
        }
    }
}
