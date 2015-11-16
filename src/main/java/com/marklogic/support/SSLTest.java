package com.marklogic.support;

import com.marklogic.xcc.*;
import org.apache.commons.configuration.Configuration;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.net.URI;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class SSLTest {

    private static Configuration config;
    private static SecurityOptions so;
    private static String xccUrl;
    private static org.slf4j.Logger LOG = LoggerFactory.getLogger("SSLTest");

    public static void run() throws Exception {

        xccUrl = config.getString("SSL_XCC_URI");
        String certLocation = config.getString("JKS_FILE");
        so = newTrustOptions(certLocation);
        so.setEnabledCipherSuites(new String[]{"TLS_RSA_WITH_AES_128_CBC_SHA"});
        so.setEnabledProtocols(new String[]{"TLSv1.2"});

        ContentSource cs = ContentSourceFactory.newContentSource(new URI(xccUrl), so);
        Session session = cs.newSession();
        AdhocQuery request = session.newAdhocQuery("xdmp:request-timestamp()");
        session.submitRequest(request);
        session.close();
    }

    public static TrustManager[] getTrust() throws Exception {
        TrustManager[] trust = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
            }
        }};
        return trust;
    }

    public static void main(String[] args) throws Exception {
        LOG.info("Starting SSL test");
        config = TestHelper.getPropertiesConfiguration();

        long count = 1L;

        while (true) {
            LOG.info("Test " + count);
            run();
            ++count;
        }
    }

    protected static SecurityOptions newTrustOptions(String certLocation) throws Exception {
        KeyStore clientKeyStore = KeyStore.getInstance(config.getString("KEYSTORE_TYPE"));
        clientKeyStore.load(new FileInputStream(certLocation), config.getString("KEY_PASSWD").toCharArray());
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(clientKeyStore, config.getString("KEY_PASSWD").toCharArray());
        KeyManager[] key = keyManagerFactory.getKeyManagers();
        SSLContext sslContext = SSLContext.getInstance("SSLv3");
        sslContext.init(key, getTrust(), (SecureRandom) null);
        return new SecurityOptions(sslContext);
    }
}
