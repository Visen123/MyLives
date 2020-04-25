package com.app.lizhilives.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.app.lizhilives.R;


public class NetWorkDialogUtils {

    public static void showNotNetWork(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("网络设置").setIcon(R.mipmap.mainplus).setMessage("当前网络无链接！\n是否打开wifi设置？")
                .setPositiveButton("确定", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction("android.net.wifi.PICK_WIFI_NETWORK");
                        intent.putExtra("extra_prefs_show_button_bar", true);
                        intent.putExtra("wifi_enable_next_on_connect", true);
                        context.startActivity(intent);
                    }
                }).setNegativeButton("取消", null).show();
    }
}
