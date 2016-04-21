package com.tencent.qcloud.timchat.ui.customview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.tencent.qcloud.timchat.R;

/**
 * 提示对话框
 */
public class NotifyDialog extends DialogFragment {

    String tag = "notifyDialog";
    private String title;
    DialogInterface.OnClickListener listener;

    public void show(String title, FragmentManager fm, DialogInterface.OnClickListener listener){
        this.title = title;
        this.listener = listener;
        show(fm, tag);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(title)
                .setPositiveButton(R.string.confirm, listener)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        return builder.create();
    }
}
