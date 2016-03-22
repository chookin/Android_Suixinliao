package com.tencent.qcloud.timchat;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.TIMManager;
import com.tencent.TIMOfflinePushListener;
import com.tencent.TIMOfflinePushNotification;
import com.tencent.qalsdk.sdk.MsfSdkUtils;
import com.tencent.qcloud.presentation.business.InitBusiness;
import com.tencent.qcloud.timchat.model.UserInfo;
import com.tencent.qcloud.tlslibrary.service.TlsBusiness;
import com.tencent.qcloud.tlslibrary.service.TLSService;




/**
 * 全局Application
 */
public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        if(MsfSdkUtils.isMainProcess(this)){
            TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {
                @Override
                public void handleNotification(TIMOfflinePushNotification timOfflinePushNotification) {
                    timOfflinePushNotification.doNotify(getApplicationContext(),R.drawable.ic_launcher);
                }
            });
        }

        InitBusiness.start(context);
        TlsBusiness.init(context);
        String id =  TLSService.getInstance().getLastUserIdentifier();
        UserInfo.getInstance().setId(id);
        UserInfo.getInstance().setUserSig(TLSService.getInstance().getUserSig(id));
        ImageLoaderInit();
    }

    public static Context getContext() {
        return context;
    }



    private void ImageLoaderInit() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .resetViewBeforeLoading(true)
//                .showImageOnLoading(R.drawable.default_cover)
//                .showImageOnFail(R.drawable.default_cover)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context).threadPriority(Thread.NORM_PRIORITY - 2)
                .threadPoolSize(5)
                .memoryCache(new LruMemoryCache(20 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(10 * 1024 * 1024)
                .diskCacheExtraOptions(480, 320, null)
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileCount(100)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(options)
                .writeDebugLogs().build();
        ImageLoader.getInstance().init(config);
    }
}
