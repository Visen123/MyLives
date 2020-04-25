package com.app.lizhilives.api;

import android.os.Build;
import android.text.TextUtils;


import com.app.lizhilives.application.BaseAPP;
import com.app.lizhilives.utils.DigitalUtils;
import com.app.lizhilives.utils.Loger;
import com.app.lizhilives.utils.MacAddressUtils;
import com.app.lizhilives.utils.VersionUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.orhanobut.hawk.Hawk;

import java.io.IOException;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitData {


    private static final String BASE_URL = "http://www.duwenz.com/";
    private static final String DEBUG_URL = "http://www.duwenz.com/";
    private static final String RELEASE_URL2 = "http://www.duwenz.com/";
    private static final String RELEASE_URL = "http://www.duwenz.com/";
    private static final String TEST_URL = "http://www.duwenz.com/";

    private static Retrofit retrofit;
    private static Retrofit retrofit3;
    private static Retrofit retrofit4;
    private static RetrofitData instance = new RetrofitData();
    private static OkHttpClient oc;
    private static Gson gson;
    private static HttpLoggingInterceptor httpLoggingInterceptor;

    private RetrofitData() {

/*
        httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    String text = URLDecoder.decode(message, "utf-8");
                    Log.e("OKHttp-----", text);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.e("OKHttp-----", message);
                }
            }
        });
*/
        httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        try {
            oc = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(false)
                    .connectTimeout(30000, TimeUnit.MILLISECONDS)
                    .readTimeout(30000, TimeUnit.MILLISECONDS)
                    .writeTimeout(30000, TimeUnit.MILLISECONDS)
                    .sslSocketFactory(SecureSSLSocketFactory.getInstance(BaseAPP.getInstance()), new SecureX509TrustManager(BaseAPP.getInstance()))
                    .addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            long time = System.currentTimeMillis();
                            String sign = getHeaderSign("", "", time);
                            String brand = "";
                            if (BaseAPP.channel.equals("test")) {
                                brand = "test";
                            } else {
                                brand = Build.BRAND.toLowerCase();
                            }
                            Request request = chain.request()
                                    .newBuilder()
                                    .addHeader("Connection", "close")
                                    .addHeader("BaseAPPid", "567765")
                                    .addHeader("version", VersionUtils.getVersionCode(BaseAPP.getInstance()) + "")
                                    .addHeader("BaseAPPStore", BaseAPP.channel)
                                    .addHeader("brand", brand)
                                    .addHeader("os", RomUtils.getRomType().toString() + RomUtils.getRomType().getVersion())
                                    .addHeader("osv", Build.MODEL)
                                    .addHeader("imei", BaseAPP.getImei())
                                    .addHeader("macAddr", MacAddressUtils.getAdresseMAC(BaseAPP.getInstance()))
                                    .addHeader("lat", "")
                                    .addHeader("lng", "")
                                    .addHeader("ua", Hawk.get("app_ua", ""))
                                    .addHeader("userid", BaseAPP.getUserId() == null ? "" : BaseAPP.getUserId())
                                    .addHeader("time", time + "")
                                    .addHeader("sign", sign)
                                    .addHeader("BaseAPPSecret", "7868768768")
                                    .build();
                            return chain.proceed(request);
                        }
                    }).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(oc)
                .build();

        retrofit3 = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(oc)
                .build();
        String u = "";
        if (getBaseUrl().equals(RELEASE_URL)) {
            u = RELEASE_URL2;
        } else {
            u = RELEASE_URL;
        }
        retrofit4 = new Retrofit.Builder()
                .baseUrl(u)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(oc)
                .build();
    }

    private void update(String url) {
        retrofit3 = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(oc)
                .build();

        if (url.equals(RELEASE_URL)) {
            url = RELEASE_URL2;
        }
        retrofit4 = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(oc)
                .build();
    }


    public static Api getInstance() {
        return instance.getService();
    }

    public static void updateUrl(String url) {
        instance.update(url);
    }

    public Api getService() {
        return retrofit.create(Api.class);
    }

    public static Api getTest2Instance() {
        return instance.getTest2Service();
    }

    public Api getTest2Service() {
        return retrofit3.create(Api.class);
    }

    public static Api getTest3Instance() {
        return instance.getTest3Service();
    }

    public Api getTest3Service() {
        return retrofit4.create(Api.class);
    }


    public static String getHeaderSign(String lat, String lng, long time) {
        String brand = "";
        if (BaseAPP.channel.equals("test")) {
            brand = "test";
        } else {
            brand = Build.BRAND.toLowerCase();
        }
        StringBuffer sb = new StringBuffer();
        TreeMap<String, Object> values = new TreeMap<>();
        values.put("BaseAPPid", "567765");
        values.put("version", VersionUtils.getVersionCode(BaseAPP.getInstance().getApplicationContext()) + "");
        values.put("BaseAPPStore", BaseAPP.channel);
        values.put("brand", brand);
        values.put("osv", Build.MODEL);
        values.put("imei", BaseAPP.getImei());
        values.put("macAddr", MacAddressUtils.getAdresseMAC(BaseAPP.getInstance()));
        values.put("os", RomUtils.getRomType().toString() + RomUtils.getRomType().getVersion());
        values.put("lat", lat);
        values.put("lng", lng);
        values.put("userid", BaseAPP.getUserId() == null ? "" : BaseAPP.getUserId());
        values.put("time", time);
        values.put("BaseAPPsecret", "7868768768");
        for (String key : values.keySet()) {
            if (!TextUtils.isEmpty(values.get(key).toString())) {
                sb.append(key).append("=").append(values.get(key)).append("&");
            }
        }
        Loger.d("headerSign", sb.substring(0, sb.length() - 1));
        return DigitalUtils.md5(sb.substring(0, sb.length() - 1));
    }

    public static String getBaseUrl() {
        String url = Hawk.get("net_url", "");
        if (!TextUtils.isEmpty(url)) {
            return url;
        }
        return RELEASE_URL;
    }
}
