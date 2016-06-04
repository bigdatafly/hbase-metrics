/**
 * 
 */
package com.bigdatafly.monitor.hbase;

import org.junit.After;
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
		conf = HbaseMonitorConfiguration.Builder.builder().create();
		hbaseOperator = new MonitorDataOperator(conf);
	}
	
	@After
	public void createTableTest(){
		hbaseOperator.createTable();
	}
	
	@Test 
	public void dropTableTest(){
		hbaseOperator.dropTable();
	}
}
