package com.tencent.qcloud.timchat.utils;

import android.media.MediaPlayer;
import android.net.Uri;

import com.tencent.qcloud.timchat.MyApplication;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * 媒体播放工具
 */
public class MediaUtil {

    private static final String TAG = "MediaUtil";

    private MediaPlayer player;

    private MediaUtil(){
        player = new MediaPlayer();
    }

    private static MediaUtil instance = new MediaUtil();

    public static MediaUtil getInstance(){
        return instance;
    }

    public MediaPlayer getPlayer() {
        return player;
    }

    public void setPlayer(MediaPlayer player) {
        this.player = player;
    }

    public void play(FileInputStream inputStream){
        try{
            player.reset();
            player.setDataSource(inputStream.getFD());
            player.prepare();
            player.start();
        }catch (IOException e){
            LogUtils.e(TAG,"play error:"+e);
        }


    }

    public long getDuration(String path){
        player = MediaPlayer.create(MyApplication.getContext(), Uri.parse(path));
        return player.getDuration();
    }
}
