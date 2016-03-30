package com.tencent.qcloud.presentation.presenter;

import android.util.Log;

import com.tencent.TIMAddFriendRequest;
import com.tencent.TIMFriendFutureMeta;
import com.tencent.TIMFriendResult;
import com.tencent.TIMFriendStatus;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMGetFriendFutureListSucc;
import com.tencent.TIMPageDirectionType;
import com.tencent.TIMUserSearchSucc;
import com.tencent.TIMValueCallBack;
import com.tencent.qcloud.presentation.viewfeatures.FriendInfoView;
import com.tencent.qcloud.presentation.viewfeatures.FriendshipManageView;
import com.tencent.qcloud.presentation.viewfeatures.FriendshipMessageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 好友关系链管理逻辑
 */
public class FriendshipManagerPresenter {


    private static final String TAG = "FriendManagerPresenter";

    private FriendshipMessageView friendshipMessageView;
    private FriendshipManageView friendshipManageView;
    private FriendInfoView friendInfoView;
    private final int PAGE_SIZE = 20;
    private int index;
    private boolean isEnd;

    public FriendshipManagerPresenter(FriendshipMessageView view){
        this(view, null, null);
    }

    public FriendshipManagerPresenter(FriendInfoView view){
        this(null, null, view);
    }

    public FriendshipManagerPresenter(FriendshipManageView view){
        this(null, view, null);
    }

    public FriendshipManagerPresenter(FriendshipMessageView view1, FriendshipManageView view2, FriendInfoView view3){
        friendshipManageView = view2;
        friendshipMessageView = view1;
        friendInfoView = view3;
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

    /**
     * 按照名称搜索好友
     *
     * @param key 关键字
     */
    public void searchFriendByName(String key){
        if (friendInfoView == null) return;
        if (!isEnd){
            TIMFriendshipManager.getInstance().getFriendshipProxy().searchUser(key, index++, PAGE_SIZE, new TIMValueCallBack<TIMUserSearchSucc>() {

                @Override
                public void onError(int arg0, String arg1) {

                }

                @Override
                public void onSuccess(TIMUserSearchSucc data) {
                    int getNum = data.getInfoList().size() + (index-1)*PAGE_SIZE;
                    isEnd = getNum == data.getTotalNum();
                    friendInfoView.showFriendInfo(data.getInfoList());
                }

            });
        }else{
            friendInfoView.showFriendInfo(null);
        }

    }


    /**
     * 添加好友
     *
     * @param id 添加对象Identify
     * @param remark 备注名
     * @param message 附加消息
     */
    public void addFriend(final String id,String remark,String message){
        if (friendshipManageView == null) return;
        List<TIMAddFriendRequest> reqList = new ArrayList<>();
        TIMAddFriendRequest req = new TIMAddFriendRequest();
        req.setAddWording(message);
        req.setIdentifier(id);
        req.setRemark(remark);
        reqList.add(req);
        TIMFriendshipManager.getInstance().getFriendshipProxy().addFriend(reqList, new TIMValueCallBack<List<TIMFriendResult>>() {

            @Override
            public void onError(int arg0, String arg1) {
                friendshipManageView.onAddFriend(TIMFriendStatus.TIM_FRIEND_STATUS_UNKNOWN);
            }

            @Override
            public void onSuccess(List<TIMFriendResult> arg0) {
                for (TIMFriendResult item : arg0) {
                    if (item.getIdentifer().equals(id)){
                        friendshipManageView.onAddFriend(item.getStatus());
                    }
                }
            }

        });
    }

}
