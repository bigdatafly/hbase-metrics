/**
 * 
 */
package com.bigdatafly.configuration;

import org.junit.Assert;

import org.junit.Test;

import com.bigdatafly.monitor.configurations.HbaseMonitorConfiguration;

/**
 * @author summer
 *
 */
public class HbaseMonitorConfigurationTest {

	HbaseMonitorConfiguration conf = HbaseMonitorConfiguration.builder();
	
	@Test
	public void configurationTest(){
		
		String expected = "h103";
		String actual = conf.getMaster();
		Assert.assertEquals(expected,actual);
	}
}
