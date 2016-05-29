/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import org.junit.Test;

import com.bigdatafly.monitor.http.HbaseJmxQuery;

/**
 * @author summer
 *
 */
public class SchedulerManagerTest {

	SchedulerManager schedulerManager = new SchedulerManager();
	
	@Test
	public void masterTaskTest() throws InterruptedException{
		HbaseJmxQuery hbaseJmxQuery = schedulerManager.getHbaseJmxQuery();
		String masterJmxQuery = hbaseJmxQuery.getMasterJmxQuery();
		Task masterTask = schedulerManager.createMasterTask(masterJmxQuery);
		Thread thread = new Thread(masterTask);
		thread.start();
		thread.join();
	}
	@Test
	public void regionServerTaskTest(){
		
	}
}
