package com.tencent.qcloud.timchat.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.model.UserInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {
    private static final String TAG = SettingFragment.class.getSimpleName();

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View settingsLayout = inflater.inflate(R.layout.fragment_setting, container, false);
        TextView idView = (TextView) settingsLayout.findViewById(R.id.myId);
        String id = UserInfo.getInstance().getId();
        Log.i(TAG, "onCreateView myid  "+id);
        idView.setText(UserInfo.getInstance().getId());
        return settingsLayout ;
    }


}
