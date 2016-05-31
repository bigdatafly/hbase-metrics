/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdatafly.monitor.configurations.HbaseMonitorConfiguration;
import com.bigdatafly.monitor.exception.HbaseMonitorException;
import com.bigdatafly.monitor.hbase.JmxQueryConstants;
import com.bigdatafly.monitor.hbase.MonitorDataOperator;
import com.bigdatafly.monitor.hbase.ServerNodeOperator;
import com.bigdatafly.monitor.hbase.model.Beans;
import com.bigdatafly.monitor.http.HbaseJmxQuery;
import com.bigdatafly.monitor.messages.Message;
import com.bigdatafly.monitor.messages.MessageParser;
import com.bigdatafly.monitor.messages.StringMessage;
import com.bigdatafly.monitor.serialization.StringDeserializer;

/**
 * @author summer
 *
 */
public class SchedulerManager {

	private static final Logger logger = LoggerFactory.getLogger(SchedulerManager.class);
	private static final Map<String,Task> tasks = new ConcurrentHashMap<String,Task>();
	public static Set<String> regionServerList = new HashSet<String>();
	private HbaseMonitorConfiguration config;
	private HbaseJmxQuery hbaseJmxQuery;
	private String master;
	private int requency;
	private static final ExecutorService executor = Executors.newCachedThreadPool();
	private Builder builder;
	private volatile boolean started;
	private MonitorDataOperator hbaseOperator;
	private ServerNodeOperator serverNodeOperator;
	
	public void start() throws HbaseMonitorException{
		
		if(!started){
			String masterQuery  = this.hbaseJmxQuery.getMasterJmxQuery();
			registry(masterQuery,createMasterTask(masterQuery));
			String regionServerJmxQuery = this.hbaseJmxQuery.getRegionServerJmxQuery();
			registry(regionServerJmxQuery,createRegionServerTask(regionServerJmxQuery));
			started = true;
			if(logger.isDebugEnabled())
				logger.debug("{debug} master "+this.master+" will be started");
		}else
			throw new HbaseMonitorException("Scheduler already was started");
	}
	
	public Task createRegionServerTask(String regionServerJmxQuery){
		
		Task task = builder
				.isMasterTask(false)
				.setUrl(regionServerJmxQuery)
				.setCallback(new RegionServerCallback())
				.setServers(regionServerList)
				.setSerializer(new StringDeserializer())
				.setInterval(requency)
				.setHandler(new Handler(){
					@Override
					public <T extends Message> void handler(T msg) {
						if(msg instanceof StringMessage){
							StringMessage stringMsg = (StringMessage)msg;
							String serverName = stringMsg.getResource();
							String body = stringMsg.getBody();
							Beans beans = MessageParser.jmxMessageParse(body);
							Map<String,Object> performancesIndex = MessageParser.getParamters(beans, JmxQueryConstants.REGION_SERVER_PERFORMANCES_INDEX);
							try {
								hbaseOperator.put(serverName,performancesIndex);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(logger.isDebugEnabled()){
								System.out.println("--------------regionserver("+serverName+") begin---------------------");
								System.out.println(body);
								System.out.println("--------------regionserver("+serverName+") end------------------------");
								logger.debug(stringMsg.toString());
							}
						}						
					}					
				})
				.create();
		return task;
	}
	
	public Task createMasterTask(String masterJmxQuery){
		Task task = builder
				.isMasterTask(true)
				.setUrl(masterJmxQuery)
				.setCallback(new MasterCallback(this))
				.setServers(regionServerList)
				.setSerializer(new StringDeserializer())
				.setInterval(requency)
				.setHandler(new Handler(){
					@Override
					public <T extends Message> void handler(T msg) {
						if(msg instanceof StringMessage){
							StringMessage stringMsg = (StringMessage)msg;
							String body = stringMsg.getBody();
							Beans beans = MessageParser.jmxMessageParse(body);
							Map<String,Object> performancesIndex = MessageParser.getParamters(beans, JmxQueryConstants.MASTER_PERFORMANCES_INDEX);
							try {
								hbaseOperator.put(master,performancesIndex);
								//serverNodeOperator.put(master, node);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(logger.isDebugEnabled()){
								System.out.println("--------------master begin---------------------");
								System.out.println(body);
								System.out.println("--------------master end------------------------");
								logger.debug(stringMsg.toString());
							}
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
		
		this.config = config;
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
		
		this.hbaseOperator = new MonitorDataOperator(this.config);
		
		this.serverNodeOperator = new ServerNodeOperator(this.config);
		
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
	
	public void await(){
		
		while(true){
			if(executor.isShutdown())
				break;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
			}
		}
		
		/*
		try {
			executor.awaitTermination(1000, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			
			if(logger.isDebugEnabled())
				logger.debug("InterruptedException:",e);
		}*/
	}
	
	public void shutdown(){
		
		executor.shutdown();
		started = false;
		
	}
	
	public HbaseJmxQuery getHbaseJmxQuery(){
		return this.hbaseJmxQuery;
	}

	public ServerNodeOperator getServerNodeOperator() {
		
		return this.serverNodeOperator;
	}
}
