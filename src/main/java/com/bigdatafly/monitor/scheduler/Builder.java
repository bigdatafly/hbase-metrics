/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import java.util.List;

import com.bigdatafly.monitor.messages.Message;
import com.bigdatafly.monitor.serialization.Serializer;

/**
 * @author summer
 *
 */
public class Builder{
	
	private Handler handler;
	private Serializer<? extends Message> serializer;
	private Callback callback;
	private String url;
	private List<String> servers;
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
	
	public Builder setServers(List<String> servers) {
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
	
	public Builder setSerializer(Serializer<? extends Message> serializer) {
		this.serializer = serializer;
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
			task = new MasterHttpTask(url,serializer,handler,callback);
		}else{
			task = new RegionServerHttpTask(url,serializer,handler,callback);
		}
		task.setServers(servers);
		task.setInterval(interval);
		task.start();
		return task;
	}
	
}
