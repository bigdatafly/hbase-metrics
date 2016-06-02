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
	public StringMessage deserialize(String source,String resource, String model, String body) {
		return new StringMessage(source,resource,model,body);
	}

}
