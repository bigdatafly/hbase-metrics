/**
 * 
 */
package com.bigdatafly.monitor.serialization;

import com.bigdatafly.monitor.messages.StringMessage;

/**
 * @author summer
 *
 */
public class StringSerializer implements Serializer<StringMessage>{

	@Override
	public StringMessage serialize(String message) {
		
		return new StringMessage(message);
	}

}
