package com.app.lizhilives.activity;


import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.app.lizhilives.R;
import com.app.lizhilives.fragment.HomeFragment;
import com.app.lizhilives.fragment.MeiWenFragment;
import com.app.lizhilives.fragment.SettingFragment;
import com.app.lizhilives.fragment.ShareFragment;
import com.app.lizhilives.fragment.TuiJianFragment;

public class MainActivityTwo extends FragmentActivity
{
	    TabHost mTabHost;
	    ViewPager  mViewPager;
	    TabsAdapter mTabsAdapter;
    private final Class[] fragments={HomeFragment.class, MeiWenFragment.class,
            ShareFragment.class,TuiJianFragment.class, SettingFragment.class};
    private int tabsImg[] = new int[]{R.drawable.tab_selector_home, R.drawable.tab_selector_mw,
            R.mipmap.mainplus,R.drawable.tab_selector_tj, R.drawable.tab_selector_sz};
    private String[] tabsString ={"首页", "美文",
            "","推荐", "设置"};

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maintwo);
		
		mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();

        mViewPager = (ViewPager)findViewById(R.id.pager);

        mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPager);
        for (int i = 0; i < fragments.length; i++) {
            mTabsAdapter.addTab(mTabHost.newTabSpec("" + i).setIndicator(getView(i)), fragments[i], null);
        }
        if (savedInstanceState != null) 
        {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
		
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tab", mTabHost.getCurrentTabTag());
    }
	
	
	
	 public static class TabsAdapter extends FragmentPagerAdapter
     implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
 private final Context mContext;
 private final TabHost mTabHost;
 private final ViewPager mViewPager;
 private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();

 static final class TabInfo {
     private final String tag;
     private final Class<?> clss;
     private final Bundle args;

     TabInfo(String _tag, Class<?> _class, Bundle _args) {
         tag = _tag;
         clss = _class;
         args = _args;
     }
 }

 static class DummyTabFactory implements TabHost.TabContentFactory {
     private final Context mContext;

     public DummyTabFactory(Context context) {
         mContext = context;
     }

     @Override
     public View createTabContent(String tag) {
         View v = new View(mContext);
         v.setMinimumWidth(0);
         v.setMinimumHeight(0);
         return v;
     }
 }

 public TabsAdapter(FragmentActivity activity, TabHost tabHost, ViewPager pager) {
     super(activity.getSupportFragmentManager());
     mContext = activity;
     mTabHost = tabHost;
     mViewPager = pager;
     mTabHost.setOnTabChangedListener(this);
     mViewPager.setAdapter(this);
     mViewPager.setOnPageChangeListener(this);
 }

 public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
     tabSpec.setContent(new DummyTabFactory(mContext));
     String tag = tabSpec.getTag();

     TabInfo info = new TabInfo(tag, clss, args);
     mTabs.add(info);
     mTabHost.addTab(tabSpec);
     notifyDataSetChanged();
 }

 @Override
 public int getCount() {
     return mTabs.size();
 }

 @Override
 public Fragment getItem(int position) {
     TabInfo info = mTabs.get(position);
     return Fragment.instantiate(mContext, info.clss.getName(), info.args);
 }

 @Override
 public void onTabChanged(String tabId) {
     int position = mTabHost.getCurrentTab();
     mViewPager.setCurrentItem(position);
 }

 @Override
 public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
 }

 @Override
 public void onPageSelected(int position) {
     // Unfortunately when TabHost changes the current tab, it kindly
     // also takes care of putting focus on it when not in touch mode.
     // The jerk.
     // This hack tries to prevent this from pulling focus out of our
     // ViewPager.
     TabWidget widget = mTabHost.getTabWidget();
     int oldFocusability = widget.getDescendantFocusability();
     widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
     mTabHost.setCurrentTab(position);
     widget.setDescendantFocusability(oldFocusability);
 }

 @Override
 public void onPageScrollStateChanged(int state)
 {
  }
}

	
}
