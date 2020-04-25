package com.app.lizhilives.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * Created by hhr on 16/1/12.
 */
public class VersionUtils {
    public VersionUtils() {
    }

    public static boolean hasFroyo() {
        return Build.VERSION.SDK_INT >= 8;
    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= 9;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= 11;
    }

    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= 12;
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= 16;
    }

    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
        }

        //获取应用版本号
        return packageInfo.versionName;
    }

    public static int getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
        }

        //获取应用版本号
        return packageInfo.versionCode;
    }

    public static String getChannel(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            ApplicationInfo appInfo = manager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.get("UMENG_CHANNEL") + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}
