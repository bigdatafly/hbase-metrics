/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import org.junit.Test;

import com.bigdatafly.monitor.exception.HbaseMonitorException;

/**
 * @author summer
 *
 */
public class SchedulerManagerTest {

	@Test
	public void schedulerManagerTest() throws HbaseMonitorException{
		
		SchedulerManager schedulerManager = new SchedulerManager();
		schedulerManager.start();
		schedulerManager.await();
	}
}
