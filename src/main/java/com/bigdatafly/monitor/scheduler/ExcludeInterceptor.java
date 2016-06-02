/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import java.util.Map;
import java.util.Set;

import com.bigdatafly.monitor.messages.Message;

/**
 * @author summer
 *
 */
public class ExcludeInterceptor extends DefaultInterceptor{

	private Set<String> attributes;
	/**
	 * 
	 */
	public ExcludeInterceptor(Set<String> attributes) {
		this.attributes = attributes;
	}

	@Override
	public Message intercept(Message msg) {
		
		if(attributes!=null){
			Map<String,Object> body = msg.getBody();
			body.keySet().retainAll(attributes);
		}

		return super.intercept(msg);
	}
}
