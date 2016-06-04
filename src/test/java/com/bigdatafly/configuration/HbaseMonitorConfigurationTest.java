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

	HbaseMonitorConfiguration conf = HbaseMonitorConfiguration.Builder.builder().create();
	
	@Test
	public void configurationTest(){
		
		String expected = "master";
		String actual = conf.getMaster();
		Assert.assertEquals(expected,actual);
	}
}
