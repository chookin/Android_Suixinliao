package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tencent.TIMFriendGroup;
import com.tencent.TIMFriendResult;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.tencent.qcloud.presentation.presenter.GetFriendGroupsPresenter;
import com.tencent.qcloud.presentation.viewfeatures.MyFriendGroupInfo;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.CategoryAdapter;
import com.tencent.qcloud.timchat.model.CategoryItem;

import java.util.LinkedList;
import java.util.List;

public class ChangeCategoryActivity extends Activity implements MyFriendGroupInfo {


    private GetFriendGroupsPresenter mGetFriendGroupsPresenter;
    private List<CategoryItem> categories = new LinkedList<>();
    private CategoryAdapter adapter;
    private ListView listView;
    private String identify, currentCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_category);
        identify = getIntent().getStringExtra("identify");
        currentCategory = getIntent().getStringExtra("category");
        listView = (ListView) findViewById(R.id.list);
        adapter = new CategoryAdapter(this, R.layout.item_category, categories);
        listView.setAdapter(adapter);
        mGetFriendGroupsPresenter = new GetFriendGroupsPresenter(this);
        mGetFriendGroupsPresenter.getFriendGroupList();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {
                mGetFriendGroupsPresenter.addFriendsToFriendGroup(categories.get(position).getName(), identify, new TIMValueCallBack<List<TIMFriendResult>>() {
                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onSuccess(List<TIMFriendResult> timFriendResults) {
                        Intent intent = new Intent();
                        intent.putExtra("category", categories.get(position).getName());
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
            }
        });
    }

    @Override
    public void showMyGroupList(List<TIMFriendGroup> timFriendGroups) {
        for (TIMFriendGroup item : timFriendGroups){
            CategoryItem categoryItem = new CategoryItem();
            categoryItem.setName(item.getGroupName());
            categoryItem.setIsSelected(currentCategory.equals(item.getGroupName()));
            categories.add(categoryItem);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showGroupMember(String groupname, List<TIMUserProfile> timUserProfiles) {

    }
}
