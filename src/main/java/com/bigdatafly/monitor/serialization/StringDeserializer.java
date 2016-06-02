/**
 * 
 */
package com.bigdatafly.monitor.serialization;

import java.util.List;
import java.util.Map;

import com.bigdatafly.monitor.messages.MessageParser;
import com.bigdatafly.monitor.messages.StringMessage;

/**
 * @author summer
 *
 */
public class StringDeserializer implements Deserializer<StringMessage>{

	public static final String MODEL_TAG = "name";
	//public static final String 
	
	@Override
	public StringMessage deserialize(String source,String resource, String model, String body) {
		List<Map<String,Object>> map = MessageParser.jmxMessageParse2(body);
		return new StringMessage(source,resource,model,body);
	}

}
