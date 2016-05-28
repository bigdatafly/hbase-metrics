/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import java.util.List;

import com.bigdatafly.monitor.messages.Message;
import com.bigdatafly.monitor.messages.MessageParser;
import com.bigdatafly.monitor.messages.StringMessage;

/**
 * @author summer
 *
 */
public class MasterCallback extends DefaultCallback{

	SchedulerManager parent;
	
	/**
	 * 
	 */
	public MasterCallback(SchedulerManager parent) {
		this.parent = parent;
	}

	@Override
	public void onMessage(Object target, Message message) {
		if(message instanceof StringMessage){
			StringMessage stringMsg = (StringMessage)message;
			String body = stringMsg.getBody();
			List<String> regionServers = MessageParser.regionServerParse(body);
			if(regionServers!=null && !regionServers.isEmpty()){
				
				synchronized(SchedulerManager.regionServerList){
					SchedulerManager.regionServerList.addAll(regionServers);
				}
			}
		}						
		
	}

	
}
