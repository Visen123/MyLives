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
public class TuiJianFragment extends BaseFragment {


    public TuiJianFragment() {
        // Required empty public constructor
    }

    public static TuiJianFragment newInstance(int arg0) {
        Bundle args = new Bundle();
        args.putInt("arg0", arg0);
        TuiJianFragment fragment = new TuiJianFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected View initViews(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tui_jian, container, false);
    }

    @Override
    public void loadData() {

    }


}
