/**
 * 
 */
package com.bigdatafly.spring.configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hadoop.config.common.annotation.EnableAnnotationConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.IntervalTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * @author summer
 *
 */
@Configuration
@EnableAnnotationConfiguration
@EnableScheduling
public class SchedulerConfiguration implements SchedulingConfigurer{

	public static final int DEFAULT_FIXED_RATE = 5;
	
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		
		taskRegistrar.setScheduler(createTaskScheduler());
		taskRegistrar.addFixedRateTask(new IntervalTask(new Runnable(){

			@Override
			public void run() {
				worker();
				
			}
			
		},DEFAULT_FIXED_RATE));
		
		
	}

	@Bean(destroyMethod="shutdown")
	public Executor createTaskScheduler(){
		
		return Executors.newSingleThreadExecutor();
	}
	
	@Bean
	public void worker(){
		
	}
	
	
}
