/**
 * 
 */
package com.bigdatafly.monitor.serialization;

import java.util.Collection;

import com.bigdatafly.monitor.messages.Message;


/**
 * @author summer
 *
 */
public interface Deserializer {

	/**
	 * @param source serverName hbase:servername storm:storm 或者 servername
	 * @param resource  如: hbase 1 storm 2
	 * @param model model json message的 MODEL_TAG
	 * @param  body json message <监控项 ,值>
	 */
	public Collection<? extends Message> deserialize(String source,String resource,String model,String body); 
}
