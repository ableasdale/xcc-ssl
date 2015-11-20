package com.marklogic.support;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHelper {

	protected static Logger LOG = LoggerFactory.getLogger("TestHelper");

  protected static PropertiesConfiguration getPropertiesConfiguration() {
    String path = "/config.properties";
    PropertiesConfiguration properties = new PropertiesConfiguration();
    InputStream fis;
    try {
      fis = PropertiesConfiguration.class.getResourceAsStream(path);
      try {
        properties.load(fis);
      } catch (ConfigurationException e) {
        LOG.error("Failed to create the configuration object from properties file [" + path + "]", e);
      } finally {
        try {
          fis.close();
        } catch (IOException e) {
          LOG.error("Failed to close the properties file [" + path + "]", e);
        }
      }
    }catch(  Exception e ){
      LOG.error("Could hnot load configuration: " + path  , e);
    }
    return properties;
  }

}
