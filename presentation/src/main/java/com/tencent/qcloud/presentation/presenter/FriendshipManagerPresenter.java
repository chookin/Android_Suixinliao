package com.tencent.qcloud.presentation.presenter;

import android.util.Log;

import com.tencent.TIMFriendFutureMeta;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMGetFriendFutureListSucc;
import com.tencent.TIMPageDirectionType;
import com.tencent.TIMValueCallBack;
import com.tencent.qcloud.presentation.viewfeatures.FriendshipManageView;
import com.tencent.qcloud.presentation.viewfeatures.FriendshipMessageView;

/**
 * 好友关系链管理逻辑
 */
public class FriendshipManagerPresenter {


    private static final String TAG = "FriendManagerPresenter";

    private FriendshipMessageView friendshipMessageView;
    private FriendshipManageView friendshipManageView;

    public FriendshipManagerPresenter(FriendshipMessageView view){
        this(view, null);
    }

    public FriendshipManagerPresenter(FriendshipMessageView view1, FriendshipManageView view2){
        friendshipManageView = view2;
        friendshipMessageView = view1;
    }




    /**
     * 获取好友关系链最有一条消息,和未读消息数
     * 包括：好友已决系统消息，好友未决系统消息，推荐好友消息
     */
    public void getFriendshipLastMessage(){
        TIMFriendFutureMeta meta = new TIMFriendFutureMeta();
        meta.setReqNum(1);
        meta.setPendencySeq(0);
        meta.setDirectionType(TIMPageDirectionType.TIM_PAGE_DIRECTION_DOWN_TYPE);
        long reqFlag = 0, futureFlags = 0;
        reqFlag |= TIMFriendshipManager.TIM_PROFILE_FLAG_NICK;
        reqFlag |= TIMFriendshipManager.TIM_PROFILE_FLAG_REMARK;
        reqFlag |= TIMFriendshipManager.TIM_PROFILE_FLAG_ALLOW_TYPE;
        futureFlags |= TIMFriendshipManager.TIM_FUTURE_FRIEND_DECIDE_TYPE;
        futureFlags |= TIMFriendshipManager.TIM_FUTURE_FRIEND_PENDENCY_IN_TYPE;
        futureFlags |= TIMFriendshipManager.TIM_FUTURE_FRIEND_PENDENCY_OUT_TYPE;
        futureFlags |= TIMFriendshipManager.TIM_FUTURE_FRIEND_RECOMMEND_TYPE;
        TIMFriendshipManager.getInstance().getFutureFriends(reqFlag, futureFlags, null, meta ,new TIMValueCallBack<TIMGetFriendFutureListSucc>(){

            @Override
            public void onError(int arg0, String arg1) {
                Log.i(TAG, "onError code" + arg0 + " msg " + arg1);
            }

            @Override
            public void onSuccess(TIMGetFriendFutureListSucc arg0) {
                long unread = arg0.getMeta().getPendencyUnReadCnt() +
                        arg0.getMeta().getDecideUnReadCnt() +
                        arg0.getMeta().getRecommendUnReadCnt();
                if (friendshipMessageView != null && arg0.getItems().size() > 0){
                    friendshipMessageView.onGetFriendshipLastMessage(arg0.getItems().get(0), unread);
                }
            }

        });
    }

}
