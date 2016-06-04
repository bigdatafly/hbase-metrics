/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import java.util.List;

import com.bigdatafly.monitor.messages.Message;

/**
 * @author summer
 *
 */
public interface Handler {

	public void handler(Message message);
	
	public Handler setInterceptors(List<Interceptor> interceptors);
	
	public Handler addInterceptor(Interceptor interceptor);
}
