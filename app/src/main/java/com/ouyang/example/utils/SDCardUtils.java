package com.ouyang.example.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * sdcard工具类
 * @author ouyangzn
 */
public class SDCardUtils {
    /** app的根目录文件夹名-根据不同项目或公司自行设置 */
    private final static String ROOT_PATH_NAME = "ouyang";

    private SDCardUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断sd卡是否已挂载
     * @return
     */
    public static boolean hasSdcard() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 获取sd卡的路径
     * @return
     */
    public static String getSDCardPath() {
        if (hasSdcard()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        else {
            return null;
        }
    }

    /**
     * 获取本应用的根目录路径
     * @return 有sd卡则为sdcard/{@link #ROOT_PATH_NAME}/,没有则返回/data/data/应用包名/{@link #ROOT_PATH_NAME}/
     */
    public static String getAppRootPath(Context context) {
        if (hasSdcard()) {
            return getSDCardPath() + File.separator + ROOT_PATH_NAME
                + File.separator;
        }
        else {
            return context.getFilesDir().getPath() + File.separator
                + ROOT_PATH_NAME + File.separator;
        }
    }

    /**
     * 创建本应用的根目录
     * @param context
     */
    public static void createAppRootPath(Context context) {
        String rootPath = "";
        if (hasSdcard()) {
            rootPath = getSDCardPath() + File.separator + ROOT_PATH_NAME
                + File.separator;
        }
        else {
            rootPath = context.getFilesDir().getPath() + File.separator
                + ROOT_PATH_NAME + File.separator;
        }
        File file = new File(rootPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * 获取内存可用空间
     * @return
     */
    public static long getAvailableLength(Context context) {
        StatFs stat = null;
        if (hasSdcard()) {
            stat = new StatFs(getSDCardPath());

        }
        else {
            stat = new StatFs(context.getFilesDir().getPath());
        }
        // 获取空闲的数据块的数量
        long availableBlocks = (long) stat.getAvailableBlocks() - 4;
        // 获取单个数据块的大小（byte）
        long blockLength = stat.getBlockSize();
        return blockLength * availableBlocks;
    }

    /**
     * 获取系统存储路径
     * @return
     */
    public static String getRootDirectoryPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

}
