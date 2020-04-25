package com.app.lizhilives.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.app.lizhilives.R;
import com.app.lizhilives.fragment.HomeFragment;
import com.app.lizhilives.fragment.MeiWenFragment;
import com.app.lizhilives.fragment.SettingFragment;
import com.app.lizhilives.fragment.ShareFragment;
import com.app.lizhilives.fragment.TuiJianFragment;
import com.app.lizhilives.host.FragmentTabHost2;
import com.app.lizhilives.view.MyDialog;

import static com.app.lizhilives.utils.RequestPermissions.PERMISSIONS_STORAGE;
import static com.app.lizhilives.utils.RequestPermissions.REQUEST_EXTERNAL_STORAGE;

public class MainActivityOne extends BaseAcitivity {

    private final Class[] fragments={HomeFragment.class, MeiWenFragment.class,
            ShareFragment.class,TuiJianFragment.class, SettingFragment.class};

    private int tabsImg[] = new int[]{R.drawable.tab_selector_home, R.drawable.tab_selector_mw,
            R.mipmap.mainplus,R.drawable.tab_selector_tj, R.drawable.tab_selector_sz};
    private String[] tabsString ={"首页", "美文",
            "","推荐", "设置"};
    private FragmentTabHost2 mTabHost;
    private TabWidget tabwidget;
    String lastid = "0";
    private TextView toolbar_title;


    @Override
    protected void initVariables() {
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mainone;
    }

    @Override
    protected void initUI() {
        initToolbar();
        mTabHost = (FragmentTabHost2) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        for (int i = 0; i < fragments.length; i++) {
            mTabHost.addTab(mTabHost.newTabSpec("" + i).setIndicator(getView(i)), fragments[i], null);
        }
        tabwidget = (TabWidget)findViewById(android.R.id.tabs);
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                switch (tabId)
                {
                    case "0":
                        lastid=tabId;
                        toolbar_title.setText("励志生活");
                        break;
                    case "1":
                        lastid=tabId;
                        toolbar_title.setText("美文欣赏");
                        break;
                    case "2":
                        mTabHost.setCurrentTabByTag(lastid);
                        MyDialog  myDialog = MyDialog.newInstance(MainActivityOne.this);
                        myDialog.show(getSupportFragmentManager(), null);
                        break;
                    case "3":
                        lastid=tabId;
                        toolbar_title.setText("励志推荐");
                        break;
                    case "4":
                        lastid=tabId;
                        toolbar_title.setText("设置");
                        break;
                }

            }
        });

    }

    private void initToolbar() {
        findViewById(R.id.iv_back).setVisibility(View.GONE);
        toolbar_title = (TextView)findViewById(R.id.tool_bar_title);
    }

    @Override
    protected void loadData() {

    }

    private View getView(int index) {
        View view = getLayoutInflater().inflate(R.layout.tabs_layout, null);
        ImageView imageView1 = (ImageView) view.findViewById(R.id.imageView1);
        TextView textView1 = (TextView) view.findViewById(R.id.textView1);
        if (index==2)
        {
            textView1.setVisibility(View.GONE);
        }else
        {
            textView1.setVisibility(View.VISIBLE);
        }
        textView1.setText(tabsString[index]);
        imageView1.setImageResource(tabsImg[index]);
        return view;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private long exitTime = 0;

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

}
