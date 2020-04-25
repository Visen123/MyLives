package com.app.lizhilives.mvp;

import android.content.Context;

import com.app.lizhilives.api.Api;
import retrofit2.Retrofit;

public abstract class RXModel {

    public Context mContext;
    private Retrofit retrofit;

    public RXModel(Context mContext) {
        this.mContext = mContext;
        if (retrofit==null)
        {
            retrofit=MyRetrofit.getRetrofits();
        }

    }

    public <V> V getServices(Class<V> service) {
        return retrofit.create(service);
    }


    public Api getApiService() {
        return retrofit.create(Api.class);
    }




}
