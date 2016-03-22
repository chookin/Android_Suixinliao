package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.tencent.TIMFriendAllowType;
import com.tencent.TIMUserProfile;
import com.tencent.qcloud.presentation.presenter.SearchFriendPresenter;
import com.tencent.qcloud.presentation.viewfeatures.SearchFriendData;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.MySimpleAdapter;
import com.tencent.qcloud.timchat.model.ItemTIMProfile;
import com.tencent.qcloud.timchat.utils.LogUtils;

import java.util.ArrayList;

/**
 * 查找添加新朋友
 */
public class AddNewFriendActivity extends Activity implements SearchFriendData, View.OnClickListener, AdapterView.OnItemClickListener, KeyEvent.Callback {

    private final static String TAG = "AddNewFriendActivity";

    SearchFriendPresenter mSearchFriendPresenter;
    ListView mSearchList;
    EditText mSearchInput;
    MySimpleAdapter mSearchResultAdapter;
    ArrayList<ItemTIMProfile> searchResult= new ArrayList<> ();
    ItemTIMProfile mSelectedPerson ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew);
        mSearchInput = (EditText) findViewById(R.id.inputSearch);
        mSearchList =(ListView) findViewById(R.id.list);
        mSearchResultAdapter = new MySimpleAdapter(this,searchResult);
        mSearchList.setAdapter(mSearchResultAdapter);
        mSearchList.setOnItemClickListener(this);
        mSearchFriendPresenter = new SearchFriendPresenter(this,getBaseContext());

    }

    @Override
    public void showSearchReuslt(TIMUserProfile userProfile) {
        LogUtils.i(""+userProfile);
        ItemTIMProfile info = new ItemTIMProfile();
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
            ItemTIMProfile info = new ItemTIMProfile();
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
        switch (view.getId()){
            case R.id.cancel:
                finish();
                break;
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

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                mSearchFriendPresenter.searchByID(mSearchInput.getText().toString());
                return true;

            default:
                return super.onKeyUp(keyCode, event);
        }
    }
}
