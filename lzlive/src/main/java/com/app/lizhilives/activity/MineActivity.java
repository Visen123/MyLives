package com.app.lizhilives.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.app.lizhilives.R;



/**
 * 功能工具类
 */

public class MineActivity extends BaseAcitivity  {

    private AppBarLayout mAppBar;
    private Toolbar mToolBar;
    private int offheight;
    private LinearLayout mLinearToolBar;

    @Override
    protected void initVariables() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.my_srcoll_list;
    }

    @Override
    protected void initUI() {
        mAppBar = (AppBarLayout) findViewById(R.id.appBar);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mLinearToolBar = (LinearLayout) findViewById(R.id.linear_toolbar);
        int appBarHeight = mAppBar.getHeight();
        int toolBarheight = mToolBar.getHeight();
        offheight = appBarHeight-toolBarheight;
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                Log.e("偏移量==","="+i);
                //todo 判断上滑偏移量高度,偏移量默认是0,往上滑偏移量是负数
                if (-i>=350)
                {
                    mLinearToolBar.setVisibility(View.VISIBLE);
                }else
                {
                    mLinearToolBar.setVisibility(View.INVISIBLE);
                }

            }
        });
        initData();
    }

    @Override
    protected void loadData() {

    }


    public static void start(Context context){
        Intent intent = new Intent(context, MineActivity.class);
        context.startActivity(intent);
    }

    private void initData() {

    }


}
