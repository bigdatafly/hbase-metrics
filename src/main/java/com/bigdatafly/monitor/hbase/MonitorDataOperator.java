/**
 * 
 */
package com.bigdatafly.monitor.hbase;

import java.io.IOException;
import java.util.Map;

import com.bigdatafly.monitor.configurations.HbaseMonitorConfiguration;

/**
 * @author summer
 *
 */
public class MonitorDataOperator extends HbaseOperator{

	
	public static final String DEFAULT_TABLE_NAME = "hbase_monitor_data";
	public static final String DEFAULT_COLUMN_FAMILY = "mdf";
	private String timeStamp;
	
	public MonitorDataOperator(HbaseMonitorConfiguration conf){
		
		super(conf);
	}
	
	public void put(String timeStamp,Map<String,Object> values) throws IOException{
		this.timeStamp = timeStamp;
		super.put(DEFAULT_TABLE_NAME, DEFAULT_COLUMN_FAMILY, values);
	}

	public void createTable() {
		super.createTable(DEFAULT_TABLE_NAME, DEFAULT_COLUMN_FAMILY);
	}

	public void dropTable(){
		super.dropTable(DEFAULT_TABLE_NAME);
	}
	@Override
	protected String getTimestamp() {
		
		return this.timeStamp;
	}

	@Override
	protected String rowkeyGenerator(String timestamp, String key) {
	
		return rowkeyGenerator
				.setMonitorType(key)
				.setTimestamp(timestamp)
				.rowkey();
	}
	
}
