/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.bigdatafly.monitor.hbase.MonitorItemOperator;
import com.bigdatafly.monitor.hbase.ServerNodeOperator;
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
	//ServerNodeOperator serverNodeOperator;
	//MonitorItemOperator monitoritemOperator;
	/**
	 * 
	 */
	public MasterCallback(SchedulerManager parent) {
		this.parent = parent;
		//this.serverNodeOperator = parent.getServerNodeOperator();
		//this.monitoritemOperator = parent.getMonitorItemOperator();
	}

	@Override
	public void onMessage(Object target, Message message) {
		if(message instanceof StringMessage){
			StringMessage stringMsg = (StringMessage)message;
			//String body = stringMsg.getBody();
			//Beans beans = MessageParser.jmxMessageParse(body);
			Map<String,Object> body = stringMsg.getBody();
			synchronized(SchedulerManager.regionServerList){
				Set<String> tempResionServers = new HashSet<String>();
				tempResionServers.addAll(SchedulerManager.regionServerList);
				Set<String> newLiveRegionServers = Sets.newHashSet(MessageParser.getLiveRegionServerFromJmxMessage(body));
				if(newLiveRegionServers.isEmpty())
					return;
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
			//refreshRegionServer(beans);
		}						
		
	}
	
//	protected void refreshRegionServer(Map<String,Object> beans){
//		
//		Set<String> newLiveRegionServers = Sets.newHashSet(MessageParser.getLiveRegionServerFromJmxMessage(beans));
//		
//		for(String serverName : newLiveRegionServers){
//			ServerNode node = new ServerNode();
//			node.setServerName(serverName);
//			node.setMonitorType(ServerNode.SLAVE_MONITOR_TYPE);
//			node.setOnline("1");
//			try {
//				serverNodeOperator.put(node);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		Set<String> newDeadRegionServers = Sets.newHashSet(MessageParser.getDeadRegionServerFromJmxMessage(beans));
//		
//		for(String serverName : newDeadRegionServers){
//			ServerNode node = new ServerNode();
//			node.setServerName(serverName);
//			node.setMonitorType(ServerNode.SLAVE_MONITOR_TYPE);
//			node.setOnline("0");
//			try {
//				serverNodeOperator.put(node);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}

	
}
