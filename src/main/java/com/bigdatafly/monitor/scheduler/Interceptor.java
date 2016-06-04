/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import com.bigdatafly.monitor.messages.Message;

/**
 * @author summer
 *
 */
public interface Interceptor {

	public Message intercept(Message msg);
	
}
