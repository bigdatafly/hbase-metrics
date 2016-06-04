package com.bigdatafly.monitor.configurations;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.bigdatafly.configurations.Configuration;
import com.bigdatafly.configurations.ConfigurationConstants;
import com.bigdatafly.configurations.ConfigurationProvider;
import com.bigdatafly.configurations.PropertiesFileConfigurationProvider;

public class HbaseMonitorConfiguration {

	Configuration conf;
	
	private HbaseMonitorConfiguration(File propertyFile){
		
		//File propertyFile = getPropteryFile();
		ConfigurationProvider configurationProvider = new PropertiesFileConfigurationProvider(propertyFile);
		conf = configurationProvider.getConfiguration();
	}
	
	public String getMaster(){
		
		return conf.getString(ConfigurationConstants.MASTER_KEY);
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
	
	public static class Builder{
		private File propertyFile;
		static HbaseMonitorConfiguration _self = null;
		static Lock lock = new ReentrantLock();
		
		private Builder(){
			
		}
		
		public static Builder builder(){
			return new Builder();
		}
		public Builder setPropertyFile(File propertyFile){
			this.propertyFile = propertyFile;
			return this;
		}
		
		public static HbaseMonitorConfiguration newSingletonConfiguration(final File propertyFile){
			
			if(_self == null){
				lock.lock();
				try{
					if(_self == null)
						_self = new HbaseMonitorConfiguration(propertyFile);				
				}finally{
					lock.unlock();
				}
			}
			
			return _self;
		}
		
		public  HbaseMonitorConfiguration create(){
			
			if(propertyFile ==null)
				propertyFile =  getPropteryFile();
			return new HbaseMonitorConfiguration(propertyFile);
		}
		
		private File getPropteryFile(){
			
			String filePath = this.getClass().getResource("/").getFile() ;
			try {
				filePath = URLDecoder.decode(filePath,"utf-8");
			} catch (UnsupportedEncodingException e) {
				
			}
			return new File(filePath,ConfigurationConstants.PROPERTY_FILE);
		}
		
	}
	
}
