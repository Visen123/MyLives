package com.app.lizhilives.api;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class SecureX509TrustManager implements X509TrustManager {
    private static final String KEY_TYPE = "bks";
    private static final String TAG = "SecureX509TrustManager";
    private static final String TRUST_FILE = "mykey.bks";
    private static final String TRUST_MANAGER_TYPE = "X509";
    private static final String TRUST_PASSWORD = "";
    protected List<X509TrustManager> m509TrustManager = new ArrayList();

    public SecureX509TrustManager(Context context) throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException, IllegalArgumentException {
        if (context == null) {
            throw new IllegalArgumentException("context is null");
        }
        InputStream inputStream = null;
        try {
            TrustManagerFactory instance = TrustManagerFactory.getInstance(TRUST_MANAGER_TYPE);
            KeyStore instance2 = KeyStore.getInstance(KEY_TYPE);
            InputStream open = context.getAssets().open(TRUST_FILE);
            inputStream = open;
            open.reset();
            instance2.load(inputStream, "".toCharArray());
            instance.init(instance2);
            TrustManager[] trustManagers = instance.getTrustManagers();
            for (int i = 0; i < trustManagers.length; i++) {
                if (trustManagers[i] instanceof X509TrustManager) {
                    this.m509TrustManager.add((X509TrustManager) trustManagers[i]);
                }
            }
            if (this.m509TrustManager.isEmpty()) {
                throw new CertificateException("X509TrustManager is empty");
            }
        } finally {
            inputStream.close();
        }
    }

    public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        if (!this.m509TrustManager.isEmpty()) {
            ((X509TrustManager) this.m509TrustManager.get(0)).checkClientTrusted(x509CertificateArr, str);
            return;
        }
        throw new CertificateException("checkClientTrusted CertificateException");
    }

    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
        if (!this.m509TrustManager.isEmpty()) {
            ((X509TrustManager) this.m509TrustManager.get(0)).checkServerTrusted(x509CertificateArr, str);
            return;
        }
        throw new CertificateException("checkServerTrusted CertificateException");
    }

    public X509Certificate[] getAcceptedIssuers() {
        try {
            ArrayList arrayList = new ArrayList();
            for (X509TrustManager acceptedIssuers : this.m509TrustManager) {
                arrayList.addAll(Arrays.asList(acceptedIssuers.getAcceptedIssuers()));
            }
            return (X509Certificate[]) arrayList.toArray(new X509Certificate[arrayList.size()]);
        } catch (Exception e) {
            return new X509Certificate[0];
        }
    }
}
