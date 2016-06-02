/**
 * 
 */
package com.bigdatafly.monitor.messages;

import java.util.Map;

/**
 * @author summer
 *
 */
public interface Message {

	//Object getSource();
	
	//Object getTarget();
	
	//String getResource();
	
	String getBody();
	
	//String getId();
	Map<String,String> getHeader(); 
}
