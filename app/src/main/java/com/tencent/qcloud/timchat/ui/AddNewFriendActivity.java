package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.tencent.TIMFriendAllowType;
import com.tencent.TIMUserProfile;
import com.tencent.qcloud.presentation.presenter.SearchFriendPresenter;
import com.tencent.qcloud.presentation.viewfeatures.SearchFriendData;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.MySimpleAdapter;
import com.tencent.qcloud.timchat.model.ItemData;
import com.tencent.qcloud.timchat.utils.LogUtils;

import java.util.ArrayList;

/**
 * 查找添加新朋友
 */
public class AddNewFriendActivity extends Activity implements SearchFriendData, View.OnClickListener, AdapterView.OnItemClickListener {
    SearchFriendPresenter mSearchFriendPresenter;
    TextView mBtnSearch;
    EditText mSearchText;
    ListView mSearchPersonList;
    MySimpleAdapter mSearchResultAdapter;
    ArrayList<ItemData> searchResult= new ArrayList<ItemData> ();
    ItemData mSelectedPerson ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew);
        mBtnSearch = (TextView) findViewById(R.id.search_icon);
        mSearchText = (EditText) findViewById(R.id.search_context);
        mSearchPersonList =(ListView) findViewById(R.id.person_searchresult);
        mSearchResultAdapter = new MySimpleAdapter(this,searchResult);
        mSearchPersonList.setAdapter(mSearchResultAdapter);
        mSearchPersonList.setOnItemClickListener(this);
        mBtnSearch.setOnClickListener(this);
        mSearchText.getText();
        mSearchFriendPresenter = new SearchFriendPresenter(this,getBaseContext());

    }

    @Override
    public void showSearchReuslt(TIMUserProfile userProfile) {
        LogUtils.i(""+userProfile);
        ItemData info = new ItemData();
        info.setID(userProfile.getIdentifier());
        info.setName(userProfile.getNickName());
        info.setNeedVerify(userProfile.getAllowType() == TIMFriendAllowType.TIM_FRIEND_NEED_CONFIRM);
        searchResult.add(info);
        mSearchResultAdapter.notifyDataSetChanged();
//        Toast.makeText(AddNewFriendActivity.this, ""+userProfile.getIdentifier(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showSearchReusltList(ArrayList<TIMUserProfile> mSearchResult) {
        for(TIMUserProfile person : mSearchResult){
            ItemData info = new ItemData();
            info.setID(person.getIdentifier());
            info.setName(person.getNickName());
            info.setNeedVerify(person.getAllowType() == TIMFriendAllowType.TIM_FRIEND_NEED_CONFIRM);
            searchResult.add(info);
        }
        mSearchResultAdapter.notifyDataSetChanged();
//        mSearchResult.add(info);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.search_icon) {
            mSearchFriendPresenter.searchByID(""+mSearchText.getText());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mSelectedPerson = searchResult.get(i);
        Intent person = new Intent(this,AddNewFriendDetailActivity.class);
        person.putExtra("Id",mSelectedPerson.getID());
        person.putExtra("name",mSelectedPerson.getName());
        person.putExtra("avatar",mSelectedPerson.getName());
        startActivity(person);
    }
}
