package com.tencent.qcloud.timchat.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.tencent.TIMCallBack;
import com.tencent.TIMMessage;
import com.tencent.TIMSnapshot;
import com.tencent.TIMVideo;
import com.tencent.TIMVideoElem;
import com.tencent.qcloud.timchat.MyApplication;
import com.tencent.qcloud.timchat.R;
import com.tencent.qcloud.timchat.adapters.ChatAdapter;
import com.tencent.qcloud.timchat.ui.VideoActivity;
import com.tencent.qcloud.timchat.utils.FileUtil;
import com.tencent.qcloud.timchat.utils.LogUtils;
import com.tencent.qcloud.timchat.utils.MediaUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 小视频消息数据
 */
public class VideoMessage extends Message {

    private static final String TAG = "VideoMessage";
    private Context context;
    private Bitmap thumb;

    public VideoMessage(Context context, TIMMessage message){
        this.message = message;
        this.context = context;
    }

    public VideoMessage(TIMMessage message){
        this.message = message;
    }

    public VideoMessage(String fileName){
        message = new TIMMessage();
        TIMVideoElem elem = new TIMVideoElem();
        elem.setVideoPath(FileUtil.getCacheFilePath(fileName));
        thumb = ThumbnailUtils.createVideoThumbnail(FileUtil.getCacheFilePath(fileName), MediaStore.Images.Thumbnails.MINI_KIND);
        elem.setSnapshotPath(FileUtil.createFile(thumb, new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())));
        TIMSnapshot snapshot = new TIMSnapshot();
        snapshot.setType("PNG");
        snapshot.setHeight(thumb.getHeight());
        snapshot.setWidth(thumb.getWidth());
        TIMVideo video = new TIMVideo();
        video.setType("MP4");
        video.setDuaration(MediaUtil.getInstance().getDuration(FileUtil.getCacheFilePath(fileName)));
        elem.setSnapshot(snapshot);
        elem.setVideo(video);
        message.addElement(elem);
    }

    /**
     * 显示消息
     *
     * @param viewHolder 界面样式
     */
    @Override
    public void showMessage(final ChatAdapter.ViewHolder viewHolder) {
        final TIMVideoElem e = (TIMVideoElem) message.getElement(0);
        final TIMSnapshot snapshot = e.getSnapshotInfo();
        if (FileUtil.isCacheFileExist(snapshot.getUuid())){
            showSnapshot(viewHolder,snapshot);
        }else{
            snapshot.getImage(FileUtil.getCacheFilePath(snapshot.getUuid()), new TIMCallBack() {
                @Override
                public void onError(int i, String s) {
                    LogUtils.d(TAG, "get snapshot failed. code: " + i + " errmsg: " + s);
                }

                @Override
                public void onSuccess() {
                    showSnapshot(viewHolder, snapshot);
                }
            });
        }

        getBubbleView(viewHolder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fileName = e.getVideoInfo().getUuid();
                if (FileUtil.isCacheFileExist(fileName)){
                    showVideo(FileUtil.getCacheFilePath(fileName));
                }else{
                    e.getVideoInfo().getVideo(FileUtil.getCacheFilePath(fileName), new TIMCallBack() {
                        @Override
                        public void onError(int i, String s) {
                            LogUtils.d(TAG, "get video failed. code: " + i + " errmsg: " + s);
                        }

                        @Override
                        public void onSuccess() {
                            showVideo(FileUtil.getCacheFilePath(fileName));
                        }
                    });
                }

            }
        });

    }

    /**
     * 获取消息摘要
     */
    @Override
    public String getSummary() {
        return MyApplication.getContext().getString(R.string.summary_video);
    }


    /**
     * 显示缩略图
     */
    private void showSnapshot(final ChatAdapter.ViewHolder viewHolder,final TIMSnapshot snapshot){
        Bitmap bitmap = BitmapFactory.decodeFile(FileUtil.getCacheFilePath(snapshot.getUuid()), new BitmapFactory.Options());
        ImageView imageView = new ImageView(MyApplication.getContext());
        imageView.setImageBitmap(bitmap);
        getBubbleView(viewHolder).removeAllViews();
        getBubbleView(viewHolder).addView(imageView);
    }

    private void showVideo(String path){
        if (context == null) return;
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra("path", path);
        context.startActivity(intent);
    }
}
