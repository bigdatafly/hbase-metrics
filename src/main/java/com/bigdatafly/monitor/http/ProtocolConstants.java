/**
 * 
 */
package com.bigdatafly.monitor.http;

import org.apache.commons.lang.StringUtils;

/**
 * @author summer
 *
 */
public class ProtocolConstants {

	/*
	 * 协议格式
	 * 
	 * 协议头
	 *PROTOCOL_HEADER_MODEL_NAME   		工程模块 
	 * PROTOCOL_HEADER_SERVER_NODE_NAME 服务器节点名称
	 * PROTOCOL_HEADER_TIME_STAMP		ts
	 * 
	 * 内容
	 * body
	 */
	public static final String MODEL_TAG = "name";
	public static final String SOURCE_TAG = "source";
	public static final String RESOURCE_TAG = "resource";

	//public static final String PROTOCOL_HEADER_PROJECT_NAME="projectName"; //目前就2种
	public static final String PROTOCOL_HEADER_MODEL_NAME="modeName";
	public static final String PROTOCOL_HEADER_SERVER_NODE_NAME = "serverNodeName";
	public static final String PROTOCOL_HEADER_TIME_STAMP = "timeStamp";
	public static final String PROTOCOL_HEADER_RESOURCE = "urlresource";
	
	public static final String PROTOCOL_HEADER_MODEL_UNKNOWN_CODE = "0";
	public static final String PROTOCOL_HEADER_MODEL_HBASE_CODE = "1";
	public static final String PROTOCOL_HEADER_MODEL_STORM_CODE = "2";
	
	public static final String PROTOCOL_HEADER_MODEL_HBASE="hbase";
	public static final String PROTOCOL_HEADER_MODEL_STORM="storm";
	
	public static final String MASTER_MONITOR_TYPE="1";
	public static final String REGION_SERVER_MONITOR_TYPE="2";
	public static final String REGION_MONITOR_TYPE="3";
	public static final String JVM_MONITOR_TYPE="4";
	
	public static final String getModelCode(String model){
		
		String modelCode = PROTOCOL_HEADER_MODEL_UNKNOWN_CODE;
		if(!StringUtils.isEmpty(model)){
			if(PROTOCOL_HEADER_MODEL_HBASE.equals(model))
				modelCode = PROTOCOL_HEADER_MODEL_HBASE_CODE;
			if(PROTOCOL_HEADER_MODEL_STORM.equals(model))
				modelCode = PROTOCOL_HEADER_MODEL_STORM_CODE;
		}
		return modelCode;	
	}
	
}
