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
	public static final Map<String,String> PERFORMANCES_MAP = Maps.newHashMap();
	
	public MonitorItemOperator(HbaseMonitorConfiguration conf) {
		super(conf);
		
	}
	
	public void put(String rowkey,String value) throws IOException {
		
		super.put(DEFAULT_TABLE_NAME, DEFAULT_COLUMN_FAMILY, rowkey,rowkey,value);
	}
	
	public void createTable() {
		super.createTable(DEFAULT_TABLE_NAME, DEFAULT_COLUMN_FAMILY);
	}

	public void dropTable() {
		
		super.dropTable(DEFAULT_TABLE_NAME);
	}

	public String getMonitorItem(String mode,String key){
		String itemKey = mode+"$"+key;
		String itemVal = "0000";
		long val = 0;
		if(PERFORMANCES_MAP.containsKey(itemKey))
			itemVal =  PERFORMANCES_MAP.get(itemKey);
		else{
			try {
				val = super.increment(DEFAULT_TABLE_NAME,DEFAULT_COLUMN_FAMILY, "incrzhuxh","incr",1);
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
	
}
