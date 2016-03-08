package com.tencent.qcloud.timchat.ui.customview;

import android.content.Context;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tencent.qcloud.presentation.viewfeatures.ChatView;
import com.tencent.qcloud.timchat.R;

/**
 * 聊天界面输入控件
 */
public class ChatInput extends RelativeLayout implements TextWatcher,View.OnClickListener {

    private ImageButton BtnAdd,BtnSend;
    private EditText editText;
    private boolean isSendVisible;
    private ChatView chatView;
    private LinearLayout morePanel;


    public ChatInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.chat_input, this);
        editText = (EditText) findViewById(R.id.input);
        editText.addTextChangedListener(this);
        isSendVisible = editText.getText().length() != 0;
        BtnAdd = (ImageButton) findViewById(R.id.btn_add);
        BtnAdd.setOnClickListener(this);
        BtnSend = (ImageButton) findViewById(R.id.btn_send);
        BtnSend.setOnClickListener(this);
        morePanel = (LinearLayout) findViewById(R.id.more_panel);
        LinearLayout BtnImage = (LinearLayout) findViewById(R.id.btn_photo);
        BtnImage.setOnClickListener(this);
        LinearLayout BtnPhoto = (LinearLayout) findViewById(R.id.btn_image);
        BtnPhoto.setOnClickListener(this);
        setSendBtn();
    }

    /**
     * 关联聊天界面逻辑
     */
    public void setChatView(ChatView chatView){
        this.chatView = chatView;
    }

    /**
     * This method is called to notify you that, within <code>s</code>,
     * the <code>count</code> characters beginning at <code>start</code>
     * are about to be replaced by new text with length <code>after</code>.
     * It is an error to attempt to make changes to <code>s</code> from
     * this callback.
     *
     * @param s
     * @param start
     * @param count
     * @param after
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    /**
     * This method is called to notify you that, within <code>s</code>,
     * the <code>count</code> characters beginning at <code>start</code>
     * have just replaced old text that had length <code>before</code>.
     * It is an error to attempt to make changes to <code>s</code> from
     * this callback.
     *
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        isSendVisible = s!=null&&s.length()>0;
        setSendBtn();
    }

    /**
     * This method is called to notify you that, somewhere within
     * <code>s</code>, the text has been changed.
     * It is legitimate to make further changes to <code>s</code> from
     * this callback, but be careful not to get yourself into an infinite
     * loop, because any changes you make will cause this method to be
     * called again recursively.
     * (You are not told where the change took place because other
     * afterTextChanged() methods may already have made other changes
     * and invalidated the offsets.  But if you need to know here,
     * you can use {@link Spannable#setSpan} in {@link #onTextChanged}
     * to mark your place and then look up from here where the span
     * ended up.
     *
     * @param s
     */
    @Override
    public void afterTextChanged(Editable s) {

    }

    private void setSendBtn(){
        if (isSendVisible){
            BtnAdd.setVisibility(GONE);
            BtnSend.setVisibility(VISIBLE);
        }else{
            BtnAdd.setVisibility(VISIBLE);
            BtnSend.setVisibility(GONE);
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                chatView.sendText();
                break;
            case R.id.btn_add:
                if (morePanel.getVisibility() == VISIBLE){
                    morePanel.setVisibility(GONE);
                    InputMethodManager inputMethodManager=(InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInputFromWindow(morePanel.getApplicationWindowToken(), InputMethodManager.RESULT_SHOWN, 0);
                }else{
                    InputMethodManager inputMethodManager=(InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInputFromWindow(morePanel.getApplicationWindowToken(), InputMethodManager.RESULT_HIDDEN, 0);
                    morePanel.setVisibility(VISIBLE);
                }
                break;
            case R.id.btn_photo:
                chatView.sendPhoto();
                break;
            case R.id.btn_image:
                chatView.sendImage();
                break;
        }
    }


    /**
     * 获取输入框文字
     */
    public String getText(){
        return editText.getText().toString();
    }

    /**
     * 设置输入框文字
     */
    public void setText(String text){
        editText.setText(text);
    }
}
