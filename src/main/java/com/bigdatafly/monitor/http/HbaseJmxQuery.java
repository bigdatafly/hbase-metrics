/**
 * 
 */
package com.bigdatafly.monitor.http;

import com.bigdatafly.monitor.configurations.HbaseMonitorConfiguration;

/**
 * @author summer
 *
 */
public class HbaseJmxQuery {

	
	public static final String MASTER_DELIMITER = "${master}";
	public static final String REGION_SERVER_DELIMITER = "${regionserver}";
	public static final String PORT_DELIMITER  = "${port}";
	
	private HbaseMonitorConfiguration config;
	private String master;
	private int masterJmxPort;
	private int regionServerJmxPort;
	private int requency;
	private String masterJmxQry;
	private String regionServerJmxQry;
	
	protected HbaseJmxQuery(HbaseMonitorConfiguration config){
		
		this.config = config;
		this.master = this.config.getMaster();
		this.masterJmxPort = this.config.getMasterJmxPort();
		this.regionServerJmxPort = this.config.getRegionServerJmxPort();
		this.requency = this.config.getFrequency();
		this.masterJmxQry = this.config.getMasterJmxQuery();
		this.regionServerJmxQry = this.config.getRegionServerJmxQuery();
	}
	
	public int getFrequency(){
		return this.requency;
	}
	
	public String getMasterJmxQuery(){
		
		String masterUrl  = masterJmxQry
				.replace(MASTER_DELIMITER, master)
				.replace(PORT_DELIMITER, String.valueOf(masterJmxPort));
		return masterUrl;
	}

	
	public String getRegionServerJmxQuery(){
		String regionServerUrl = regionServerJmxQry
				.replace(PORT_DELIMITER, String.valueOf(regionServerJmxPort));
		return regionServerUrl;
	}

	public static String getRegionServerJmxQuery(String oldJmxQuery,String regionServer){
		
		return oldJmxQuery.replace(REGION_SERVER_DELIMITER, regionServer);
	}
	
	public String getMaster() {
		
		return this.master;
	}
	
	public static class Builder{
		
		private HbaseMonitorConfiguration config;
		private Builder(){
			
		}
		
		public static Builder builder(){
			return new Builder();
		}
		
		public Builder setConfiguration(HbaseMonitorConfiguration config){
			this.config = config;
			return this;
		}
		
		public HbaseJmxQuery builderQuery(){
			return new HbaseJmxQuery(config);
		}
	}
}
