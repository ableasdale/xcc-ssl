package com.marklogic.support;

import com.marklogic.xcc.*;
import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class SSLTest {

    private static Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static Configuration config;
    private static SecurityOptions so;
    private static String xccUrl;

    public static void run() throws Exception {
        int errCount = 0;
        try {
            xccUrl = config.getString("SSL_XCC_URI");
            LOG.debug("SSL URI: " + xccUrl);
            String certLocation = config.getString("JKS_FILE");
            LOG.debug("Certificate Location is: " + certLocation);
            so = newTrustOptions(certLocation);
            so.setEnabledCipherSuites(new String[]{"TLS_RSA_WITH_AES_128_CBC_SHA"});
            so.setEnabledProtocols(new String[]{"TLSv1.2"});

            ContentSource cs = ContentSourceFactory.newContentSource(new URI(xccUrl), so);
            Session session = cs.newSession();
            AdhocQuery request = session.newAdhocQuery("xdmp:request-timestamp()");
            ResultSequence result = session.submitRequest(request);
            LOG.info("Result: " + result.asString());
            result.close();
            session.close();
            cs.getConnectionProvider().shutdown(null);
        } catch (Throwable t) {
            LOG.error("Something went wrong with the connection: ", t);
            errCount++;
        }
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

    private static SecurityOptions newTrustOptions(String certLocation) throws Exception {
        KeyStore clientKeyStore = KeyStore.getInstance("JKS");
        clientKeyStore.load(new FileInputStream(certLocation), config.getString("KEY_PASSWD").toCharArray());
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(clientKeyStore, config.getString("KEY_PASSWD").toCharArray());
        KeyManager[] key = keyManagerFactory.getKeyManagers();
        SSLContext sslContext = SSLContext.getInstance("SSLv3");
        sslContext.init(key, getTrust(), (SecureRandom) null);
        return new SecurityOptions(sslContext);
    }
}

