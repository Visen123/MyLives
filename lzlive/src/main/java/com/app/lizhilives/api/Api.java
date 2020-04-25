package com.app.lizhilives.api;


import com.app.lizhilives.model.MeiWenModel;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {

    @PUT("txtjson/classgroup/wenzhan/{key}")
    Observable<ResponseBody> putWenZhan(@Path("key") String key);

    @FormUrlEncoded
    @PUT("txtjson/classgroup/detail/{key}")
    Observable<ResponseBody> putDetail(@Path("key") String key, @Field("type") String type);


    // TODO: 阅读文章
    @GET("txtjson/classgroup/cgmw_all_editortj_{page}.txt")
    Observable<ResponseBody> getMWURL(@Path("page") int page);


    // TODO: 阅读文章
    @GET("txtjson/classgroup/cgmw_all_editortj_{page}.txt")
    Observable<MeiWenModel> getMWURLs(@Path("page") int page);


    // TODO: 猪信息
    @POST("txtjson/classgroup/pig/star")
    Observable<ResponseBody> postPig();

    // TODO: 车现象
    @FormUrlEncoded
    @POST("txtjson/classgroup/cat/rise")
    Observable<ResponseBody> postCat(@Field("id") String id);

}
