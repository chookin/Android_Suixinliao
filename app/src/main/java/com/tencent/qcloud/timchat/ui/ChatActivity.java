package com.tencent.qcloud.timchat.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
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
import com.tencent.qcloud.timchat.model.MessageFactory;
import com.tencent.qcloud.timchat.model.TextMessage;
import com.tencent.qcloud.timchat.model.VideoMessage;
import com.tencent.qcloud.timchat.model.VoiceMessage;
import com.tencent.qcloud.timchat.ui.customview.ChatInput;
import com.tencent.qcloud.timchat.ui.customview.TemplateTitle;
import com.tencent.qcloud.timchat.ui.customview.VoiceSendingView;
import com.tencent.qcloud.timchat.utils.FileUtil;
import com.tencent.qcloud.timchat.utils.RecorderUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends FragmentActivity implements ChatView {

    private static final String TAG = "ChatActivity";

    private List<Message> messageList = new ArrayList<>();
    private ChatAdapter adapter;
    private ListView listView;
    private ChatPresenter presenter;
    private ChatInput input;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int IMAGE_STORE = 200;
    private Uri fileUri;
    private VoiceSendingView voiceSendingView;
    private RecorderUtil recorder = new RecorderUtil();


    public static void navToChat(Context context, String identify, TIMConversationType type){
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("identify", identify);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        final String identify = getIntent().getStringExtra("identify");
        final TIMConversationType type = (TIMConversationType) getIntent().getSerializableExtra("type");
        presenter = new ChatPresenter(this, identify, type);
        input = (ChatInput) findViewById(R.id.input_panel);
        input.setChatView(this);
        adapter = new ChatAdapter(this, R.layout.item_message, messageList);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        input.setInputMode(ChatInput.InputMode.NONE);
                        break;
                }
                return false;
            }
        });
        presenter.getConversation().setReadMessage();
        TemplateTitle title = (TemplateTitle) findViewById(R.id.chat_title);
        title.setTitleText(presenter.getConversation().getPeer());
        switch (type) {
            case C2C:
                title.setMoreImg(R.drawable.btn_person);
                title.setMoreImgAction(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ChatActivity.this, ProfileActivity.class);
                        intent.putExtra("identify", identify);
                        startActivity(intent);
                    }
                });
                break;
            case Group:
                title.setMoreImg(R.drawable.btn_group);
                title.setMoreImgAction(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ChatActivity.this, GroupProfileActivity.class);
                        intent.putExtra("identify", identify);
                        startActivity(intent);
                    }
                });
                break;

        }
        voiceSendingView = (VoiceSendingView) findViewById(R.id.voice_sending);
        presenter.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.stop();
    }

    /**
     * 显示消息
     *
     * @param message
     */
    @Override
    public void showMessage(TIMMessage message) {
        if (message == null) {
            adapter.notifyDataSetChanged();
        } else {
            Message mMessage = MessageFactory.getMessage(message);
            if (mMessage != null) {
                if (messageList.size()==0){
                    mMessage.setHasTime(null);
                }else{
                    mMessage.setHasTime(messageList.get(messageList.size()-1).getMessage());
                }
                messageList.add(mMessage);
                adapter.notifyDataSetChanged();
                listView.setSelection(adapter.getCount()-1);
            }
        }

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
        Intent intent_album = new Intent("android.intent.action.GET_CONTENT");
        intent_album.setType("image/*");
        startActivityForResult(intent_album, IMAGE_STORE);
    }

    /**
     * 发送照片消息
     */
    @Override
    public void sendPhoto() {
        Intent intent_photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File tempFile = FileUtil.getTempFile(FileUtil.FileType.IMG);
        if (tempFile != null) {
            fileUri = Uri.fromFile(tempFile);
        }
        intent_photo.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent_photo, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    /**
     * 发送文本消息
     */
    @Override
    public void sendText() {
        Message message = new TextMessage(input.getText());
        presenter.sendMessage(message.getMessage());
        input.setText("");
    }


    /**
     * 开始发送语音消息
     */
    @Override
    public void startSendVoice() {
        voiceSendingView.setVisibility(View.VISIBLE);
        voiceSendingView.showRecording();
        recorder.startRecording();
    }

    /**
     * 结束发送语音消息
     */
    @Override
    public void endSendVoice() {
        voiceSendingView.release();
        voiceSendingView.setVisibility(View.GONE);
        recorder.stopRecording();
        if (recorder.getTimeInterval() < 1) {
            Toast.makeText(this, getResources().getString(R.string.chat_audio_too_short), Toast.LENGTH_SHORT).show();
        } else {
            Message message = new VoiceMessage(recorder.getTimeInterval(), recorder.getDate());
            presenter.sendMessage(message.getMessage());
        }
    }

    /**
     * 发送小视频消息
     *
     * @param fileName 文件名
     */
    @Override
    public void sendVideo(String fileName) {
        Message message = new VideoMessage(fileName);
        presenter.sendMessage(message.getMessage());
    }


    /**
     * 结束发送语音消息
     */
    @Override
    public void cancelSendVoice() {

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
            }
        } else if (requestCode == IMAGE_STORE) {
            if (resultCode == RESULT_OK) {
                Message message = new ImageMessage(FileUtil.getImageFilePath(this, data.getData()));
                presenter.sendMessage(message.getMessage());
            }

        }

    }

    public void showLast(){
        listView.setSelection(adapter.getCount()-1);
    }




}
