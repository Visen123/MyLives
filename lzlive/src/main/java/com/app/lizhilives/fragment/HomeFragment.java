package com.app.lizhilives.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.lizhilives.R;
import com.app.lizhilives.activity.MainActivity;
import com.app.lizhilives.activity.MyUtilsActivity;
import com.app.lizhilives.utils.DBManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.btn_utils)
    Button mBtnUtils;
    @BindView(R.id.btn_bottom_main)
    Button btnBottomMain;
    Unbinder unbinder;
    private View view;
    private DBManager instance;

    public HomeFragment() {
    }


    @Override
    protected void initVariables() {

    }

    @Override
    protected View initViews(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home, container, false);
        }
        ButterKnife.bind(this, view);
        initListener();
        return view;
    }

    @Override
    public void loadData() {

    }

    private void initListener() {
        mBtnUtils.setOnClickListener(this);
        btnBottomMain.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_utils:
                MyUtilsActivity.start(getContext());
                break;
            case R.id.btn_bottom_main:
                MainActivity.start(getContext());
                break;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
