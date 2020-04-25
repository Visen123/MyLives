package com.app.lizhilives.api;

import android.content.Context;
import android.os.Build.VERSION;

import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.StrictHostnameVerifier;
import org.apache.http.conn.ssl.X509HostnameVerifier;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Locale;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

public class SecureSSLSocketFactory extends SSLSocketFactory {
    public static final X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER = new BrowserCompatHostnameVerifier();
    private static final String CLIENT_AGREEMENT = "TLS";
    public static final X509HostnameVerifier STRICT_HOSTNAME_VERIFIER = new StrictHostnameVerifier();
    private static final String[] UN_SAFE_ALGORITHMS = {"TEA", "SHA0", "MD2", "MD4", "RIPEMD", "NULL", "RC4", "DES", "DESX", "DES40", "RC2", "MD5", "ANON", "TLS_EMPTY_RENEGOTIATION_INFO_SCSV"};
    private static String[] safeEnableCiphers = null;
    private static volatile SecureSSLSocketFactory ssf = null;
    private Context mContext;
    private SSLContext sslContext;

    private SecureSSLSocketFactory(Context context) throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException, KeyManagementException, IllegalArgumentException {
        this.sslContext = null;
        this.mContext = context;
        this.sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);
        SecureX509TrustManager secureX509TrustManager = new SecureX509TrustManager(this.mContext);
        this.sslContext.init(null, new X509TrustManager[]{secureX509TrustManager}, null);
    }

    public SecureSSLSocketFactory(InputStream inputStream, String str) throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException, KeyManagementException, IllegalArgumentException {
        this.sslContext = null;
        this.sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);
        HiCloudX509TrustManager hiCloudX509TrustManager = new HiCloudX509TrustManager(inputStream, str);
        this.sslContext.init(null, new X509TrustManager[]{hiCloudX509TrustManager}, null);
    }

    public static SecureSSLSocketFactory getInstance(Context context) throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException, IllegalAccessException, KeyManagementException, IllegalArgumentException {
        if (ssf == null) {
            synchronized (SecureSSLSocketFactory.class) {
                if (ssf == null) {
                    ssf = new SecureSSLSocketFactory(context);
                }
            }
        }
        return ssf;
    }

    private static void setEnableSafeCipherSuites(SSLSocket sSLSocket) {
        if (sSLSocket != null) {
            String[] enabledCipherSuites = sSLSocket.getEnabledCipherSuites();
            ArrayList arrayList = new ArrayList();
            String[] strArr = enabledCipherSuites;
            int length = enabledCipherSuites.length;
            for (int i = 0; i < length; i++) {
                String str = strArr[i];
                boolean z = false;
                String upperCase = str.toUpperCase(Locale.ENGLISH);
                String[] strArr2 = UN_SAFE_ALGORITHMS;
                int length2 = strArr2.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length2) {
                        break;
                    } else if (upperCase.contains(strArr2[i2])) {
                        z = true;
                        break;
                    } else {
                        i2++;
                    }
                }
                if (!z) {
                    arrayList.add(str);
                }
            }
            safeEnableCiphers = (String[]) arrayList.toArray(new String[arrayList.size()]);
            sSLSocket.setEnabledCipherSuites(safeEnableCiphers);
        }
    }

    private void setSocketOptions(Socket socket) {
        if (socket != null && (socket instanceof SSLSocket)) {
            setEnabledProtocols((SSLSocket) socket);
            setEnableSafeCipherSuites((SSLSocket) socket);
        }
    }

    private void setEnabledProtocols(SSLSocket sSLSocket) {
        if (sSLSocket != null && VERSION.SDK_INT >= 16) {
            sSLSocket.setEnabledProtocols(new String[]{"TLSv1.1", "TLSv1.2"});
        }
    }

    public String[] getDefaultCipherSuites() {
        if (safeEnableCiphers != null) {
            return (String[]) safeEnableCiphers.clone();
        }
        return new String[0];
    }

    public String[] getSupportedCipherSuites() {
        return new String[0];
    }

    public Socket createSocket(String str, int i) throws IOException, UnknownHostException {
        Socket createSocket = this.sslContext.getSocketFactory().createSocket(str, i);
        setSocketOptions(createSocket);
        return createSocket;
    }

    public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
        return createSocket(inetAddress.getHostAddress(), i);
    }

    public Socket createSocket(String str, int i, InetAddress inetAddress, int i2) throws IOException, UnknownHostException {
        return createSocket(str, i);
    }

    public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) throws IOException {
        return createSocket(inetAddress.getHostAddress(), i);
    }

    public Socket createSocket(Socket socket, String str, int i, boolean z) throws IOException {
        Socket createSocket = this.sslContext.getSocketFactory().createSocket(socket, str, i, z);
        setSocketOptions(createSocket);
        return createSocket;
    }
}
