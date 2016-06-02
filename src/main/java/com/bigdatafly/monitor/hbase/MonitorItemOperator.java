/**
 * 
 */
package com.bigdatafly.monitor.hbase;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.bigdatafly.monitor.configurations.HbaseMonitorConfiguration;
import com.bigdatafly.monitor.messages.ProtocolConstants;
import com.google.common.collect.Maps;

/**
 * @author summer
 *
 */
public class MonitorItemOperator extends HbaseOperator{

	public static final String DEFAULT_TABLE_NAME = "hbase_monitor_item";
	public static final String DEFAULT_COLUMN_FAMILY = "micf";
	
	public static final String DEFAULT_INCR_TABLE_NAME = "hbase_incr";
	public static final String DEFAULT_INCR_COLUMN_FAMILY = "incr";
	
	public static final String  DEFAULT_SERVER_NODES_TABLE_NAME = "hbase_server_nodes";
	public static final String  DEFAULT_SERVER_NODES_COLUMN_FAMILY = "sncf";
	
	public static final Map<String,String> PERFORMANCES_MAP = Maps.newHashMap();
	public static final Map<String,String> SERVER_NODES_MAP = Maps.newHashMap();
	
	public MonitorItemOperator(HbaseMonitorConfiguration conf) {
		super(conf);
		
	}
	
	public void put(String rowkey,String value) throws IOException {
		
		put(DEFAULT_TABLE_NAME, DEFAULT_COLUMN_FAMILY, rowkey,rowkey,value);
	}
	
	public void put(String tableName,String family,String rowkey,String value) throws IOException {
		
		super.put(tableName, family, rowkey,rowkey,value);
	}
	
	public void createTable() {
		super.createTable(DEFAULT_TABLE_NAME, DEFAULT_COLUMN_FAMILY);
		super.createTable(DEFAULT_INCR_TABLE_NAME, DEFAULT_INCR_COLUMN_FAMILY);
		super.createTable(DEFAULT_SERVER_NODES_TABLE_NAME, DEFAULT_SERVER_NODES_COLUMN_FAMILY);
	}

	public void dropTable() {
		
		super.dropTable(DEFAULT_TABLE_NAME);
		super.dropTable(DEFAULT_INCR_TABLE_NAME);
		super.dropTable(DEFAULT_SERVER_NODES_TABLE_NAME);
	}

	public String getMonitorItem(String mode,String key){
		String itemKey = mode+"$"+key;
		String itemVal = "0000";
		long val = 0;
		if(PERFORMANCES_MAP.containsKey(itemKey))
			itemVal =  PERFORMANCES_MAP.get(itemKey);
		else{
			try {
				val = super.increment(DEFAULT_INCR_TABLE_NAME,DEFAULT_INCR_COLUMN_FAMILY, "incrzhuxh","incr",1);
				String modelCode = ProtocolConstants.getModelCode(mode);
				itemVal = modelCode+StringUtils.leftPad(String.valueOf(val),3,"0");
				put(itemKey,itemVal);
				PERFORMANCES_MAP.put(itemKey, itemVal);
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
				
			}
		}
		
		return itemVal;
	}

	public String getMonitorServerItem(String mode, String key) {
		
		String itemKey = mode+"$"+key;
		String itemVal = "0000";
		long val = 0;
		if(SERVER_NODES_MAP.containsKey(itemKey))
			itemVal =  SERVER_NODES_MAP.get(itemKey);
		else{
			try {
				val = super.increment(DEFAULT_INCR_TABLE_NAME,DEFAULT_INCR_COLUMN_FAMILY, "incrzhuxh","incr",1);
				String modelCode = ProtocolConstants.getModelCode(mode);
				itemVal = modelCode+StringUtils.leftPad(String.valueOf(val),3,"0");
				put(DEFAULT_SERVER_NODES_TABLE_NAME,DEFAULT_SERVER_NODES_COLUMN_FAMILY,itemKey,itemVal);
				SERVER_NODES_MAP.put(itemKey, itemVal);
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
				
			}
		}
		
		return itemVal;
	}	
	
}
