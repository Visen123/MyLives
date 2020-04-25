package com.app.lizhilives.activity;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.app.lizhilives.R;
import com.app.lizhilives.adapter.MWitemAdapter;
import com.app.lizhilives.api.BaseObserver;
import com.app.lizhilives.api.HttpConnect;
import com.app.lizhilives.api.RefreshHeaders;
import com.app.lizhilives.api.RetrofitFactory;
import com.app.lizhilives.model.MeiWenModel;
import com.app.lizhilives.utils.GsonUtil;
import com.qq.e.ads.nativ.widget.NativeAdContainer;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;


/**
 * Created by Administrator on 2018/7/12 0012.
 */

public class MessageActivity extends BaseAcitivity {
    @BindView(R.id.list)
    RecyclerView mList;
    @BindView(R.id.progress)
    ProgressBar mProgress;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;
    @BindView(R.id.lav_error)
    LottieAnimationView mError;
    @BindView(R.id.mAdRoot)
    ConstraintLayout mAdRoot;
    @BindView(R.id.mAdPic)
    ImageView mAdPic;
    @BindView(R.id.mAdContainer)
    NativeAdContainer mAdContainer;
    @BindView(R.id.mCsjOpen)
    TextView mCsjOpen;
    @BindView(R.id.mAdIcon)
    ImageView mAdIcon;
    @BindView(R.id.mAdName)
    TextView mAdName;
    @BindView(R.id.mAdTitle)
    TextView mAdTitle;
    @BindView(R.id.mAdBtn)
    TextView mAdBtn;
    @BindView(R.id.error)
    TextView tvError;
    @BindView(R.id.tool_bar_title)
    TextView mTitle;
    private RefreshHeaders mHeader;
    MWitemAdapter adapter;
    List<MeiWenModel.ListBean> list = new ArrayList<>();
    private int page = 1;
    private TextView mTVNoHoistorys;


    public static void start(Context context){
        Intent intent = new Intent(context, MessageActivity.class);
        context.startActivity(intent);
    }

    private void initList() {
        mRefresh.setEnableRefresh(true);
        mRefresh.setEnableLoadMore(true);
        adapter = new MWitemAdapter(list);
        mList.setLayoutManager(new LinearLayoutManager(this));
        // TODO: 2020/4/8 设置列表滑到最底部显示暂无更多记录
        View footNoMessageView = getLayoutInflater().inflate(R.layout.bottom_no_history, null);
        mTVNoHoistorys = footNoMessageView.findViewById(R.id.mTVNoHoistory);
        adapter.addFooterView(footNoMessageView);
        mList.setAdapter(adapter);

    }

    private void getMessage(final int page) {
        HttpConnect.networkRequest(RetrofitFactory.getInstance().getMWURL(page), new BaseObserver<ResponseBody>(this, null) {
            @Override
            protected void onHandleSuccess(ResponseBody responseBody) {
                try {
                    String json = new String(responseBody.bytes());
                    MeiWenModel meiWenModel = GsonUtil.buildGson().fromJson(json, MeiWenModel.class);
                    List<MeiWenModel.ListBean> mlist = meiWenModel.getList();
                    refreshList(page == 1, mlist);
                    if (list.size() == 0) {
                        showError();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    showError();
                } catch (Exception e) {
                    e.printStackTrace();
                    showError();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (list.size()>0)
                {
                    mRefresh.finishRefresh(false);
                    mRefresh.finishLoadMore(false);
                    mRefresh.setEnableLoadMore(false);
                    mRefresh.setNoMoreData(false);
                    mTVNoHoistorys.setVisibility(View.VISIBLE);
                }else
                {
                   showError();
                }

            }
        });
    }

    private void refreshList(boolean refresh, List<MeiWenModel.ListBean> mlist) {
        mProgress.setVisibility(View.GONE);
        mList.setVisibility(View.VISIBLE);
        mRefresh.finishLoadMore();
        mRefresh.finishRefresh();
        if (refresh) {
            list.clear();
            if (mlist.size() > 0) {
                list.addAll(mlist);
                adapter.notifyDataSetChanged();
                mRefresh.setEnableLoadMore(true);
                mTVNoHoistorys.setVisibility(View.INVISIBLE);
            }

        } else {
            if (mlist.size() > 0) {
                mTVNoHoistorys.setVisibility(View.INVISIBLE);
                list.addAll(mlist);
                adapter.notifyDataSetChanged();
            } else {
                mRefresh.setEnableLoadMore(false);
                mRefresh.setNoMoreData(false);
                mTVNoHoistorys.setVisibility(View.VISIBLE);
            }
        }
    }

    private void showError() {
        tvError.setVisibility(View.VISIBLE);
        mList.setVisibility(View.GONE);
        mProgress.setVisibility(View.GONE);
        mError.setVisibility(View.VISIBLE);
        mRefresh.finishLoadMore();
        mHeader.finishRefresh(mRefresh);
    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_lists;
    }

    @Override
    protected void initUI() {
        ButterKnife.bind(this);
        mTitle.setText("消息");
        mHeader = new RefreshHeaders(this);
        mRefresh.setRefreshHeader(mHeader);
        initList();
        listener();
    }

    public void listener() {
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                getMessage(page);
            }
        });

        mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                getMessage(page);

            }
        });
    }


    @Override
    protected void loadData() {
        getMessage(page);
    }
}
