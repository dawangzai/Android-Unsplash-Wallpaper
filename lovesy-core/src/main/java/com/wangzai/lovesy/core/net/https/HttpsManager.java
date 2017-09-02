package com.wangzai.lovesy.core.net.https;

import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

/**
 * Created by wangzai on 2017/8/25
 */

public class HttpsManager {

    public static SSLSocketFactory getFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{getTrustManager()}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HostnameVerifier getVerifier() {
        return new TrueHostnameVerifier();
    }

    public static TrustManager getTrustManager() {
        return new UnSafeTrustManager();
    }
}
