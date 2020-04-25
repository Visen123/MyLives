package com.app.lizhilives.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 基类Activity
 */
public abstract class BaseFragment extends Fragment {
    protected View view;

    /**
     * 初始化变量，包括Intent带的数据和Activity内的变量
     */
    protected abstract void initVariables();

    /**
     * 加载layout布局文件，初始化控件，为控件挂上事件方法
     *
     * @param savedInstanceState
     */
    protected abstract View initViews(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 调用获取数据
     */
    public abstract void loadData();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initVariables();
        view = initViews(inflater, container, savedInstanceState);
        loadData();
        return view;
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

}
