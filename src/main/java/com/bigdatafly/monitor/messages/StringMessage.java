/**
 * 
 */
package com.bigdatafly.monitor.messages;

import java.util.Map;

import com.bigdatafly.utils.DateUtils;
import com.google.common.collect.Maps;

/**
 * @author summer
 *
 */
public class StringMessage implements Message{

	private String body;
	//private Object model;//存放工程名称
	//private Object source;//存放 servername
	//private String resource;//存放URL
	//private String timestamp;
	
	Map<String,String> header;
	
	public StringMessage(String body){
		
		this("","",body);
	} 

	public StringMessage(String source,String resource,String body){
		this(source, resource,null,body);
	}
	
	public StringMessage(String source,String resource,String model,String body){
		
		this.header = Maps.newHashMap();
		this.header.put(ProtocolConstants.PROTOCOL_HEADER_SERVER_NODE_NAME, source);
		this.header.put(ProtocolConstants.PROTOCOL_HEADER_MODEL_NAME, model);
		this.header.put(ProtocolConstants.PROTOCOL_HEADER_RESOURCE, resource);
		this.header.put(ProtocolConstants.PROTOCOL_HEADER_TIME_STAMP, DateUtils.getTimestamp());
		this.body = body;

	}

	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}




	public String getModel() {
		
		return this.header.get(ProtocolConstants.PROTOCOL_HEADER_MODEL_NAME);
	}

	
	public void setModel(String model) {
		this.header.put(ProtocolConstants.PROTOCOL_HEADER_MODEL_NAME, model);
	}

	public String getSource() {
		
		return this.header.get(ProtocolConstants.PROTOCOL_HEADER_SERVER_NODE_NAME);
	}
	
	public void setSource(String source) {
		this.header.put(ProtocolConstants.PROTOCOL_HEADER_SERVER_NODE_NAME, source);
		
	}

	public void setResource(String resource) {
		
		this.header.put(ProtocolConstants.PROTOCOL_HEADER_RESOURCE, resource);
		
	}



	public String getResource() {
		
		return this.header.get(ProtocolConstants.PROTOCOL_HEADER_RESOURCE);
	}


	public String getTimestamp() {
		
		return this.header.get(ProtocolConstants.PROTOCOL_HEADER_TIME_STAMP);
	}

	@Override
	public Map<String, String> getHeader() {
		
		return this.header;
	}
	
	@Override
	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("timestamp:"+getTimestamp()+",");
		sb.append("source:"+getSource()+",");
		sb.append("resource:"+getResource()+",");
		sb.append("model:"+getModel()+",");
		sb.append("body:"+getBody());
		sb.append("}");
		return sb.toString();
	}
	
}
