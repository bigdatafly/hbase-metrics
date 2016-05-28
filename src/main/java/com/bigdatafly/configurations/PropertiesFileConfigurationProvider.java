/**
 * 
 */
package com.bigdatafly.configurations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author summer
 *
 */
public class PropertiesFileConfigurationProvider extends AbstractConfigurationProvider{

	private static final Logger LOGGER = LoggerFactory
		      .getLogger(PropertiesFileConfigurationProvider.class);

	private final File file;
	
	public PropertiesFileConfigurationProvider(File file) {
		
		this.file = file;
	}

	@Override
	public Configuration getConfiguration() {
		
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			Properties properties = new Properties();
		    properties.load(reader);
		    return new Configuration(toMap(properties));
		} catch (IOException ex) {
			LOGGER.error("Unable to load file:" + file
			          + " (I/O failure) - Exception follows.", ex);
		} finally{
			if(reader != null)
				try {
					reader.close();
				} catch (IOException ex) {
					 LOGGER.warn(
				              "Unable to close file reader for file: " + file, ex);
				}
		}
		
		return new Configuration(new HashMap<String,String>());
	}

}
