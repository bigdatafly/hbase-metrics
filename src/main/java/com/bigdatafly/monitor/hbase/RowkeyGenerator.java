/**
 * 
 */
package com.bigdatafly.monitor.hbase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author summer
 * servername-yyyymmddHHmmss
 */
public class RowkeyGenerator {

	public static final String MASTER_MONITOR_TYPE="master";
	public static final String REGION_SERVER_MONITOR_TYPE = "regionserver";
	
	
	public String rowkey(){
		
		return serverName+"$"+monitorType+""+getTimestamp();
	}
	
	private String serverName;
	
	public String monitorType;
	
	
	public RowkeyGenerator setServerName(String serverName){
		this.serverName = serverName;
		return this;
	}
	
	public RowkeyGenerator setMonitorType(String monitorType){
		this.monitorType = monitorType;
		return this;
	}
	
	private static String getTimestamp(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddHHmm");
		return sdf.format(new Date());
	}
	
	private RowkeyGenerator(){
		
	}
	
	public static RowkeyGenerator builder(){
		return new RowkeyGenerator();
	}
}
