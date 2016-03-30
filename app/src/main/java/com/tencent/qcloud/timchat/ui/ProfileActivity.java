package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.TIMConversationType;
import com.tencent.TIMFriendGroup;
import com.tencent.TIMFriendStatus;
import com.tencent.TIMUserProfile;
import com.tencent.qcloud.presentation.presenter.FriendshipManagerPresenter;
import com.tencent.qcloud.presentation.presenter.GetFriendGroupsPresenter;
import com.tencent.qcloud.presentation.presenter.ProfilePresenter;
import com.tencent.qcloud.presentation.viewfeatures.FriendshipManageView;
import com.tencent.qcloud.presentation.viewfeatures.MyFriendGroupInfo;
import com.tencent.qcloud.presentation.viewfeatures.ProfileView;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.ui.customview.LineControllerView;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends Activity implements ProfileView, FriendshipManageView, MyFriendGroupInfo, View.OnClickListener {


    private static final String TAG = ProfileActivity.class.getSimpleName();

    private final int CHANGE_CATEGORY_CODE = 100;

    private ProfilePresenter presenter;
    private FriendshipManagerPresenter friendshipManagerPresenter;
    private GetFriendGroupsPresenter mGetFriendGroupsPresenter;
    private ArrayList<String> groups = new ArrayList<String>();
    private Spinner mGroupList;
    ArrayAdapter<String> mAdapter;
    private String identify, categoryStr;


    public static void navToProfile(Context context, String identify){
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra("identify", identify);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        mGroupList = (Spinner)findViewById(R.id.set_group);
        identify = getIntent().getStringExtra("identify");
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, groups);
//        mGroupList.setAdapter(mAdapter);
        presenter = new ProfilePresenter(this, identify);
        presenter.getProfile();
        friendshipManagerPresenter = new FriendshipManagerPresenter(this);



    }

    /**
     * 显示用户信息
     *
     * @param profile
     */
    @Override
    public void showProfile(TIMUserProfile profile) {
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(getName(profile));
        LineControllerView id = (LineControllerView) findViewById(R.id.id);
        id.setContent(profile.getIdentifier());
        LineControllerView remark = (LineControllerView) findViewById(R.id.remark);
        remark.setContent(profile.getRemark());
        LineControllerView category = (LineControllerView) findViewById(R.id.group);
        //一个用户可以在多个分组内，客户端逻辑保证一个人只存在于一个分组
        category.setContent(categoryStr = profile.getFriendGroups().size() == 0?"":profile.getFriendGroups().get(0));

    }

    @Override
    public void showMyGroupList(List<TIMFriendGroup> timFriendGroups) {
        groups.clear();
        groups.add(getResources().getString(R.string.profile_group));
        for(TIMFriendGroup groupItem : timFriendGroups){
            groups.add(groupItem.getGroupName());
        }
        mAdapter.notifyDataSetChanged();
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
                Intent intent = new Intent(this, ChatActivity.class);
                intent.putExtra("identify", identify);
                intent.putExtra("type", TIMConversationType.C2C);
                startActivity(intent);
                finish();
                break;
            case R.id.btnDel:
                friendshipManagerPresenter.delFriend(identify);
                break;
            case R.id.group:
                Intent changeCategoryIntent = new Intent(this, ChangeCategoryActivity.class);
                changeCategoryIntent.putExtra("identify", identify);
                changeCategoryIntent.putExtra("category", categoryStr);
                startActivityForResult(changeCategoryIntent, CHANGE_CATEGORY_CODE);
                break;
        }
    }

    public void showGroupMember(String groupname,List<TIMUserProfile> timUserProfiles) {}


    /**
     * 获取名称
     *
     * @param profile 返回的用户资料数据
     */
    private String getName(TIMUserProfile profile){
        if (!profile.getRemark().equals("")) return profile.getRemark();
        if (!profile.getNickName().equals("")) return profile.getNickName();
        return profile.getIdentifier();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHANGE_CATEGORY_CODE) {
            if (resultCode == RESULT_OK) {
                LineControllerView category = (LineControllerView) findViewById(R.id.group);
                category.setContent(categoryStr = data.getStringExtra("category"));
            }
        }

    }

    /**
     * 添加好友结果回调
     *
     * @param status 返回状态
     */
    @Override
    public void onAddFriend(TIMFriendStatus status) {

    }

    /**
     * 删除好友结果回调
     *
     * @param status 返回状态
     */
    @Override
    public void onDelFriend(TIMFriendStatus status) {
        switch (status){
            case TIM_FRIEND_STATUS_SUCC:
                Toast.makeText(this, getResources().getString(R.string.profile_del_succeed), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case TIM_FRIEND_STATUS_UNKNOWN:
                Toast.makeText(this, getResources().getString(R.string.profile_del_fail), Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
