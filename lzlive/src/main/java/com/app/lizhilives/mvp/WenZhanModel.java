package com.app.lizhilives.mvp;

import android.content.Context;

import com.app.lizhilives.api.Api;
import com.app.lizhilives.api.BaseObserver;
import com.app.lizhilives.api.HttpConnect;
import com.app.lizhilives.model.MeiWenModel;

/**
 * 网络请求下载数据
 */
public class WenZhanModel extends RXModel {
    public WenZhanModel(Context mContext) {
        super(mContext);
    }

    public void getInfro(int page, BaseObserver<MeiWenModel> mBaseObserver){
       // HttpConnect.networkRequest(getServices(Api.class).getMWURLs(page),mBaseObserver);
        HttpConnect.networkRequest(getApiService().getMWURLs(page),mBaseObserver);

    }
}
