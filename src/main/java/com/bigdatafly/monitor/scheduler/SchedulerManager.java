/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdatafly.monitor.configurations.HbaseMonitorConfiguration;
import com.bigdatafly.monitor.exception.HbaseMonitorException;
import com.bigdatafly.monitor.http.HbaseJmxQuery;
import com.bigdatafly.monitor.messages.Message;
import com.bigdatafly.monitor.messages.StringMessage;
import com.bigdatafly.monitor.serialization.StringSerializer;

/**
 * @author summer
 *
 */
public class SchedulerManager {

	private static final Logger logger = LoggerFactory.getLogger(SchedulerManager.class);
	private static final Map<String,Task> tasks = new ConcurrentHashMap<String,Task>();
	public static List<String> regionServerList = new ArrayList<String>();
	//private HbaseMonitorConfiguration config;
	private HbaseJmxQuery hbaseJmxQuery;
	private String master;
	private int requency;
	private static final ExecutorService executor = Executors.newCachedThreadPool();
	private Builder builder;
	private volatile boolean started;
	
	public void start() throws HbaseMonitorException{
		
		if(!started){
			String masterQuery  = this.hbaseJmxQuery.getMasterJmxQuery();
			Task task = createMasterTask();
			registry(masterQuery,task);
			started = true;
			if(logger.isDebugEnabled())
				logger.debug("{debug} master "+this.master+" will be started");
		}else
			throw new HbaseMonitorException("Scheduler already was started");
	}
	
	private Task createMasterTask(){
		Task task = builder
				.isMasterTask(true)
				.setCallback(new MasterCallback())
				.setServers(regionServerList)
				.setSerializer(new StringSerializer())
				.setInterval(requency)
				.setHandler(new Handler(){
					@Override
					public <T extends Message> void handler(T msg) {
						if(msg instanceof StringMessage){
							
						}						
					}					
				})
				.create();
		return task;
	}
	
	public SchedulerManager(){
		
		this(HbaseMonitorConfiguration.builder());
	}
	
	protected SchedulerManager(HbaseMonitorConfiguration config){
		
		//this.config = config;
		this.hbaseJmxQuery =   HbaseJmxQuery.Builder
											.builder()
											.setConfiguration(config)
											.builderQuery();
		
		this.master = this.hbaseJmxQuery.getMaster();
		//this.masterJmxPort = this.hbaseJmxQueryUrl.getMasterJmxPort();
		//this.regionServerJmxPort = this.config.getRegionServerJmxPort();
		this.requency = this.hbaseJmxQuery.getFrequency();
		//this.masterJmxQry = this.config.getMasterJmxQuery();
		//this.regionServerJmxQry = this.config.getRegionServerJmxQuery();

		this.builder = Builder.builder();
		
		if(logger.isDebugEnabled())
			logger.debug(config.toString());
	}
	
	protected void registry(final String url,final Task task) throws HbaseMonitorException{
		
		Task currentTask = tasks.remove(url);
		if(currentTask!=null && currentTask.getState() == State.RUNNING){
			throw new HbaseMonitorException("Task "+url+" was already  running!");
		}else{
		
			executor.execute(task);
			tasks.put(url, task);
		}
	}
	
	protected void unregistry(String url){
		
		Task currentTask = tasks.remove(url);
		if(currentTask!=null && currentTask.getState() == State.RUNNING){
			currentTask.stop();
			currentTask.waitFor();
		}
		regionServerList.remove(url);
	}
	
	public void shutdown(){
		
		executor.shutdown();
		try {
			executor.awaitTermination(1000, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			
			if(logger.isDebugEnabled())
				logger.debug("InterruptedException:",e);
		}
		started = false;
		
	}
}
