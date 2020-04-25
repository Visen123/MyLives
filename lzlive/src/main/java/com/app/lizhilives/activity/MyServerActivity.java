package com.app.lizhilives.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.ListView;

import com.app.lizhilives.R;
import com.app.lizhilives.adapter.MyListAdapter;
import com.app.lizhilives.api.RetrofitFactory;
import com.app.lizhilives.data.Conts;
import com.app.lizhilives.model.MeiWenModel;
import com.app.lizhilives.services.MyService;
import com.app.lizhilives.utils.AsyncHttpClientUtils;
import com.app.lizhilives.utils.GsonUtil;
import com.app.lizhilives.utils.MyProgressDialog;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Consts;
import cz.msebera.android.httpclient.Header;

/**
 * 绑定服务，后台运行处理
 */
public class MyServerActivity extends BaseAcitivity {
    @BindView(R.id.myserver_listview)
    ListView mListview;
    private Intent serviceIntent;
    private MyListAdapter myListAdapter;
    private List<MeiWenModel.ListBean> mList=new ArrayList<>();
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e("链接成功", "=链接成功");
            MyService.MyBinder myBinder = (MyService.MyBinder) iBinder;
            myBinder.startRun();
            myBinder.startLoad();
            myBinder.setTimeClick(new MyService.TimeClickListener() {
                @Override
                public void getTime(int time) {
                    if (time == 58) {
                        myBinder.endRun();
                        Log.e("时间到=", "=" + time);
                    }
                }
            });


        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e("链接失败", "=链接失败");
        }
    };


    @Override
    protected void initVariables() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_server;
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, MyServerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initUI() {
        ButterKnife.bind(this);
        // TODO: 2020/4/18 启动服务
        serviceIntent = new Intent(this, MyService.class);
        startService(serviceIntent);
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE);

        myListAdapter = new MyListAdapter(this, mList);
        mListview.setAdapter(myListAdapter);
    }

    @Override
    protected void loadData() {
        MyProgressDialog.dialogShow(this);
        AsyncHttpClientUtils.getInstance().get(Conts.SWURL+"1.txt", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                MyProgressDialog.dialogHide();
                String json = new String(responseBody);
                MeiWenModel meiWenModel = GsonUtil.buildGson().fromJson(json, MeiWenModel.class);
                List<MeiWenModel.ListBean> list = meiWenModel.getList();
                mList.clear();
                mList.addAll(list);
                if (myListAdapter!=null)
                {
                    myListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        stopService(serviceIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
