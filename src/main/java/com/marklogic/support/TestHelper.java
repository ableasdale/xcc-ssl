package com.marklogic.support;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHelper {

	protected static Logger LOG = LoggerFactory.getLogger("TestHelper");

	protected static PropertiesConfiguration getPropertiesConfiguration() {
		String path = "src/main/resources/config.properties";
		PropertiesConfiguration properties = new PropertiesConfiguration();
		FileInputStream fis;
		try {
			fis = new FileInputStream(path);
			try {
				properties.load(fis);
			} catch (ConfigurationException e) {
				LOG.debug("Failed to create the configuration object from properties file [" + path + "]", e);
			} finally {
				try {
					fis.close();
				} catch (IOException e) {
					LOG.debug("Failed to close the properties file [" + path + "]", e);
				}
			}
		} catch (FileNotFoundException e) {
			LOG.debug("Failed to load properties file [" + path + "]", e);
		}
		return properties;
	}

}
