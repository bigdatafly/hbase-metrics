/**
 * 
 */
package com.bigdatafly.configurations;

/**
 * @author summer
 *
 */
public class ConfigurationConstants {

	public static final String PROPERTY_FILE = "/hbase.properties";
	
	public static final String MASTER_KEY = "master";
	
	public static final String FETCHER_INTERVAL_KEY = "fetcher.interval";
	
	public static final int DEFAULT_FETCHER_INTERVAL = 60;//(s)
	
	public static final String MASTER_JMX_PORT_KEY = "master.jmx.port";
	
	public static final int DEFAULT_MASTER_JMX_PORT = 60030;
	
	public static final String REGION_SERVER_JMX_PORT_KEY= "regionserver.jmx.port";
	
	public static final int DEFAULT_REGION_SERVER_JMX_PORT = 16301;
	
	public static final String MASTER_JMX_QRY="master.jmx.qry";
	
	public static final String REGION_SERVER_JMX_QRY="regionserver.jmx.qry";
	
	

}
