package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.TIMCallBack;
import com.tencent.TIMGroupManager;
import com.tencent.TIMGroupMemberResult;
import com.tencent.TIMGroupMemberRoleType;
import com.tencent.TIMValueCallBack;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.model.GroupInfo;
import com.tencent.qcloud.timchat.ui.customview.LineControllerView;
import com.tencent.qcloud.timchat.ui.customview.ListPickerDialog;

import java.util.Collections;
import java.util.List;

public class GroupMemberProfileActivity extends FragmentActivity {

    private String userIdentify, groupIdentify, userCard;
    private TIMGroupMemberRoleType type;
    private TIMGroupMemberRoleType currentUserRole;
    private long quietTime;
    private String[] quietingOpt;
    private String[] quietOpt;
    private long[] quietTimeOpt = new long[] {600, 3600, 24*3600};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_member_profile);
        userIdentify = getIntent().getStringExtra("id");
        groupIdentify = getIntent().getStringExtra("groupId");
        userCard = getIntent().getStringExtra("name");
        type = (TIMGroupMemberRoleType) getIntent().getSerializableExtra("role");
        quietTime = getIntent().getLongExtra("quietTime", 0);
        currentUserRole = GroupInfo.getInstance().getRole(groupIdentify);
        quietingOpt = new String[] {getString(R.string.group_member_quiet_cancel)};
        quietOpt = new String[] {getString(R.string.group_member_quiet_ten_min),
                getString(R.string.group_member_quiet_one_hour),
                getString(R.string.group_member_quiet_one_day),
        };
        TextView tvName = (TextView) findViewById(R.id.name);
        tvName.setText(userIdentify);
        TextView tvKick = (TextView) findViewById(R.id.kick);
        tvKick.setVisibility(canManage()? View.VISIBLE:View.GONE);
        tvKick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TIMGroupManager.getInstance().deleteGroupMember(groupIdentify, Collections.singletonList(userIdentify), new TIMValueCallBack<List<TIMGroupMemberResult>>() {
                    @Override
                    public void onError(int i, String s) {
                        Toast.makeText(GroupMemberProfileActivity.this, getString(R.string.group_member_del_err), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(List<TIMGroupMemberResult> timGroupMemberResults) {
                        Toast.makeText(GroupMemberProfileActivity.this, getString(R.string.group_member_del_succ), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
        final LineControllerView setManager = (LineControllerView) findViewById(R.id.manager);
        setManager.setVisibility(currentUserRole == TIMGroupMemberRoleType.Owner && currentUserRole != type ? View.VISIBLE : View.GONE);
        setManager.setSwitch(type == TIMGroupMemberRoleType.Admin);
        setManager.setCheckListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                TIMGroupManager.getInstance().modifyGroupMemberInfoSetRole(groupIdentify, userIdentify,
                        isChecked ? TIMGroupMemberRoleType.Admin : TIMGroupMemberRoleType.Normal,
                        new TIMCallBack() {
                            @Override
                            public void onError(int i, String s) {
                                Toast.makeText(GroupMemberProfileActivity.this, getString(R.string.group_member_manage_set_err), Toast.LENGTH_SHORT).show();
                                setManager.setSwitch(!isChecked);
                            }

                            @Override
                            public void onSuccess() {
                                Toast.makeText(GroupMemberProfileActivity.this, getString(R.string.group_member_manage_set_succ), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        LineControllerView nameCard = (LineControllerView) findViewById(R.id.groupCard);
        nameCard.setContent(userCard);
        final LineControllerView setQuiet = (LineControllerView) findViewById(R.id.setQuiet);
        setQuiet.setVisibility(canManage()?View.VISIBLE:View.GONE);
        if (canManage()){
            if (quietTime != 0){
                setQuiet.setContent(getString(R.string.group_member_quiet_ing));
            }
            setQuiet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ListPickerDialog().show(getQuietOption(), getSupportFragmentManager(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, final int which) {
                            TIMGroupManager.getInstance().modifyGroupMemberInfoSetSilence(groupIdentify, userIdentify, getQuietTime(which),
                                    new TIMCallBack() {
                                        @Override
                                        public void onError(int i, String s) {
                                            Toast.makeText(GroupMemberProfileActivity.this, getString(R.string.group_member_quiet_err), Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onSuccess() {
                                            if ((quietTime = getQuietTime(which)) == 0){
                                                setQuiet.setContent("");
                                            }else{
                                                setQuiet.setContent(getString(R.string.group_member_quiet_ing));
                                            }
                                        }
                                    });
                        }
                    });
                }
            });
        }




    }

    private boolean canManage(){
        if ((currentUserRole == TIMGroupMemberRoleType.Owner && type != TIMGroupMemberRoleType.Owner) ||
                (currentUserRole == TIMGroupMemberRoleType.Admin && type == TIMGroupMemberRoleType.Normal)) return true;
        return false;
    }

    private String[] getQuietOption(){
        if (quietTime == 0){
            return quietOpt;
        }else{
            return quietingOpt;
        }
    }

    private long getQuietTime(int which){
        if (quietTime == 0){
            return quietTimeOpt[which];
        }
        return 0;
    }
}
