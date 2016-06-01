/**
 * 
 */
package com.bigdatafly.monitor.hbase;

/**
 * @author summer
 * servername-yyyymmddHHmmss
 */
public class RowkeyGenerator {

	public static final String MASTER_MONITOR_TYPE="master";
	public static final String REGION_SERVER_MONITOR_TYPE = "regionserver";
	
	
	public String rowkey(){
		
		return monitorType+""+timeStamp;
	}
	
	public String monitorType;
	private String timeStamp;
	
	public RowkeyGenerator setMonitorType(String monitorType){
		this.monitorType = monitorType;
		return this;
	}
	
	public RowkeyGenerator setTimestamp(String timeStamp){
		this.timeStamp = timeStamp;
		return this;
	}
	
	private RowkeyGenerator(){
		
	}
	
	public static RowkeyGenerator builder(){
		return new RowkeyGenerator();
	}
}
