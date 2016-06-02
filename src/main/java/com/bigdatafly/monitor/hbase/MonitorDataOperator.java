/**
 * 
 */
package com.bigdatafly.monitor.hbase;

import java.util.List;
import java.util.Map;

import com.bigdatafly.monitor.configurations.HbaseMonitorConfiguration;
import com.bigdatafly.monitor.messages.ProtocolConstants;
import com.google.common.collect.Maps;

/**
 * @author summer
 *
 */
public class MonitorDataOperator extends HbaseOperator{

	
	public static final String DEFAULT_TABLE_NAME = "hbase_monitor_data";
	public static final String DEFAULT_COLUMN_FAMILY = "mdcf";
	MonitorItemOperator monitorItemOperator;
	
	public MonitorDataOperator(HbaseMonitorConfiguration conf){
		
		this(conf,null);
	}
	
	public MonitorDataOperator(HbaseMonitorConfiguration conf,MonitorItemOperator monitorItemOperator){
		
		super(conf);
		this.monitorItemOperator = ((monitorItemOperator == null)?new MonitorItemOperator(conf):monitorItemOperator);
		
	}
	
	public void put(List<Map<String,Object>> values){
	
		Map<String,Map<String,Object>> vals = convert(values);
		super.put(DEFAULT_TABLE_NAME, DEFAULT_COLUMN_FAMILY, vals);
	}

	public void createTable() {
		super.createTable(DEFAULT_TABLE_NAME, DEFAULT_COLUMN_FAMILY);
	}

	public void dropTable(){
		super.dropTable(DEFAULT_TABLE_NAME);
	}

	@Override
	protected Map<String,Map<String,Object>> convert(List<Map<String, Object>> protocolData) {
		
		Map<String,Map<String,Object>> monitorData = Maps.newHashMap();
		
		for(Map<String,Object> datas:protocolData){
			//去掉协议头
			String timestamp = datas.remove(ProtocolConstants.PROTOCOL_HEADER_TIME_STAMP).toString();
			String modeName  = datas.remove(ProtocolConstants.PROTOCOL_HEADER_MODEL_NAME).toString();
			String serverNode  = datas.remove(ProtocolConstants.PROTOCOL_HEADER_SERVER_NODE_NAME).toString();
			String urlresource  = datas.remove(ProtocolConstants.PROTOCOL_HEADER_RESOURCE).toString();
			//转码
			serverNode = getMonitorItem(modeName,serverNode);
			for(Map.Entry<String,Object> e:datas.entrySet()){
				
				String attribute = e.getKey();
				Object value = e.getValue();
				String key = rowkeyGenerator(getMonitorItem(modeName,attribute),timestamp);
				if(monitorData.containsKey(key))
					monitorData.get(key).put(serverNode, value);
				else{
					Map<String,Object> cf = Maps.newHashMap();
					cf.put(serverNode,value);
					monitorData.put(key, cf);
				}
			}	
		}
		
		return monitorData;
	}
	
	//还有问题
	private String getMonitorItem(String mode,String key){
		
		return monitorItemOperator.getMonitorItem(mode, key);
	}
	
	protected String rowkeyGenerator(String key,String timestamp) {
	
		return key+timestamp;	
	}
	
}
