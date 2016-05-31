/**
 * 
 */
package com.bigdatafly.monitor.serialization;

import com.bigdatafly.monitor.messages.Message;

/**
 * @author summer
 *
 */
public interface Deserializer<T extends Message> {

	public T deserialize(Object source,String resource,Object target,String body); 
}
