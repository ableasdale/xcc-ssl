package com.marklogic.support;

import com.marklogic.xcc.*;
import com.marklogic.xcc.exceptions.RequestException;
import com.marklogic.xcc.exceptions.XccConfigException;
import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.net.URI;
import java.net.URISyntaxException;

public class SimpleTest {
    private static Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {

        Configuration config = TestHelper.getPropertiesConfiguration();

        int counter = 1;

        URI uri = null;
        try {
            uri = new URI(config.getString("XCC_URI"));
        } catch (URISyntaxException e) {
            LOG.error("Unable to create a valid URI (from XCC_URI in resources/config.properties): ",e);
        }

        for (; ;) {
            ContentSource contentSource = null;
            try {
                contentSource = ContentSourceFactory.newContentSource(uri);
            } catch (XccConfigException e) {
                LOG.error("Unable to create the ContentSource Object: ",e);
            }

            Session session = contentSource.newSession();
            Request request = session.newAdhocQuery("xdmp:request-timestamp()");

            try {
                ResultSequence rs;
                rs = session.submitRequest(request);
                LOG.debug(counter + " : " + rs.asString());
            } catch (RequestException e) {
                e.printStackTrace();
            }

            session.close();
            counter++;
        }

    }

}
