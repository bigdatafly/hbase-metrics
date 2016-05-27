/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import com.bigdatafly.monitor.messages.Message;

/**
 * @author summer
 *
 */
public interface Callback {

	public void onMessage(Object target,Message message);
	
	public void onError(Object target,Throwable cause);
}
