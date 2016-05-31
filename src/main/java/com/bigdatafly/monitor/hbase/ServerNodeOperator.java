/**
 * 
 */
package com.bigdatafly.monitor.hbase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.bigdatafly.monitor.configurations.HbaseMonitorConfiguration;
import com.bigdatafly.monitor.hbase.model.ServerNode;

/**
 * @author summer
 *
 */
public class ServerNodeOperator extends HbaseOperator{

	
	public static final String DEFAULT_TABLE_NAME = "hbase_servernode";
	public static final String DEFAULT_COLUMN_FAMILY = "sncf";
	private ServerNode node;
	
	public ServerNodeOperator(HbaseMonitorConfiguration conf) {
		super(conf);
	}

	
	public void put(String serverName,ServerNode node) throws IOException{
		this.node = node;
		Map<String,Object> values = new HashMap<>();
		values.put("serverName", node.getServerName());
		values.put("monitorType", node.getMonitorType());
		values.put("online", node.getOnline());
		super.put(DEFAULT_TABLE_NAME, DEFAULT_COLUMN_FAMILY, values);
	}
	
	@Override
	protected String getServername() {
		
		return node.getServerName();
	}

	@Override
	protected String rowkeyGenerator(String serverName, String key) {
		
		return serverName;
	}


	protected void createTable() {
		
		super.createTable(DEFAULT_TABLE_NAME, DEFAULT_COLUMN_FAMILY);
	}


	protected void dropTable() {
		
		super.dropTable(DEFAULT_TABLE_NAME);
	}
	
	

}
