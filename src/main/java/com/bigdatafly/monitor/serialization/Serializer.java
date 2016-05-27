/**
 * 
 */
package com.bigdatafly.monitor.serialization;

import com.bigdatafly.monitor.messages.Message;

/**
 * @author summer
 *
 */
public interface Serializer<T extends Message> {

	public T serialize(String message); 
}
