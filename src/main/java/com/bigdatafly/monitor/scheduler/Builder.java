/**
 * 
 */
package com.bigdatafly.monitor.scheduler;


import java.util.Set;

import com.bigdatafly.monitor.messages.Message;
import com.bigdatafly.monitor.serialization.Deserializer;

/**
 * @author summer
 *
 */
public class Builder{
	
	private Handler handler;
	private Deserializer<? extends Message> deserializer;
	private Callback callback;
	private String url;
	private Set<String> servers;
	private int interval;
	private boolean masterTask;
	
	private Builder(){
		
		masterTask = false;
	}
	
	public static Builder builder(){
		
		return new Builder();
	}
	
	public Builder isMasterTask(boolean master){
		
		this.masterTask = master;
		return this;
	}
	
	public Builder setServers(Set<String> servers) {
		this.servers = servers;
		return this;
	}

	public Builder setInterval(int interval){
		this.interval = interval;
		return this;
	}
	
	public Builder setHandler(Handler handler) {
		this.handler = handler;
		return this;
	}
	
	public Builder setSerializer(Deserializer<? extends Message> deserializer) {
		this.deserializer = deserializer;
		return this;
	}

	public Builder setCallback(Callback callback) {
		this.callback = callback;
		return this;
	}

	public Builder setUrl(String url) {
		this.url = url;
		return this;
	}

	public Task create(){
		AbstractHttpTask task ;
		if(masterTask){
			task = new MasterHttpTask(url,deserializer,handler,callback);
		}else{
			task = new RegionServerHttpTask(url,deserializer,handler,callback);
		}
		task.setServers(servers);
		task.setInterval(interval);
		task.start();
		return task;
	}
	
}
