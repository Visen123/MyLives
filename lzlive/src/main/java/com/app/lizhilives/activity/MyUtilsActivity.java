package com.app.lizhilives.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.lizhilives.R;
import com.app.lizhilives.model.User;
import com.app.lizhilives.utils.DBManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 功能工具类
 */

public class MyUtilsActivity extends BaseAcitivity  {
    private DBManager instance;
    @BindView(R.id.tv_myutils_title)
    TextView mTitle;

    @Override
    protected void initVariables() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_utils;
    }

    @Override
    protected void initUI() {
        ButterKnife.bind(this);
        initData();
    }

    @Override
    protected void loadData() {

    }


    public static void start(Context context){
        Intent intent = new Intent(context, MyUtilsActivity.class);
        context.startActivity(intent);
    }

    private void initData() {
        instance = DBManager.getInstance(this);

    }

    @OnClick({R.id.btn_add,R.id.btn_update,R.id.btn_message,R.id.btn_service,
            R.id.btn_receiver,R.id.btn_banner,R.id.btn_cehuan,R.id.btn_mine})
    public void onClicks(View view) {
        User mUser = new User("187", "很健康", "28");
        switch (view.getId()) {
            case R.id.btn_add:// TODO: 2020/4/19  插入数据库一条书籍
                instance.insertTopicMo(mUser);
                break;
            case R.id.btn_update:// TODO: 2020/4/19  修改数据库数据
                mUser.setAge("25");
                mUser.setName("887");
                instance.insertTopicMo(mUser);
                // TODO: 2020/4/19  查询数据库数据
                List<User> users = instance.queryAllTopicMo();
                if (users.size()>0) {
                    mTitle.setText(users.get(0).name);
                }
                break;
            case R.id.btn_message:// TODO: 2020/4/19 消息列表下拉刷新
                MessageActivity.start(this);
                break;
            case R.id.btn_service:// TODO: 2020/4/19 绑定服务 ，执行程序运行在后台
                MyServerActivity.start(this);
                break;
            case R.id.btn_receiver:// TODO: 2020/4/19 清单文件注册广播，然后代码发送广播 ，最后在广播接收器中进行处理
                MyReceiverActivity.start(this);
                break;
            case R.id.btn_banner:
                MyBannerActivity.start(this);
                break;
            case R.id.btn_cehuan:
                MyCeHuanActivity.start(this);
                break;
            case R.id.btn_mine:
                MineActivity.start(this);
                break;
        }
    }
}
