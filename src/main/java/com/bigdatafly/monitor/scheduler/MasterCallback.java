/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.bigdatafly.monitor.hbase.ServerNodeOperator;
import com.bigdatafly.monitor.hbase.model.Beans;
import com.bigdatafly.monitor.hbase.model.ServerNode;
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
	ServerNodeOperator serverNodeOperator;
	/**
	 * 
	 */
	public MasterCallback(SchedulerManager parent) {
		this.parent = parent;
		this.serverNodeOperator = parent.getServerNodeOperator();
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
				Set<String> newLiveRegionServers = Sets.newHashSet(MessageParser.getLiveRegionServerFromJmxMessage(beans));
				
				try{
					SchedulerManager.regionServerList.clear();
					//newRegionServers.removeAll(SchedulerManager.regionServerList);
					SchedulerManager.regionServerList.addAll(newLiveRegionServers);
				}catch(Exception ex){
					SchedulerManager.regionServerList.clear();
					SchedulerManager.regionServerList.addAll(tempResionServers);
				}
			}
			
			//
			refreshRegionServer(beans);
		}						
		
	}
	
	private void refreshRegionServer(Beans beans){
		
		Set<String> newLiveRegionServers = Sets.newHashSet(MessageParser.getLiveRegionServerFromJmxMessage(beans));
		
		for(String serverName : newLiveRegionServers){
			ServerNode node = new ServerNode();
			node.setServerName(serverName);
			node.setMonitorType(ServerNode.SLAVE_MONITOR_TYPE);
			node.setOnline("1");
			try {
				serverNodeOperator.put(serverName, node);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Set<String> newDeadRegionServers = Sets.newHashSet(MessageParser.getDeadRegionServerFromJmxMessage(beans));
		
		for(String serverName : newDeadRegionServers){
			ServerNode node = new ServerNode();
			node.setServerName(serverName);
			node.setMonitorType(ServerNode.SLAVE_MONITOR_TYPE);
			node.setOnline("0");
			try {
				serverNodeOperator.put(serverName, node);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
}
