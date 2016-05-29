/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.bigdatafly.monitor.http.HbaseJmxQuery;

/**
 * @author summer
 *
 */
public class SchedulerManagerTest {

	SchedulerManager schedulerManager = new SchedulerManager();
	HbaseJmxQuery hbaseJmxQuery = schedulerManager.getHbaseJmxQuery();
	private static final ExecutorService executor = Executors.newCachedThreadPool();
	
	private Task createMasterTask(String masterJmxQuery){
		return schedulerManager.createMasterTask(masterJmxQuery);
	}
	
	@Test
	public void masterAndRegionServerTaskTest() throws InterruptedException{
		
		String masterJmxQuery = hbaseJmxQuery.getMasterJmxQuery();
		Task masterTask = createMasterTask(masterJmxQuery);
		String regionServerJmxQuery = this.hbaseJmxQuery.getRegionServerJmxQuery();
		Task regionServer = schedulerManager.createRegionServerTask(regionServerJmxQuery);
		executor.execute(masterTask);
		executor.execute(regionServer);
		
		
		executor.awaitTermination(10, TimeUnit.MINUTES);
	}
	
}
