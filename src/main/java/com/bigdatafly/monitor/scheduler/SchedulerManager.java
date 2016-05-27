/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.bigdatafly.monitor.configurations.SchedulerConfiguration;

/**
 * @author summer
 *
 */
public class SchedulerManager {

	private static final Map<String,Task> tasks = new ConcurrentHashMap<String,Task>();
	public static List<String> regionServerList = new ArrayList<String>();
	private String master;
	private SchedulerConfiguration config;
	private static final Executor exector = Executors.newCachedThreadPool();
	
	protected SchedulerManager(SchedulerConfiguration config){
		
		this.config = config;
	}
	
	public void configurate(){
		
	}
	
	public void refreshRegionServer(){
		
		//regionServerList
	}
	
	
	
	protected void registry(final String url,final Task task){
		
		Task currentTask = tasks.remove(url);
		if(currentTask!=null && currentTask.getState() == State.RUNNING){
			
		}else{
		
			exector.execute(task);
			tasks.put(url, task);
		}
	}
	
	public void unregistry(String url){
		
		Task currentTask = tasks.remove(url);
		if(currentTask!=null && currentTask.getState() == State.RUNNING){
			currentTask.stop();
			currentTask.waitFor();
		}
	}
	
	public void start(){
		
	}
	
	public void shutdown(){
		
	}
}
