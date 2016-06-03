/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import org.junit.Test;

import com.bigdatafly.monitor.exception.HbaseMonitorException;
import com.bigdatafly.monitor.http.MonitorHttpServer;

/**
 * @author summer
 *
 */
public class SchedulerManagerTest {

	@Test
	public void schedulerManagerTest() throws HbaseMonitorException{
		
		SchedulerManager schedulerManager = new SchedulerManager();
		schedulerManager.start();
		MonitorHttpServer httpServer = new MonitorHttpServer();
		httpServer.start();
		//httpServer.await();
		//httpServer.stop();
		schedulerManager.await();
	}
}
