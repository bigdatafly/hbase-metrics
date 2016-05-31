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
public class MonitorItemOperator extends HbaseOperator{

	public static final String DEFAULT_TABLE_NAME = "hbase_monitor_item";
	public static final String DEFAULT_COLUMN_FAMILY = "micf";
	
	public MonitorItemOperator(HbaseMonitorConfiguration conf) {
		super(conf);
		
	}
	
	public void put(Map<String, Object> values) throws IOException {
		
		super.put(DEFAULT_TABLE_NAME, DEFAULT_COLUMN_FAMILY, values);
	}
	
	public void createTable() {
		super.createTable(DEFAULT_TABLE_NAME, DEFAULT_COLUMN_FAMILY);
	}

	public void dropTable() {
		
		super.dropTable(DEFAULT_TABLE_NAME);
	}

	@Override
	protected String rowkeyGenerator(String serverName, String key) {
		
		return key;
	}

}
