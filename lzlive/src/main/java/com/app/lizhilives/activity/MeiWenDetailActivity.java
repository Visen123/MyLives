package com.app.lizhilives.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.lizhilives.data.LConstant;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.app.lizhilives.R;
import com.app.lizhilives.data.Conts;
import com.app.lizhilives.model.DetailModel;
import com.app.lizhilives.utils.GsonUtil;
import com.app.lizhilives.utils.MyProgressDialog;
import com.app.lizhilives.utils.ToastUtil;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.qq.e.ads.interstitial.AbstractInterstitialADListener;
import com.qq.e.ads.interstitial.InterstitialAD;
import com.qq.e.comm.util.AdError;

import cz.msebera.android.httpclient.Header;


public class MeiWenDetailActivity extends BaseAcitivity {

    private int pageindex=1;
    private String id="";
    private String type="";
    private TextView tool_bar_title,detail_tv_content,detail_tv_time,detail_tv_title;


    @Override
    protected void initVariables() {
        Intent intent = getIntent();
        pageindex = intent.getIntExtra(Conts.PAGEINDEX, 1);
        id = intent.getStringExtra(Conts.ID);
        type = intent.getStringExtra(Conts.TYPE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mei_wen_detail;
    }

    @Override
    protected void initUI() {
        getBanner().loadAD();
        showAsPopup();
        tool_bar_title  = (TextView) findViewById(R.id.tool_bar_title);
        tool_bar_title.setText(type);
        detail_tv_content = (TextView)findViewById(R.id.detail_tv_content);
        detail_tv_time = (TextView)findViewById(R.id.detail_tv_time);
        detail_tv_title =(TextView) findViewById(R.id.detail_tv_title);
        findViewById(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }



    @Override
    protected void loadData() {
        initData();
    }

    InterstitialAD iad;

    private InterstitialAD getIAD() {
        if (iad == null) {
            iad = new InterstitialAD(this, LConstant.APPID, LConstant.InterteristalPosID);
        }
        return iad;
    }

    private void showAsPopup() {
        getIAD().setADListener(new AbstractInterstitialADListener() {

            @Override
            public void onNoAD(AdError error) {
                Log.i(
                        "AD_DEMO",
                        String.format("LoadInterstitialAd Fail, error code: %d, error msg: %s",
                                error.getErrorCode(), error.getErrorMsg()));
            }

            @Override
            public void onADReceive() {
                iad.showAsPopupWindow();
            }
        });
        iad.loadAD();
    }

    private void closeAsPopup() {
        if (this.iad != null) {
            iad.closePopupWindow();
            iad.destroy();
            iad = null;
        }
    }

    private void doCloseBanner() {
        if (bannerContainer!=null)
        {
            bannerContainer.removeAllViews();
        }
        if (bv != null) {
            bv.destroy();
            bv = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doCloseBanner();
        closeAsPopup();
    }

    ViewGroup bannerContainer;
    BannerView bv;

    private BannerView getBanner() {
        bannerContainer = (ViewGroup) this.findViewById(R.id.bannerContainer);

        if( this.bv != null ) {
            return this.bv;
        }

        this.bv = new BannerView(this, ADSize.BANNER, LConstant.APPID, LConstant.BannerPosID);
        // 注意：如果开发者的banner不是始终展示在屏幕中的话，请关闭自动刷新，否则将导致曝光率过低。
        // 并且应该自行处理：当banner广告区域出现在屏幕后，再手动loadAD。
        bv.setRefresh(30);
        bv.setADListener(new AbstractBannerADListener() {

            @Override
            public void onNoAD(AdError error) {
                Log.i(
                        "AD_DEMO",
                        String.format("Banner onNoAD，eCode = %d, eMsg = %s", error.getErrorCode(),
                                error.getErrorMsg()));
            }

            @Override
            public void onADReceiv() {
                Log.i("AD_DEMO", "ONBannerReceive");
            }
        });
        bannerContainer.addView(bv);
        return this.bv;
    }

    public static void start( String id,String type, Context context){
        Intent intent = new Intent(context, MeiWenDetailActivity.class);
        intent.putExtra(Conts.ID,id);
        intent.putExtra(Conts.TYPE,type);
        context.startActivity(intent);
    }

        private void initData() {
        MyProgressDialog.dialogShow(this);
        Conts.getDetail_Data(id, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                MyProgressDialog.dialogHide();
                String json = new String(responseBody);
                DetailModel detailModel = GsonUtil.buildGson().fromJson(json, DetailModel.class);
                detail_tv_title.setText(detailModel.getTitle());
                detail_tv_content.setText(Html.fromHtml(detailModel.getContent()));
                detail_tv_time.setText("作者："+detailModel.getAuthor()+"  "+detailModel.getUpdatetime()+" 创建");

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                MyProgressDialog.dialogHide();
                Log.e("加载失败","="+error.getMessage());
                ToastUtil.onFailure(MeiWenDetailActivity.this,statusCode);

            }
        });
    }




}
