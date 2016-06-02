package com.bigdatafly.monitor.scheduler;

import com.bigdatafly.monitor.messages.Message;

public class DefaultInterceptor implements Interceptor{

	@Override
	public Message intercept(Message msg) {
		
		return msg;
	}

}
