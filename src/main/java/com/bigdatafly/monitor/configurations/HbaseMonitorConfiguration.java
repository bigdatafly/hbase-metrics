package com.bigdatafly.monitor.configurations;

import java.io.File;

import com.bigdatafly.configurations.Configuration;
import com.bigdatafly.configurations.ConfigurationConstants;
import com.bigdatafly.configurations.ConfigurationProvider;
import com.bigdatafly.configurations.PropertiesFileConfigurationProvider;

public class HbaseMonitorConfiguration {

	Configuration conf;
	
	private HbaseMonitorConfiguration(){
		
		File propertyFile = getPropteryFile();
		ConfigurationProvider configurationProvider = new PropertiesFileConfigurationProvider(propertyFile);
		conf = configurationProvider.getConfiguration();
	}
	
	public String getMaster(){
		
		return conf.getString(ConfigurationConstants.MASTER_KEY);
	}

	private File getPropteryFile(){
		
		String file = ConfigurationConstants.PROPERTY_FILE;
		return new File(this.getClass().getResource("").getPath(),file);
	}
	
	public Integer getFrequency(){
		
		return conf.getInteger(ConfigurationConstants.FETCHER_INTERVAL_KEY, 
				ConfigurationConstants.DEFAULT_FETCHER_INTERVAL);
	}
	
	public Integer getMasterJmxPort(){
		
		return conf.getInteger(ConfigurationConstants.MASTER_JMX_PORT_KEY, 
				ConfigurationConstants.DEFAULT_MASTER_JMX_PORT);
	}
	
	public Integer getRegionServerJmxPort(){
		
		return conf.getInteger(ConfigurationConstants.REGION_SERVER_JMX_PORT_KEY, 
				ConfigurationConstants.DEFAULT_REGION_SERVER_JMX_PORT);
	}
	
	public String getMasterJmxQuery(){
		return conf.getString(ConfigurationConstants.MASTER_JMX_QRY);
	}
	
	public String getRegionServerJmxQuery(){
		
		return conf.getString(ConfigurationConstants.REGION_SERVER_JMX_QRY);
	}
	
	public static HbaseMonitorConfiguration builder(){
		return new HbaseMonitorConfiguration();
	}

	@Override
	public String toString() {
		
		return "{configuration:"+this.conf.toString()+"]}";
	}
	
	
}
