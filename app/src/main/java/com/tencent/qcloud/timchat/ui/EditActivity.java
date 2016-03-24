package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.TIMCallBack;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.ui.customview.TemplateTitle;

public class EditActivity extends Activity implements TIMCallBack{


    private String result;

    public static void navToEdit(Activity context, String title, int reqCode){
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra("title", title);
        context.startActivityForResult(intent, reqCode);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getIntent().getStringExtra("title");
        TemplateTitle title = (TemplateTitle) findViewById(R.id.editTitle);
        title.setTitleText(getIntent().getStringExtra("title"));

    }

    @Override
    public void onError(int i, String s) {
        //TODO
    }

    @Override
    public void onSuccess() {

    }
}
