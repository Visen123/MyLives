package com.app.lizhilives.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.app.lizhilives.R;
import com.app.lizhilives.adapter.MWitemAdapter;
import com.app.lizhilives.api.BaseObserver;
import com.app.lizhilives.api.HttpConnect;
import com.app.lizhilives.api.RetrofitFactory;
import com.app.lizhilives.model.MeiWenModel;
import com.app.lizhilives.utils.GsonUtil;
import com.app.lizhilives.utils.ToastUtil;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

/**
 * 侧滑删除
 */
public class MyCeHuanActivity extends BaseAcitivity {


    @BindView(R.id.cehuan_swipe_recycler)
    SwipeMenuRecyclerView cehuanSwipeRecycler;
    List<MeiWenModel.ListBean> list = new ArrayList<>();
    private MWitemAdapter mWitemAdapter;

    @Override
    protected void initVariables() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_cehuan;
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, MyCeHuanActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initUI() {
        ButterKnife.bind(this);
        initSlide();
    }



    private void initSlide() {
        mWitemAdapter = new MWitemAdapter(list);

        // cehuanSwipeRecycler.setItemViewSwipeEnabled(true);// 开启滑动删除。默认关闭。
        cehuanSwipeRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // 创建菜单：
        SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
                // 在Item右侧添加一个菜单。
                // 1.编辑
                // 各种文字和图标属性设置。
                SwipeMenuItem modifyItem = new SwipeMenuItem(MyCeHuanActivity.this)
                        .setBackgroundColor(getResources().getColor(R.color.colorAccent))
                        .setText("编辑")
                        .setTextColor(Color.WHITE)
                        .setTextSize(15) // 文字大小。
                        .setWidth(140)
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                leftMenu.addMenuItem(modifyItem);
                // 2 删除
                SwipeMenuItem deleteItem = new SwipeMenuItem(MyCeHuanActivity.this);
                deleteItem.setText("删除")
                        .setBackgroundColor(getResources().getColor(R.color.colorAccent))
                        .setImage(R.drawable.dialog_share_close)
                        .setTextColor(Color.WHITE) // 文字颜色。
                        .setTextSize(15) // 文字大小。
                        .setWidth(140)
                        .setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

                rightMenu.addMenuItem(deleteItem);

                // 注意：哪边不想要菜单，那么不要添加即可。
            }
        };
        // 设置监听器。
        cehuanSwipeRecycler.setSwipeMenuCreator(mSwipeMenuCreator);

        SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
                menuBridge.closeMenu();

                int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
                int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
                int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
                if (direction==SwipeMenuRecyclerView.LEFT_DIRECTION)
                {
                    if (menuPosition == 0) {
                        ToastUtil.toastShow(MyCeHuanActivity.this,"编辑成功");
                    }
                }
                if (direction==SwipeMenuRecyclerView.RIGHT_DIRECTION)
                {
                    if (menuPosition == 0) {
                        ToastUtil.toastShow(MyCeHuanActivity.this,"删除成功");

                    }
                }

            }
        };

        // 菜单点击监听。
        cehuanSwipeRecycler.setSwipeMenuItemClickListener(mMenuItemClickListener);

        // 必须 最后执行
        cehuanSwipeRecycler.setAdapter(mWitemAdapter);
    }


    private void getMessage(final int page) {
        HttpConnect.networkRequest(RetrofitFactory.getInstance().getMWURL(page), new BaseObserver<ResponseBody>(this, null) {
            @Override
            protected void onHandleSuccess(ResponseBody responseBody) {
                try {
                    String json = new String(responseBody.bytes());
                    MeiWenModel meiWenModel = GsonUtil.buildGson().fromJson(json, MeiWenModel.class);
                    List<MeiWenModel.ListBean> mlist = meiWenModel.getList();
                    list.clear();
                    list.addAll(mlist);
                    mWitemAdapter.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);

            }
        });
    }


    @Override
    protected void loadData() {
        getMessage(1);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
