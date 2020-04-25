package com.app.lizhilives.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.lizhilives.R;
import com.app.lizhilives.model.User;
import com.app.lizhilives.utils.DBManager;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 广告Banner
 */

public class MyBannerActivity extends BaseAcitivity{
    @BindView(R.id.banner)
    MZBannerView mMZBanner;
    @BindView(R.id.banner_normal)
    MZBannerView mNormalBanner;
    List<Integer> list = new ArrayList<>();
    List<Integer> bannerList = new ArrayList<>();
    public static final  String TAG="MyBannerActivity";

    @Override
    protected void initVariables() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.banner_layout;
    }

    @Override
    protected  void initUI() {
        ButterKnife.bind(this);
        mMZBanner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                Toast.makeText(MyBannerActivity.this,"click page:"+position,Toast.LENGTH_LONG).show();
            }
        });
        mMZBanner.addPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e(TAG,"----->addPageChangeLisnter:"+position + "positionOffset:"+positionOffset+ "positionOffsetPixels:"+positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.e(TAG,"addPageChangeLisnter:"+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mMZBanner.setIndicatorVisible(true);
        // 代码中更改indicator 的位置
        //mMZBanner.setIndicatorAlign(MZBannerView.IndicatorAlign.LEFT);
        //mMZBanner.setIndicatorPadding(10,0,0,150);


    }

    @Override
    protected void loadData() {
        initData();
    }

    public static void start(Context context){
        Intent intent = new Intent(context, MyBannerActivity.class);
        context.startActivity(intent);
    }

    private void initData() {

        for(int i=0;i<6;i++){
            list.add(R.mipmap.ic_full);
        }

        for(int i=0;i<6;i++){
            bannerList.add(R.mipmap.splash);
        }

        mMZBanner.setPages(bannerList, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });

        mNormalBanner.setPages(list, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        mMZBanner.pause();
        mNormalBanner.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMZBanner.start();
        mNormalBanner.start();
    }

    public static class BannerViewHolder implements MZViewHolder<Integer> {
        private ImageView mImageView;
        @Override
        public View createView(Context context) {
            // 返回页面布局文件
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item,null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            // 数据绑定
            mImageView.setImageResource(data);
        }
    }


}
