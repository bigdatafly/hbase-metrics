/**
 * 
 */
package com.bigdatafly.monitor.hbase;

import java.util.Map;

import com.bigdatafly.monitor.configurations.HbaseMonitorConfiguration;

/**
 * @author summer
 *
 */
public class MonitorDataOperator extends HbaseOperator{

	
	public static final String DEFAULT_TABLE_NAME = "hbase_monitor_data";
	public static final String DEFAULT_COLUMN_FAMILY = "mdcf";

	
	public MonitorDataOperator(HbaseMonitorConfiguration conf){
		
		super(conf);
	}
	
	public void put(/*List<Map<String,Object>>*/Map<String,Map<String,Object>> values){
	
		//Map<String,Map<String,Object>> vals = convert(values);
		super.put(DEFAULT_TABLE_NAME, DEFAULT_COLUMN_FAMILY, values);
	}

	public void createTable() {
		super.createTable(DEFAULT_TABLE_NAME, DEFAULT_COLUMN_FAMILY);
	}

	public void dropTable(){
		super.dropTable(DEFAULT_TABLE_NAME);
	}

	
}
