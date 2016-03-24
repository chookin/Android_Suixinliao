package com.tencent.qcloud.presentation.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tencent.TIMFriendAddResponse;
import com.tencent.TIMFriendResponseType;
import com.tencent.TIMFriendResult;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMValueCallBack;
import com.tencent.qcloud.presentation.viewfeatures.AgreeNewFriend;

/**
 * Created by admin on 16/3/10.
 */
public class ResponseFriInvitePresenter extends Presenter {
    private static final String TAG = ResponseFriInvitePresenter.class.getSimpleName();
    private Context mContext;
    private AgreeNewFriend mAgree;

    public ResponseFriInvitePresenter(Context context, AgreeNewFriend view) {
        mContext = context;
        mAgree = view;
    }


    public void answerFriInvite(String id, final boolean decision) {
        TIMFriendAddResponse response = new TIMFriendAddResponse();
        response.setIdentifier(id);
        response.setRemark("response");
        if (decision == true) {
            response.setType(TIMFriendResponseType.AgreeAndAdd);

        } else {
            response.setType(TIMFriendResponseType.Reject);
        }
        TIMFriendshipManager.getInstance().getFriendshipProxy().addFriendResponse(response, new TIMValueCallBack<TIMFriendResult>() {
            @Override
            public void onError(int arg0, String arg1) {
                Log.e(TAG, "addFriendResponse error:" + arg0 + ":" + arg1);
            }

            @Override
            public void onSuccess(TIMFriendResult arg0) {
                if (decision == true)
                    mAgree.jumpIntoProfileActicity();
                Toast.makeText(mContext, "reponse suc !!!!", Toast.LENGTH_SHORT).show();
            }

        });
    }

}
