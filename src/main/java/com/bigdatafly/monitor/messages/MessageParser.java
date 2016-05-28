/**
 * 
 */
package com.bigdatafly.monitor.messages;

import com.bigdatafly.monitor.hbase.model.Beans;
import com.bigdatafly.utils.JsonUtils;

/**
 * @author summer
 *
 */
public class MessageParser {

	public static Beans JmxMessageParser(final String json){
		
		return JsonUtils.fromJson(json, Beans.class);
	}

	
}
