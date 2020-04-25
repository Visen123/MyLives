package com.app.lizhilives.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.app.lizhilives.utils.NetWorkDialogUtils;
import com.app.lizhilives.utils.NetWorkUtils;
import com.app.lizhilives.utils.ToastUtil;

/**
 * 自定义广播接收器 用于接收到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String text = intent.getAction();
        // TODO: 2020/4/19 接收发送过来的广播消息
        if (text.equals("myreceiveractivity"))
        {
            // TODO: 2020/4/19 判断是否连接网络成功
            boolean isNetworkConnect= NetWorkUtils.isNetworkConnected(context);
            if (!isNetworkConnect)
            {
                //todo 连接失败，则跳转到设置网络界面
                NetWorkDialogUtils.showNotNetWork(context);
            }else
            {
                ToastUtil.toastShow(context,"网络连接成功！");
            }
        }

    }
}
