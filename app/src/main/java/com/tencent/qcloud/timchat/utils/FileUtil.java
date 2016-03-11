package com.tencent.qcloud.timchat.utils;

import com.tencent.qcloud.timchat.MyApplication;

import java.io.File;

/**
 * 文件工具类
 */
public class FileUtil {

    private static String pathDiv = "/";
    private static File cacheDir = MyApplication.getContext().getCacheDir();

    private FileUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取缓存文件地址
     */
    public static String getCacheFilePath(String fileName){
        return cacheDir.getAbsolutePath()+pathDiv+fileName;
    }


    /**
     * 判断缓存文件是否存在
     */
    public static boolean isCacheFileExist(String fileName){
        File file = new File(getCacheFilePath(fileName));
        return file.exists();
    }


}
