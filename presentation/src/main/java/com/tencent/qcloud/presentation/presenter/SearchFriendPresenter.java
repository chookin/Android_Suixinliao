package com.tencent.qcloud.presentation.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tencent.TIMFriendshipManager;
import com.tencent.TIMUserProfile;
import com.tencent.TIMUserSearchSucc;
import com.tencent.TIMValueCallBack;
import com.tencent.qcloud.presentation.viewfeatures.SearchFriendData;

import java.util.ArrayList;

/**
 * 获取自己分组的数据，
 */
public class SearchFriendPresenter extends Presenter {

    private static final String TAG = SearchFriendPresenter.class.getSimpleName();
    SearchFriendData view;
    private Context mContext;
    private int mPage = 0;
    private int MAX_PAGE_NUM = 20;
    private ArrayList<TIMUserProfile> mSearchResult = new ArrayList<TIMUserProfile>();;
    private String mSearchId;
    private boolean mBMore;

    public SearchFriendPresenter(SearchFriendData view, Context context) {
        this.view = view;
        mContext = context;
    }



    public void searchByID(String id) {
        mSearchId = id;
        Log.i(TAG, "searchByID:" + mSearchId);
        TIMFriendshipManager.getInstance().getFriendshipProxy().searchFriend(mSearchId, new TIMValueCallBack<TIMUserProfile>() {
            @Override
            public void onError(int arg0, String arg1) {
                Log.i(TAG, "onError " + arg0);
                if (6011 == arg0)
                    arg1 = "用户不存在";
                searchByNick(mSearchId,0,MAX_PAGE_NUM);
            }

            @Override
            public void onSuccess(TIMUserProfile userProfile) {
                Log.i(TAG, "onSuccess " + userProfile.getIdentifier());
                // TODO Auto-generated method stub
                view.showSearchReuslt(userProfile);
            }
        });
    }


    private void searchByNick(String nick,int mPage,int max) {
        Log.d(TAG, "searchFriend by nick:"+nick);
        TIMFriendshipManager.getInstance().getFriendshipProxy().searchUser(nick, mPage, max, new TIMValueCallBack<TIMUserSearchSucc>() {

            @Override
            public void onError(int arg0, String arg1) {
                Log.e(TAG, "searchByNick error:" + arg0 + ":" + arg1);
                if (6011 == arg0)
                    arg1 = "用户不存在";
                Toast.makeText(mContext, "查询异常:" + arg0 + ":" + arg1, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(TIMUserSearchSucc data) {
                Log.d(TAG, "searchByNick ok:" + data.getInfoList().size());
                if (data.getInfoList().size() == 0) {
                    Toast.makeText(mContext, "该用户不存在!", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (int i = 0; i < data.getInfoList().size(); i++) {
                    TIMUserProfile userProfile = data.getInfoList().get(i);
                    mSearchResult.add(userProfile);

                }
                view.showSearchReusltList(mSearchResult);
                if (data.getTotalNum() == mSearchResult.size()) {
                    mBMore = false;
                }

            }

        });
    }
}
