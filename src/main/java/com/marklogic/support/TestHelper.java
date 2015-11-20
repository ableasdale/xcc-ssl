package com.marklogic.support;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

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
				LOG.error(String.format("Failed to create the configuration object from properties file [%s]", path), e);
			} finally {
				try {
					fis.close();
				} catch (IOException e) {
					LOG.error(String.format("Failed to close the properties file [%s]", path), e);
				}
			}
		} catch (Exception e) {
			LOG.error(String.format("Failed to load properties file [%s]", path), e);
		}
		return properties;
	}

}
