/**
 * 
 */
package com.zgxcw.monitor.scheduler;

//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.bigdatafly.monitor.scheduler.SchedulerManager;

/**
 * @author summer
 *
 */
@Service
public class SchedulerManagerBean implements InitializingBean,DisposableBean{

	SchedulerManager schedulerManager;
	
	//@PreDestroy
	@Override
	public void destroy() throws Exception {
		
		schedulerManager.shutdown();
	}

	
	//@PostConstruct
	@Override
	public void afterPropertiesSet() throws Exception {
		
		if(schedulerManager == null)
			schedulerManager = new SchedulerManager();
		schedulerManager.start();
	}

}
