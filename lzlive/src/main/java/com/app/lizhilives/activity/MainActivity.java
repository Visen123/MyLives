package com.app.lizhilives.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.app.lizhilives.R;
import com.app.lizhilives.fragment.BaseFragment;
import com.app.lizhilives.fragment.HomeFragment;
import com.app.lizhilives.fragment.MeiWenFragment;
import com.app.lizhilives.fragment.SettingFragment;
import com.app.lizhilives.fragment.TuiJianFragment;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;



public class MainActivity extends BaseAcitivity implements BottomNavigationBar.OnTabSelectedListener {
    public static MainActivity mainActivity;
    public BottomNavigationBar bottomNavigationBar;
    private BaseFragment mFragment0, mFragment1,mFragment2,mFragment3;
    private BaseFragment oldFragment;
    private int lastSelectedPosition = 0;

    private int tabsImg[] = new int[]{R.drawable.tab_selector_home, R.drawable.tab_selector_mw,
            R.drawable.tab_selector_tj, R.drawable.tab_selector_sz};
    private String[] tabsString ={"首页", "美文", "推荐", "设置"};

    public static void start(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initVariables() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                try {
                    Intent intent = new Intent();
                    intent.setAction(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    intent.setData(Uri.parse("package:" + packageName));
                    startActivity(intent);
                } catch (Exception e) {
                }
            }
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initUI() {
        mainActivity = this;
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBarBackgroundColor("#FFF5F7F6");//设置bar背景颜色FFEBF0F4
        bottomNavigationBar.setActiveColor(R.color.colorPrimary);//设置被选中时的颜色
        bottomNavigationBar.setInActiveColor("#FF888888");//设置未被选中时的颜色

        bottomNavigationBar.addItem(new BottomNavigationItem(tabsImg[0], tabsString [0]))
                .addItem(new BottomNavigationItem(tabsImg[1], tabsString [1]))
                .addItem(new BottomNavigationItem(tabsImg[2], tabsString [2]))
                .addItem(new BottomNavigationItem(tabsImg[3], tabsString [3]))
                .initialise();
        bottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment();

    }

    @Override
    protected void loadData() {

    }


    @Override
    public void onTabSelected(int position) {
        bottomNavigationBar.selectTab(position);
        FragmentManager fm = this.getSupportFragmentManager();
        //开启事务
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.hide(oldFragment);
        switch (position) {
            case 0:
                if (mFragment0 == null) {
                    mFragment0 = new HomeFragment();
                }
                if (mFragment0.isAdded()) {
                    transaction.show(mFragment0);
                    mFragment0.loadData();
                } else {
                    transaction.add(R.id.tb, mFragment0);
                }
                lastSelectedPosition = position;
                oldFragment = mFragment0;
                break;
            case 1:
                if (mFragment1 == null) {
                    mFragment1 = new MeiWenFragment();
                }
                if (mFragment1.isAdded()) {
                    transaction.show(mFragment1);
                    mFragment1.loadData();
                } else {
                    transaction.add(R.id.tb, mFragment1);
                }
                lastSelectedPosition = position;
                oldFragment = mFragment1;
                break;
            case 2:
                if (mFragment2 == null) {
                    mFragment2 = new TuiJianFragment();
                }
                if (mFragment2.isAdded()) {
                    transaction.show(mFragment2);
                    mFragment2.loadData();
                } else {
                    transaction.add(R.id.tb, mFragment2);
                }
                lastSelectedPosition = position;
                oldFragment = mFragment2;
                break;
            case 3:
                if (mFragment3 == null) {
                    mFragment3 = new SettingFragment();
                }
                if (mFragment3.isAdded()) {
                    transaction.show(mFragment3);
                    mFragment3.loadData();
                } else {
                    transaction.add(R.id.tb, mFragment3);
                }
                lastSelectedPosition = position;
                oldFragment = mFragment3;
                break;
            default:
                break;
        }
        // 事务提交
        transaction.commitAllowingStateLoss();
    }


    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    /**
     * 设置默认的
     */
    public void setDefaultFragment() {
        lastSelectedPosition = 0;
        bottomNavigationBar.setFirstSelectedPosition(lastSelectedPosition);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mFragment0 = new HomeFragment();
        transaction.replace(R.id.tb, mFragment0);
        transaction.commit();
        oldFragment = mFragment0;
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
