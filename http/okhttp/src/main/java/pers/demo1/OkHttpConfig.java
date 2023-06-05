package pers.demo1;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * @auther ken.ck
 * @date 2023/6/2 15:01
 */
public class OkHttpConfig {

    private static final Long connectTimeout = 30L;
    private static final Long readTimeout = 10L;;
    private static final Long writeTimeout = 30L;;
    private static final Integer maxIdleConnections = 50;
    private static final Integer keepAliveDuration = 5;

    public static OkHttpClient getOkHttpClient() {

        ConnectionPool connectionPool = new ConnectionPool(maxIdleConnections, keepAliveDuration, TimeUnit.MINUTES);
        return new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory(), x509TrustManager())
                //链接失效时，进行重试
                .retryOnConnectionFailure(true)
                //连接池
                .connectionPool(connectionPool)
                //链接超时时间
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                //读超时时间
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                //写超时时间
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .build();
    }

    public static X509TrustManager x509TrustManager(){
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }
            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    public static SSLSocketFactory sslSocketFactory(){
        try {
            //信任任何链接
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{x509TrustManager()}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

}
