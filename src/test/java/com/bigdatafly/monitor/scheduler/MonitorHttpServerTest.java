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
public class MonitorHttpServerTest {

	MonitorHttpServer httpServer = new MonitorHttpServer();
	
	@Test
	public void httpMonitorServerTest() throws  HbaseMonitorException{
		
		
		httpServer.start();
		httpServer.await();
		httpServer.stop();
		
	}
}
