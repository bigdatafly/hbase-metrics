/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import java.util.HashSet;
import java.util.Set;

import com.bigdatafly.monitor.hbase.model.Beans;
import com.bigdatafly.monitor.messages.Message;
import com.bigdatafly.monitor.messages.MessageParser;
import com.bigdatafly.monitor.messages.StringMessage;
import com.google.common.collect.Sets;

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
			Beans beans = MessageParser.jmxMessageParse(body);
			synchronized(SchedulerManager.regionServerList){
				Set<String> tempResionServers = new HashSet<>();
				tempResionServers.addAll(SchedulerManager.regionServerList);
				Set<String> newRegionServers = Sets.newHashSet(MessageParser.getRegionServerFromJmxMessage(beans));
				try{
					SchedulerManager.regionServerList.clear();
					//newRegionServers.removeAll(SchedulerManager.regionServerList);
					SchedulerManager.regionServerList.addAll(newRegionServers);
				}catch(Exception ex){
					SchedulerManager.regionServerList.clear();
					SchedulerManager.regionServerList.addAll(tempResionServers);
				}
			}
			
		}						
		
	}

	
}
