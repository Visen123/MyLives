package com.app.lizhilives.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.app.lizhilives.mvp.IBaseView;
import com.app.lizhilives.permission.PermissionActivity;
import com.app.lizhilives.permission.PermissionActivity1;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareAPI;
import com.app.lizhilives.data.Conts;

/**
 * @author Administrator
 * @date 2019/6/21 09:25
 */
public abstract class  BaseAcitivity extends PermissionActivity {

    protected abstract void initVariables();
    protected abstract int getLayoutId();
    protected abstract void initUI();
    protected abstract void loadData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariables();
        setContentView(getLayoutId());
        initUI();
        loadData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }


    public void onClickBack(View view) {
        finish();
    }

    public void onShareClick(View view) {
        /*MyDialog myDialog = MyDialog.newInstance(this);
        myDialog.show(getSupportFragmentManager(),null);*/
        Intent textIntent = new Intent(Intent.ACTION_SEND);
        textIntent.setType("text/plain");
        textIntent.putExtra(Intent.EXTRA_TEXT, Conts.DOWLOAD_URL);
        startActivity(Intent.createChooser(textIntent, "分享好友"));
    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
