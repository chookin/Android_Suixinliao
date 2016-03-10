package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tencent.TIMFriendFutureItem;
import com.tencent.qcloud.presentation.presenter.GetFutureFriListPresenter;
import com.tencent.qcloud.presentation.viewfeatures.GetFutureFriListData;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.NewFriAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 查找添加新朋友
 */
public class NewFriendActivity extends Activity implements AdapterView.OnItemClickListener ,GetFutureFriListData{
    private String id;
    private ListView mNewFriList;
    private NewFriAdapter mNewFriAdapter;
    private List<TIMFriendFutureItem> mNewFriListReuslt;
    private GetFutureFriListPresenter mGetFutureFriListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfriend);
        mNewFriList = (ListView)findViewById(R.id.newfriend_list);
        mNewFriList.setOnItemClickListener(this);
        mNewFriListReuslt = new ArrayList<TIMFriendFutureItem>();
        mNewFriAdapter = new NewFriAdapter(this,mNewFriListReuslt,this);
        mNewFriList.setAdapter(mNewFriAdapter);
        mGetFutureFriListPresenter = new GetFutureFriListPresenter(getBaseContext(),this);
        mGetFutureFriListPresenter.getFutureFriList();
        mNewFriList.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        
    }

    @Override
    public void showFutureFriListData(List<TIMFriendFutureItem> mFutureFriList) {
        for(TIMFriendFutureItem newFri : mFutureFriList){
            mNewFriListReuslt.add(newFri);
        }
//        mNewFriListReuslt = mFutureFriList;
        mNewFriAdapter.notifyDataSetChanged();
    }

    public void confirmItemStatus(String id){
        mGetFutureFriListPresenter.confrimNewFriStatus(id);
    }
}
