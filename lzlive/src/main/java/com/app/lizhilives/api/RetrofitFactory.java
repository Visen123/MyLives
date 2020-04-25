package com.app.lizhilives.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {


    private static final String BASE_URL = "http://www.duwenz.com/";
    private static final String RELEASE_URL2 = "http://www.duwenz.com/";
    private static String RELEASE_URL = "http://www.duwenz.com/";

    private static Retrofit retrofit;
    private static Retrofit retrofit2;
    private static RetrofitFactory instance = new RetrofitFactory();
    private static OkHttpClient oc;
    private static Gson gson;
    private static HttpLoggingInterceptor httpLoggingInterceptor;

    private RetrofitFactory() {

        httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        TrustManagerFactory trustManagerFactory = null;
        try {
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
        }
        X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        oc = new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .connectTimeout(30000, TimeUnit.MILLISECONDS)
                .readTimeout(30000, TimeUnit.MILLISECONDS)
                .writeTimeout(30000, TimeUnit.MILLISECONDS)
                .sslSocketFactory(sslSocketFactory, trustManager)
                .addInterceptor(httpLoggingInterceptor)
                .build();
        gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(oc)
                .build();

        retrofit2 = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(oc)
                .build();

    }

    private void update(String url) {
        retrofit2 = new Retrofit.Builder()
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
        return retrofit2.create(Api.class);
    }




    public static String getBaseUrl() {
        return RELEASE_URL;
    }

    public static void setBaseUrl(String URL) {
        RELEASE_URL = URL;
    }



}
