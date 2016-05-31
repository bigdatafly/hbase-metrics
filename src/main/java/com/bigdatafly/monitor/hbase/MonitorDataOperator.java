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
	private String serverName;
	
	public MonitorDataOperator(HbaseMonitorConfiguration conf){
		
		super(conf);
	}
	
	public void put(String serverName,Map<String,Object> values) throws IOException{
		this.serverName = serverName;
		super.put(DEFAULT_TABLE_NAME, DEFAULT_COLUMN_FAMILY, values);
	}

	public void createTable() {
		super.createTable(DEFAULT_TABLE_NAME, DEFAULT_COLUMN_FAMILY);
	}

	public void dropTable(){
		super.dropTable(DEFAULT_TABLE_NAME);
	}
	@Override
	protected String getServername() {
		
		return this.serverName;
	}

	@Override
	protected String rowkeyGenerator(String serverName, String key) {
	
		return rowkeyGenerator
				.setMonitorType(JmxQueryConstants.getMonitorTypeByItem(key))
				.setServerName(serverName)
				.rowkey();
	}
	
}
