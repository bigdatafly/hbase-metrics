package com.bigdatafly.monitor.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

import com.bigdatafly.monitor.messages.Message;
import com.bigdatafly.monitor.messages.StringMessage;
import com.bigdatafly.monitor.scheduler.Handler;

public class HbaseHandlers implements Handler{

	@Autowired
	HbaseTemplate hbaseTemplate;

	@Override
	public <T extends Message> void handler(T msg) {
		
		if(msg instanceof StringMessage){
			StringMessage strMessage = (StringMessage)msg;
			
			//hbaseTemplate.
		}
		
	}

	 
}
