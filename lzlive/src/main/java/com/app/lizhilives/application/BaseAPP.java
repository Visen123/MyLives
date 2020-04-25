package com.app.lizhilives.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.provider.Settings;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.app.lizhilives.api.DK;
import com.app.lizhilives.utils.DBManager;
import com.app.lizhilives.utils.SharedPreUtils;
import com.app.lizhilives.utils.VersionUtils;
import com.orhanobut.hawk.Hawk;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/11/12.
 */

public class BaseAPP extends Application {

    private static BaseAPP mContext;
    private List<Activity> activityList;
    public static String channel = "";
    public static String imei = "";
    public static boolean login;

    public static BaseAPP getInstance() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        SharedPreUtils.init(mContext);
        DBManager.getInstance(mContext).init();
        allowUnKnowSrc(mContext);
        activityList = new ArrayList<>();



        //初始化 K V 数据库
        Hawk.init(mContext)
                .build();
        if (TextUtils.isEmpty(Hawk.get("app_channel_value", ""))) {
            channel = VersionUtils.getChannel(mContext);
        } else {
            channel = Hawk.get("app_channel_value", "");
        }
        if (TextUtils.isEmpty(channel)) {
            channel = "test";
        }

        //初始化 友盟SDK
        UMConfigure.init(this, "5d11ed200cafb2128a000948", "Umeng", UMConfigure.DEVICE_TYPE_PHONE,
                "null");
        UMConfigure.setLogEnabled(false);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        PlatformConfig.setWeixin("wx29b8ac37da26a2a2", "ebe4bb51ae8acff11d801caab07bb900");
        PlatformConfig.setQQZone("101522953", "8c8a89d2814aee7a7b0d150fae39a");

     /*   PlatformConfig.setWeixin("wxe7c2ed7bbd16c4b4", "c6087d5249b96aced4270349b52073ea");
        PlatformConfig.setQQZone("1105250483", "WtkL6Eq71TLnV3Dk");*/
    }


    public void allowUnKnowSrc(Context context){
        try {
            // todo 0代表禁止安装未知应用，1代表允许安装未知应用
            Settings.Global.putInt(context.getContentResolver(),
                    Settings.Secure.INSTALL_NON_MARKET_APPS, 1);
        }catch(SecurityException se)
        {
            Log.e("SecurityException==","安装未知应用权限问题");
        }
    }


    public List<Activity> getActivityList() {
        return activityList;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
    }


    public static void setLoginMode(boolean login) {
        if (login) {
            BaseAPP.login = true;
            Hawk.put(DK.login, true);
        } else {
            BaseAPP.login = false;
            Hawk.put(DK.login, false);
        }
    }

    public static void setImei(String imei) {
        Hawk.put(DK.imei, imei);
    }

    public static String getImei() {
        return Hawk.get(DK.imei);
    }

    public static void setUserId(String userid) {
        Hawk.put(DK.userId, userid);
    }

    public static String getUserId() {
        return Hawk.get(DK.userId);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
