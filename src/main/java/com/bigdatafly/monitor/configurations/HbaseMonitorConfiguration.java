package com.bigdatafly.monitor.configurations;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.bigdatafly.configurations.Configuration;
import com.bigdatafly.configurations.ConfigurationConstants;
import com.bigdatafly.configurations.ConfigurationProvider;
import com.bigdatafly.configurations.PropertiesFileConfigurationProvider;

public class HbaseMonitorConfiguration {

	Configuration conf;
	
	static HbaseMonitorConfiguration _self;
	
	private HbaseMonitorConfiguration(){
		
		File propertyFile = getPropteryFile();
		ConfigurationProvider configurationProvider = new PropertiesFileConfigurationProvider(propertyFile);
		conf = configurationProvider.getConfiguration();
	}
	
	public String getMaster(){
		
		return conf.getString(ConfigurationConstants.MASTER_KEY);
	}

	private File getPropteryFile(){
		
		String filePath = this.getClass().getResource("/").getFile() ;
		try {
			filePath = URLDecoder.decode(filePath,"utf-8");
		} catch (UnsupportedEncodingException e) {
			
		}
		return new File(filePath,ConfigurationConstants.PROPERTY_FILE);
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
	
	

	public String getHbaseZookeeperHost(){
		return conf.getString(ConfigurationConstants.ZOOKEEPER_HOST_KEY);
	}
	
	public int getHbaseZookeeperPort(){
		return conf.getInteger(ConfigurationConstants.ZOOKEEPER_PORT_KEY);
	}
	
	/**
	 * 
	 * http server configuration
	 */
	
	public int getHttpServerPort(){
		return conf.getInteger(ConfigurationConstants.HTTP_SERVER_PORT_KEY,ConfigurationConstants.DEFAULT_HTTP_SERVER_PORT);
	}
	
	
	public int getHttpServerMaxRequestNum(){
		return conf.getInteger(ConfigurationConstants.HTTP_MAX_REQUEST_NUM_KEY,ConfigurationConstants.DEFAULT_HTTP_MAX_REQUEST_NUM);
	}
	
	public String getHttpServerContextPath(){
		return conf.getString(ConfigurationConstants.HTTP_SERVER_REQ_PATH_KEY,ConfigurationConstants.DEFAULT_HTTP_SERVER_REQ_PATH);
	}
	
	public Configuration getConfiguration() {
		return conf;
	}

	@Override
	public String toString() {
		
		return "{configuration:"+this.conf.toString()+"]}";
	}
	
	public static synchronized HbaseMonitorConfiguration builder(){
		
		if(_self ==null)
			_self = new HbaseMonitorConfiguration();
		return _self;
	}
}
