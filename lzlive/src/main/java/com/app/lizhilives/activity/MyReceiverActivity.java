package com.app.lizhilives.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ListView;

import com.app.lizhilives.R;
import com.app.lizhilives.adapter.MyListAdapter;
import com.app.lizhilives.model.MeiWenModel;
import com.app.lizhilives.mvp.WenZhanPresenter;
import com.app.lizhilives.mvp.WenZhanView;
import com.app.lizhilives.receivers.MyReceiver;
import com.app.lizhilives.utils.MyProgressDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 发送广播，接收消息 MVP架构模式
 */
public class MyReceiverActivity extends BaseAcitivity implements WenZhanView{

    private MyReceiver myReceiver;
    private WenZhanPresenter wenZhanPresenter;
    @BindView(R.id.myserver_listview)
    ListView mListview;
    private MyListAdapter myListAdapter;
    private List<MeiWenModel.ListBean> mList=new ArrayList<>();

    @Override
    protected void initVariables() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_receiver;
    }

    public static void start(Context context){
        Intent intent = new Intent(context, MyReceiverActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void initUI() {
        ButterKnife.bind(this);
     /*   // TODO: 2020/4/18 动态注册广播
        myReceiver = new MyReceiver();
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("myreceiveractivity");
        registerReceiver(myReceiver,mIntentFilter);*/

        // TODO: 2020/4/18 发送广播
        Intent intent = new Intent();
        intent.setAction("myreceiveractivity");
        sendBroadcast(intent);
        myListAdapter = new MyListAdapter(this, mList);
        mListview.setAdapter(myListAdapter);

    }

    @Override
    protected void loadData() {
        wenZhanPresenter = new WenZhanPresenter(this);
        wenZhanPresenter.loadListData(2);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        // TODO: 2020/4/18 注销广播
       /* if (myReceiver!=null)
        {
            unregisterReceiver(myReceiver);
        }*/

    }

    @Override
    public void getSuccess(MeiWenModel listbean) {
        mList.clear();
        mList.addAll(listbean.getList());
        if (myListAdapter!=null)
        {
            myListAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void showLoading() {
        MyProgressDialog.dialogShow(this);
    }

    @Override
    public void hideLoading() {
       MyProgressDialog.dialogHide();
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public Context getContext() {
        return this;
    }

}
