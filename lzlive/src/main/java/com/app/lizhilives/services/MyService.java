package com.app.lizhilives.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;


import java.util.Calendar;

/**
 * 自定义后台进程服务
 */
public class MyService extends Service {
    private static final String TAG = MyService.class.getName();
    private MyBinder myBinder;
    private final Handler mHandler = new Handler();
    private boolean isStop=true;
    TimeClickListener timeClickListener;
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (!isStop)
            {
                Calendar mCalender = Calendar.getInstance();
                int minute = mCalender.get(Calendar.MINUTE);
                Log.e("当前=","=" + minute );
                timeClickListener.getTime(minute);
                mHandler.postDelayed(this, 1000);

            }

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("服务","=服务OnCreate方法");
    }

    public MyService() {
        if (myBinder == null) {
            myBinder = new MyBinder();
        }

    }


    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    public class MyBinder extends Binder {
        //todo 加载数据
        public void startLoad() {
            mHandler.post(mRunnable);

        }

        //todo 开始执行
        public void startRun(){
            isStop=false;
        }

        //todo 结束执行
        public void endRun(){
            isStop=true;
        }

        public void setTimeClick(TimeClickListener timeClick){
            timeClickListener=timeClick;
        }
    }


  public   interface TimeClickListener {
        void getTime(int time);
    }
}
