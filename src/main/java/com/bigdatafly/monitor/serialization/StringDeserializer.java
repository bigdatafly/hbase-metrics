/**
 * 
 */
package com.bigdatafly.monitor.serialization;

import java.util.List;
import java.util.Map;

import org.apache.storm.shade.com.google.common.collect.Lists;

import com.bigdatafly.monitor.http.ProtocolConstants;
import com.bigdatafly.monitor.messages.MessageParser;
import com.bigdatafly.monitor.messages.StringMessage;

/**
 * @author summer
 *
 */
public class StringDeserializer implements Deserializer{

	//public static final String 
	/**
	 * @param source serverName hbase:servername storm:storm 或者 servername
	 * @param resource  如: hbase 1 storm 2
	 * @param model model json message的 MODEL_TAG
	 * @param  body json message <监控项 ,值>
	 */
	@Override
	public List<StringMessage> deserialize(String source,String resource, String model, String body) {
		String strmodel = model;
		String strresource = resource;
		String strsource = source;
		List<Map<String,Object>> map = MessageParser.jmxMessageParse2(body);
		List<StringMessage> messages = Lists.newArrayList();
		if(map!=null){
			for(Map<String,Object> messageBody:map){
				if(messageBody.containsKey(ProtocolConstants.MODEL_TAG)){
					strmodel = messageBody.remove(ProtocolConstants.MODEL_TAG).toString();
				}
				
				if(messageBody.containsKey(ProtocolConstants.RESOURCE_TAG)){
					strresource = messageBody.remove(ProtocolConstants.RESOURCE_TAG).toString();
				}
				
				if(messageBody.containsKey(ProtocolConstants.SOURCE_TAG)){
					strsource = messageBody.remove(ProtocolConstants.SOURCE_TAG).toString();
				}
				messages.add(new StringMessage(strsource,strresource,strmodel,messageBody));
			}
		}
		return messages;
	}

}
