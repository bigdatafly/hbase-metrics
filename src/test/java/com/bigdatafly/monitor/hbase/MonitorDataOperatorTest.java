/**
 * 
 */
package com.bigdatafly.monitor.hbase;

import org.junit.Before;
import org.junit.Test;

import com.bigdatafly.monitor.configurations.HbaseMonitorConfiguration;

/**
 * @author summer
 *
 */
public class MonitorDataOperatorTest {

	MonitorDataOperator hbaseOperator;
	HbaseMonitorConfiguration conf; 
	@Before
	public void initialize(){
		conf = HbaseMonitorConfiguration.builder();
		hbaseOperator = new MonitorDataOperator(conf);
	}
	
	@Test
	public void createTableTest(){
		hbaseOperator.createTable();
	}
	
	@Test 
	public void dropTableTest(){
		hbaseOperator.dropTable();
	}
}
