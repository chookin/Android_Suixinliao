package com.tencent.qcloud.timchat.ui;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.tencent.TIMConversationType;
import com.tencent.TIMMessage;
import com.tencent.qcloud.presentation.presenter.ChatPresenter;
import com.tencent.qcloud.presentation.viewfeatures.ChatView;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.ChatAdapter;
import com.tencent.qcloud.timchat.model.ImageMessage;
import com.tencent.qcloud.timchat.model.Message;
import com.tencent.qcloud.timchat.model.TextMessage;
import com.tencent.qcloud.timchat.model.VoiceMessage;
import com.tencent.qcloud.timchat.ui.customview.ChatInput;
import com.tencent.qcloud.timchat.ui.customview.TemplateTitle;

import java.io.File;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatActivity extends Activity implements ChatView {

    private List<Message> messageList = new ArrayList<>();
    private ChatAdapter adapter;
    private ListView listView;
    private ChatPresenter presenter;
    private ChatInput input;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int IMAGE_STORE = 200;
    private Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        final String identify = getIntent().getStringExtra("identify");
        final TIMConversationType type = (TIMConversationType) getIntent().getSerializableExtra("type");
        final String name = getIntent().getStringExtra("name");
        presenter = new ChatPresenter(this,identify,type);
        adapter = new ChatAdapter(this, R.layout.item_message, messageList);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        TemplateTitle title = (TemplateTitle) findViewById(R.id.chat_title);
        title.setTitleText(name);
        input = (ChatInput) findViewById(R.id.input_panel);
        input.setChatView(this);
        presenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
    }

    /**
     * 显示消息
     *
     * @param message
     */
    @Override
    public void showMessage(TIMMessage message) {
        Message mMessage = null;
        switch (message.getElement(0).getType()){
            case Text:
            case Face:
                mMessage = new TextMessage(message);
                break;
            case Image:
                mMessage = new ImageMessage(message);
                break;
            case Sound:
                mMessage = new VoiceMessage(message);
                break;
        }
        if (mMessage != null){
            messageList.add(mMessage);
            adapter.notifyDataSetChanged();
        }
        listView.setSelection(adapter.getCount() - 1);
    }

    /**
     * 发送消息成功
     *
     * @param message 返回的消息
     */
    @Override
    public void onSendMessageSuccess(TIMMessage message) {
        showMessage(message);
    }

    /**
     * 发送消息失败
     *
     * @param code 返回码
     * @param desc 返回描述
     */
    @Override
    public void onSendMessageFail(int code, String desc) {

    }

    /**
     * 发送图片消息
     */
    @Override
    public void sendImage() {
        fileUri = getOutputMediaFileUri();
        Intent intent_album=new Intent("android.intent.action.GET_CONTENT");
        intent_album.setType("image/*");
        intent_album.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);
        startActivityForResult(intent_album,IMAGE_STORE);
    }

    /**
     * 发送照片消息
     */
    @Override
    public void sendPhoto() {
        Intent intent_photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(); // create a file to save the image
        intent_photo.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
        startActivityForResult(intent_photo, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    /**
     * 发送照片消息
     */
    @Override
    public void sendText() {
        presenter.sendMessage((new TextMessage(input.getText())).getMessage());
        input.setText("");
    }

    /**
     * 开始发送语音消息
     */
    @Override
    public void startSendVoice() {

    }

    /**
     * 结束发送语音消息
     */
    @Override
    public void endSendVoice() {

    }

    /**
     * 结束发送语音消息
     */
    @Override
    public void cancelSendVoice() {

    }

    private Uri getOutputMediaFileUri(){
        File file=getOutputMediaFile();
        if (file==null) return null;
        return Uri.fromFile(file);
    }

    private File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "TIMChat");
        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");

        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                File file = new File(fileUri.getPath());
                if (file.exists()) {
                    Message message = new ImageMessage(file.getAbsolutePath());
                    presenter.sendMessage(message.getMessage());


                }
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
            }

        }else if (requestCode== IMAGE_STORE){
            if (resultCode == RESULT_OK){
                Message message = new ImageMessage(getRealFilePath(data.getData()));
                presenter.sendMessage(message.getMessage());
            }

        }

    }

    /**
     * 从uri转化为地址
     *
     * @param uri uri
     *
     */
    private String getRealFilePath(final Uri uri) {
        String result;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            result = uri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

}
