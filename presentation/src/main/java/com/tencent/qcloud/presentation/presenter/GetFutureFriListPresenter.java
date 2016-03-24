package com.tencent.qcloud.presentation.presenter;

import android.content.Context;
import android.util.Log;

import com.tencent.TIMFriendAddResponse;
import com.tencent.TIMFriendFutureItem;
import com.tencent.TIMFriendFutureMeta;
import com.tencent.TIMFriendResponseType;
import com.tencent.TIMFriendResult;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMGetFriendFutureListSucc;
import com.tencent.TIMPageDirectionType;
import com.tencent.TIMValueCallBack;
import com.tencent.qcloud.presentation.viewfeatures.GetFutureFriListData;

import java.util.List;

/**
 * Created by admin on 16/3/7.
 */
public class GetFutureFriListPresenter extends Presenter {
    private GetFutureFriListData view;
    private Context mContext;
    private TIMFriendFutureMeta beginMeta;
    private long reqFlag=0;
    private long futureFlags=0;
    private static final String TAG = GetFutureFriListPresenter.class.getSimpleName();

    public GetFutureFriListPresenter( Context context, GetFutureFriListData view) {
        this.view = view;
        mContext = context;
    }


    public void getFutureFriList(){
        if(beginMeta == null){
            beginMeta = new TIMFriendFutureMeta();
            //	beginMeta.setTimestamp(0);
            //	beginMeta.setNumPerPage(20);
            //	beginMeta.setSeq(0);
            beginMeta.setReqNum(20);
            beginMeta.setPendencySeq(0);
            beginMeta.setDirectionType(TIMPageDirectionType.TIM_PAGE_DIRECTION_DOWN_TYPE);


            reqFlag |= TIMFriendshipManager.TIM_PROFILE_FLAG_NICK;
            reqFlag |= TIMFriendshipManager.TIM_PROFILE_FLAG_FACE_URL;
            reqFlag |= TIMFriendshipManager.TIM_PROFILE_FLAG_REMARK ;
            reqFlag |= TIMFriendshipManager.TIM_PROFILE_FLAG_ALLOW_TYPE ;

            futureFlags |= TIMFriendshipManager.TIM_FUTURE_FRIEND_DECIDE_TYPE;
            futureFlags |= TIMFriendshipManager.TIM_FUTURE_FRIEND_PENDENCY_IN_TYPE;
            futureFlags |= TIMFriendshipManager.TIM_FUTURE_FRIEND_PENDENCY_OUT_TYPE;
            futureFlags |= TIMFriendshipManager.TIM_FUTURE_FRIEND_RECOMMEND_TYPE;
        }


        TIMFriendshipManager.getInstance().getFriendshipProxy().getFutureFriends(reqFlag, futureFlags, null,beginMeta, new TIMValueCallBack<TIMGetFriendFutureListSucc>(){

            @Override
            public void onError(int arg0, String arg1) {
                Log.i(TAG, "onError code"+arg0+" name " +arg1);
                // TODO Auto-generated method stub

            }

            @Override
            public void onSuccess(TIMGetFriendFutureListSucc arg0) {
                // TODO Auto-generated method stub
                beginMeta = arg0.getMeta();
                beginMeta.getPendencyUnReadCnt();

//                if(beginMeta.getTimestamp() == 0){
//                    mBMore = false;
//                }
                List<TIMFriendFutureItem> items = arg0.getItems();
                view.showFutureFriListData(items);
            }

        });
    }

    public void confrimNewFriStatus(String id){
        TIMFriendAddResponse response = new TIMFriendAddResponse();
        response.setIdentifier(id);
        response.setRemark("response");
        response.setType(TIMFriendResponseType.AgreeAndAdd);
        TIMFriendshipManager.getInstance().getFriendshipProxy().addFriendResponse(response, new TIMValueCallBack<TIMFriendResult>(){
            @Override
            public void onError(int arg0, String arg1) {
                // TODO Auto-generated method stub
                Log.e(TAG,"addFriendResponse error:" + arg0 + ":" + arg1);
            }

            @Override
            public void onSuccess(TIMFriendResult arg0) {
                // TODO Auto-generated method stub

            }

        });
    }
}
