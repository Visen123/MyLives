package com.app.lizhilives.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.app.lizhilives.activity.MeiWenDetailActivity;
import com.app.lizhilives.R;
import com.app.lizhilives.adapter.MWitemAdapter;
import com.app.lizhilives.data.Conts;
import com.app.lizhilives.model.MeiWenModel;
import com.app.lizhilives.utils.AsyncHttpClientUtils;
import com.app.lizhilives.utils.GsonUtil;
import com.app.lizhilives.view.MyLinearLayoutManager;
import com.app.lizhilives.view.RecyclerViewDecoration;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class MWitemFragment extends Fragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {



    private View view;
    private ArrayList<MeiWenModel.ListBean> list = new ArrayList<>();
    private MWitemAdapter mAdapter;
    private SwipeRefreshLayout mSwipyRefreshLayout;
    private     int p=1;
    private    int count=1;
    private int arg;
    private RecyclerView mRecyclerView;

    public MWitemFragment() {
        // Required empty public constructor
    }

    public static MWitemFragment newInstance(int arg0) {
        Bundle args = new Bundle();
        args.putInt("arg0", arg0);
        MWitemFragment fragment = new MWitemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        arg = arguments.getInt("arg0");
        if (view==null)
        {
            view = inflater.inflate(R.layout.fragment_mwitem, container, false);
            initUI();
            initData(p);
        }

        return view;
    }

    private void initData(int p) {
        Log.e("文章接口=","="+Conts.URL[arg]+p+".txt");
        AsyncHttpClientUtils.getInstance().get(Conts.URL[arg]+p+".txt", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                mSwipyRefreshLayout.setRefreshing(false);
                mAdapter.setEnableLoadMore(true);
                mSwipyRefreshLayout.setEnabled(true);
               // list.clear();
                String msg = new String(responseBody);
                Log.e("请求成功=","="+msg);
                MeiWenModel meiWenModel = GsonUtil.buildGson().fromJson(msg, MeiWenModel.class);
                count=meiWenModel.getPagecount();
                List<MeiWenModel.ListBean> mlist = meiWenModel.getList();
                list.addAll(mlist);
                if (list.size()==0)
                {
                    mAdapter.loadMoreEnd(true);
                }else
                {
                    mAdapter.notifyDataSetChanged();
                    mAdapter.loadMoreComplete();
                }



            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                mSwipyRefreshLayout.setRefreshing(false);
                mAdapter.setEnableLoadMore(true);
                mSwipyRefreshLayout.setEnabled(true);
                Log.e("请求失败=","="+error.getMessage());
               // Toast.makeText(getContext(), "数据加载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initUI() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_zl_list);
        mSwipyRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_ziLiao);
        mSwipyRefreshLayout.setOnRefreshListener(this);
        mSwipyRefreshLayout.setRefreshing(true);
        LinearLayoutManager  mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //mRecyclerView.addItemDecoration(new RecyclerViewDecoration(getActivity(), LinearLayoutManager.HORIZONTAL));
        initAinm();
        mAdapter = new MWitemAdapter(list);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            /*  final   TextView time = view.findViewById(R.id.tv_cy_title);
                time.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.up);
                time.startAnimation(animation);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        time.setVisibility(View.GONE);
                    }
                },1000);*/
                MeiWenDetailActivity.start(list.get(position).getId(),Conts.TITLES[arg],getContext());
            }
        });
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        

    }


    private void initAinm() {
        //通过加载XML动画设置文件来创建一个Animation对象；
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.left);
        //得到一个LayoutAnimationController对象；
        LayoutAnimationController lac = new LayoutAnimationController(animation);
        //设置控件显示的顺序；
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        //设置控件显示间隔时间；
        lac.setDelay(0.3f);
        //为ListView设置LayoutAnimationController属性；
        mRecyclerView.setLayoutAnimation(lac);
    }


    @Override
    public void onLoadMoreRequested() {
        p++;
        if (count<p)
        {
            p=count;
            Toast.makeText(getContext(), "数据已到底", Toast.LENGTH_SHORT).show();
            mAdapter.loadMoreEnd(false);
        }else
        {
            initData(p);
        }

    }

    @Override
    public void onRefresh() {
        list.clear();
        initData(p);

    }
}
