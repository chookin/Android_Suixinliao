package com.tencent.qcloud.presentation.presenter;

import android.util.Log;

import com.tencent.TIMGroupManager;
import com.tencent.TIMGroupPendencyGetParam;
import com.tencent.TIMGroupPendencyListGetSucc;
import com.tencent.TIMValueCallBack;
import com.tencent.qcloud.presentation.viewfeatures.GroupManageMessageView;

/**
 * 群管理逻辑
 */
public class GroupManagerPresenter {

    private static final String TAG = "GroupManagerPresenter";

    private GroupManageMessageView messageView;

    public GroupManagerPresenter(GroupManageMessageView view){
        messageView = view;
    }


    /**
     * 获取群管理最有一条消息,和未读消息数
     * 包括：加群等已决和未决的消息
     */
    public void getGroupManageLastMessage(){
        TIMGroupPendencyGetParam param = new TIMGroupPendencyGetParam();
        param.setNumPerPage(1);
        param.setTimestamp(0);
        TIMGroupManager.getInstance().getGroupPendencyList(param, new TIMValueCallBack<TIMGroupPendencyListGetSucc>() {
            @Override
            public void onError(int i, String s) {
                Log.i(TAG, "onError code" + i + " msg " + s);
            }

            @Override
            public void onSuccess(TIMGroupPendencyListGetSucc timGroupPendencyListGetSucc) {
                if (messageView != null && timGroupPendencyListGetSucc.getPendencies().size() > 0){
                    messageView.onGetGroupManageLastMessage(timGroupPendencyListGetSucc.getPendencies().get(0),
                            0);
                }
            }
        });
    }
}
