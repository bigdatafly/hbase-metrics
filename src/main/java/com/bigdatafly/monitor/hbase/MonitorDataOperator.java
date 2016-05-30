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

	HbaseMonitorConfiguration conf;
	public static final String DEFAULT_TABLE_NAME = "hbase_monitor_data";
	public static final String DEFAULT_COLUMN_FAMILY = "mdf";
	
	public MonitorDataOperator(HbaseMonitorConfiguration conf){
		
		this.conf = conf;
		this.hbaseConfig = createHBaseConfiguration(conf.getHbaseZookeeperHost(), conf.getHbaseZookeeperPort());
	}
	
	public void put(Map<String,Object> values) throws IOException{
		
		super.put(DEFAULT_TABLE_NAME, RowkeyGenerator(), DEFAULT_COLUMN_FAMILY, values);
	}

	public void createTable() {
		super.createTable(DEFAULT_TABLE_NAME, DEFAULT_COLUMN_FAMILY);
	}
	
	@Override
	protected String RowkeyGenerator() {
		
		return rowkeyGenerator
				.setMonitorType(RowkeyGenerator.MASTER_MONITOR_TYPE)
				.setServerName(conf.getMaster())
				.rowkey();
	}
	
}
