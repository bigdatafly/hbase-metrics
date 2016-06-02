package com.bigdatafly.monitor.scheduler;

import java.util.Map;
import java.util.Set;

import com.bigdatafly.monitor.messages.Message;

public class IncludeInterceptor extends DefaultInterceptor{

	private Set<String> attributes;
	
	public IncludeInterceptor(Set<String> attributes) {
		
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
