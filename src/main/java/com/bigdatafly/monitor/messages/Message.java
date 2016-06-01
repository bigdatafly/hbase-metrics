/**
 * 
 */
package com.bigdatafly.monitor.messages;

/**
 * @author summer
 *
 */
public interface Message {

	Object getSource();
	
	Object getTarget();
	
	String getResource();
	
	String getBody();
	
	String getId();
}
