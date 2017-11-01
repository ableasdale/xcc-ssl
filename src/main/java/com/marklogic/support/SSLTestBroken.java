package com.marklogic.support;

import com.marklogic.xcc.*;

import java.util.logging.Logger;

import org.apache.commons.configuration.Configuration;

import javax.net.ssl.*;
import java.net.URI;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class SSLTestBroken {

    private static Configuration config;
    private static SecurityOptions so;
    private static String xccUrl;
    static Logger LOG = Logger.getLogger(SSLTestBroken.class.getName());

    public static void run() throws Exception {
        int errCount = 0;
        try {
            xccUrl = config.getString("SSL_XCC_URI");
            String certLocation = config.getString("JKS_FILE");
            so = newTrustOptions(certLocation);
            so.setEnabledCipherSuites(new String[]{"TLS_RSA_WITH_AES_128_CBC_SHA"});
            so.setEnabledProtocols(new String[]{"TLSv1.2"});

            ContentSource cs = ContentSourceFactory.newContentSource(new URI(xccUrl), so);
            Session session = cs.newSession();
            AdhocQuery request = session.newAdhocQuery("xdmp:request-timestamp()");
            ResultSequence result = session.submitRequest(request);
            result.close();
            session.close();
            cs.getConnectionProvider().shutdown(null);
        } catch (Throwable t) {
            t.printStackTrace();
            errCount++;
        }
    }

    public static TrustManager[] getTrust(final KeyManager[] key) throws Exception {
        TrustManager[] trust = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                String alias = "replserver";
                //LOG.info("getAcceptedIssures");
                for (KeyManager k : key) {
                    if (k instanceof X509KeyManager) {
                        X509Certificate c[] = ((X509KeyManager) k).getCertificateChain(alias);
                        LOG.info("Found issuers:" + (c == null ? "null" : String.valueOf(c.length)));
                        return c;
                    }
                }
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                for (X509Certificate c : certs)
                    LOG.info("checking trusted client cert of type: " + authType + "\n");
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                for (X509Certificate c : certs) {
                    LOG.info("checking trusted server cert:type: " + authType + "\n");
                }
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
        KeyStore clientKeyStore = KeyStore.getInstance("JKS");
        clientKeyStore.load(SSLTestBroken.class.getResourceAsStream(certLocation), config.getString("KEY_PASSWD").toCharArray());
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(clientKeyStore, config.getString("KEY_PASSWD").toCharArray());
        KeyManager[] key = keyManagerFactory.getKeyManagers();
        SSLContext sslContext = SSLContext.getInstance("SSLv3");
        sslContext.init(key, getTrust(key), (SecureRandom) null);
        return new SecurityOptions(sslContext);
    }
}
