package com.app.lizhilives.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.lizhilives.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends BaseFragment {


    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initVariables() {

    }

    @Override
    protected View initViews(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void loadData() {

    }


}
