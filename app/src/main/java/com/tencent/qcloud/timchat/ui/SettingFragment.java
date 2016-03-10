package com.tencent.qcloud.timchat.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tencent.qcloud.presentation.presenter.SettingsPresenter;
import com.tencent.qcloud.presentation.viewfeatures.SettingsFeature;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.model.UserInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment implements SettingsFeature{
    private static final String TAG = SettingFragment.class.getSimpleName();
    private SettingsPresenter mSettingsPresenter;
    public SettingFragment() {
        // Required empty public constructor
    }

    private TextView mStatus;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View settingsLayout = inflater.inflate(R.layout.fragment_setting, container, false);
        TextView idView = (TextView) settingsLayout.findViewById(R.id.myId);
        mStatus = (TextView) settingsLayout.findViewById(R.id.allow_status);
        String id = UserInfo.getInstance().getId();
        Log.i(TAG, "onCreateView myid  "+id);
        idView.setText(UserInfo.getInstance().getId());
        mSettingsPresenter = new SettingsPresenter(this,getActivity());
        mSettingsPresenter.setDefaultAllowType();
        return settingsLayout ;
    }


    @Override
    public void showMyAllowedStatus(int status) {
        if(status==1){
            mStatus.setText("Need by my confirm");
            mStatus.setTextColor(getResources().getColor(R.color.colorLabelRed));
        }
    }
}
