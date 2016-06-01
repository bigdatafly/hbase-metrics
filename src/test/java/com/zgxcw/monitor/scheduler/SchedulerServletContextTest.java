/**
 * 
 */
package com.zgxcw.monitor.scheduler;

import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdatafly.monitor.exception.HbaseMonitorException;
import com.bigdatafly.monitor.scheduler.SchedulerManager;

/**
 * @author summer
 *
 */
public class SchedulerServletContextTest implements ServletContextListener{

	SchedulerManager schedulerManager = null;
	private static final Logger logger = LoggerFactory.getLogger(SchedulerServletContext.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		if(schedulerManager!=null){
			schedulerManager.shutdown();
			schedulerManager.await(3, TimeUnit.SECONDS);
			logger.debug("{start}");
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		if(schedulerManager == null)
			schedulerManager = new SchedulerManager();
		try {
			schedulerManager.start();
		} catch (HbaseMonitorException e) {
			logger.error("scheduler started failed",e);
			//e.printStackTrace();
		}
	}



}
