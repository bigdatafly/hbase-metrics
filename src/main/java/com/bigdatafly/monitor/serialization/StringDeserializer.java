/**
 * 
 */
package com.bigdatafly.monitor.serialization;

import com.bigdatafly.monitor.messages.StringMessage;

/**
 * @author summer
 *
 */
public class StringDeserializer implements Deserializer<StringMessage>{

	
	@Override
	public StringMessage deserialize(Object source,String resource, Object target, String body) {
		return new StringMessage(source,resource,target,body);
	}

}
