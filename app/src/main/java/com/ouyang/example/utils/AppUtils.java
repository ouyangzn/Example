package com.ouyang.example.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * 跟App相关的辅助类,<pre>eg:获取app名、获取app版本号等</pre>
 * @author ouyangzn
 */
public class AppUtils {
    
    private static final String TAG = AppUtils.class.getSimpleName();

    private AppUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取app名
     * @param context
     * @return 当前app名
     */
    public static String getAppName(Context context) {
        PackageInfo packageInfo = getPackageManager(context);
        if (packageInfo != null) {
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        }
        return null;
    }

    /**
     * 获取应用versionName信息
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        PackageInfo packageInfo = getPackageManager(context);
        if (packageInfo != null) {
            return packageInfo.versionName;
        }
        return null;
    }
    /**
     * 获取应用versionCode信息
     * @param context
     * @return 当前应用的版本名称
     */
    public static int getVersionCode(Context context) {
        PackageInfo packageInfo = getPackageManager(context);
        if (packageInfo != null) {
            return packageInfo.versionCode;
        }
        return 0;
    }
    
    /**
     * 获取包信息
     * @param context
     * @return 如果找不到此包名对应的信息则返回null,否则返回PackageInfo
     */
    public static PackageInfo getPackageManager(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        }
        catch (NameNotFoundException e) {
            Log.e(TAG, "获取包信息出错", e);
            return null;
        }
    }

}
