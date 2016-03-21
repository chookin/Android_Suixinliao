package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tencent.qcloud.timchat.R;

/**
 * Created by admin on 16/3/17.
 */
public class CreateGroupActivity extends Activity{
    TextView mAddMembers;
    EditText mInputView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategroup);
        mInputView = (EditText)findViewById(R.id.input_group_name);
        mAddMembers = (TextView)findViewById(R.id.btn_add_group_member);
        mAddMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateGroupActivity.this, ChooseMembersActivity.class);
                String groupname =mInputView.getText().toString();
                intent.putExtra("groupname",groupname);
                startActivity(intent);
            }
        });
    }
}
