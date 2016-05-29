package com.bigdatafly.monitor.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdatafly.monitor.messages.Message;

public class DefaultCallback implements Callback {

	static final Logger logger = LoggerFactory.getLogger(DefaultCallback.class);
	@Override
	public void onMessage(Object target, Message message) {

		if(logger.isDebugEnabled())
			logger.debug(message.toString());

	}

	@Override
	public void onError(Object target, Throwable cause) {
		
			logger.error("{target:"+target+"}",cause);

	}

}
