package com.app.lizhilives.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.lizhilives.R;
import com.app.lizhilives.data.Conts;
import com.app.lizhilives.view.PagerSlidingTabStrip_2;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeiWenFragment extends BaseFragment {


    private View view;

    public MeiWenFragment() {
    }


    @Override
    protected void initVariables() {

    }

    @Override
    protected View initViews(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view==null)
        {
            view = inflater.inflate(R.layout.fragment_mei_wen, container, false);
            initUI();
        }
        return view;
    }

    @Override
    public void loadData() {

    }


    private void initUI() {
        ViewPager pager = (ViewPager) view.findViewById(R.id.vp_ziLiao);
        FragmentManager fm = getChildFragmentManager();
        pager.setAdapter(new MyPageAdapter(fm));
        PagerSlidingTabStrip_2 tabs = (PagerSlidingTabStrip_2)view. findViewById(R.id.tabs_ziLiao);
        tabs.setTextColor(getResources().getColor(R.color.gray));
        tabs.setTextSize(16);
        tabs.setTabPaddingTop(6);
        tabs.setTabPaddingLeftRight(10);
        tabs.setTabPaddingBottom(6);
        tabs.setSelectedTextColor(getResources().getColor(R.color.colorPrimary));
        tabs.setViewPager(pager);
    }


    private final class MyPageAdapter extends FragmentPagerAdapter {
        private MyPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return Conts.TITLES[position];
        }

        @Override
        public int getCount() {
            return Conts.TITLES.length;
        }

        @Override
        public Fragment getItem(int arg0) {
                return  MWitemFragment.newInstance(arg0);
        }
    }


}
