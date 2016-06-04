/**
 * 
 */
package com.bigdatafly.monitor.configurations;

import org.junit.Test;

import com.bigdatafly.configurations.Configuration;

/**
 * @author summer
 *
 */
public class HbaseMonitorConfigurationTest {

	HbaseMonitorConfiguration config = HbaseMonitorConfiguration.Builder.builder().create();
	@Test
	public void hbaseMonitorConfigurationTest(){
		
		Configuration conf = config.getConfiguration();
		System.out.println(conf);
	}

}
