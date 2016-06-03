/**
 * 
 */
package com.bigdatafly.configurations;

/**
 * @author summer
 *
 */
public class ConfigurationConstants {

	public static final String PROPERTY_FILE = "hbase.properties";
	
	public static final String MASTER_KEY = "master";
	
	public static final String FETCHER_INTERVAL_KEY = "fetcher.interval";
	
	public static final int DEFAULT_FETCHER_INTERVAL = 60;//(s)
	
	public static final String MASTER_JMX_PORT_KEY = "master.jmx.port";
	
	public static final int DEFAULT_MASTER_JMX_PORT = 60030;
	
	public static final String REGION_SERVER_JMX_PORT_KEY= "regionserver.jmx.port";
	
	public static final int DEFAULT_REGION_SERVER_JMX_PORT = 16301;
	
	public static final String MASTER_JMX_QRY="master.jmx.qry";
	
	public static final String REGION_SERVER_JMX_QRY="regionserver.jmx.qry";
	
	public static final String ZOOKEEPER_HOST_KEY = "hbase.zk.host";
	
	public static final String ZOOKEEPER_PORT_KEY = "hbase.zk.port";
	
	//#### http server
	
	public static final String HTTP_SERVER_PORT_KEY = "http.server.port";
	
	public static final int    DEFAULT_HTTP_SERVER_PORT = 7080;
	
	public static final String HTTP_MAX_REQUEST_NUM_KEY = "http.server.maxrequestnum";
	
	public static final int   DEFAULT_HTTP_MAX_REQUEST_NUM = 100;
	
	public static final String HTTP_SERVER_REQ_PATH_KEY ="http.server.reqpath";
	
	public static final String DEFAULT_HTTP_SERVER_REQ_PATH = "/metric";
	
	

}
