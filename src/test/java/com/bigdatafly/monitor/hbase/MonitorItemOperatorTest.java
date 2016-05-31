/**
 * 
 */
package com.bigdatafly.monitor.hbase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.bigdatafly.monitor.configurations.HbaseMonitorConfiguration;

/**
 * @author summer
 *
 */
public class MonitorItemOperatorTest {

	MonitorItemOperator hbaseOperator;
	HbaseMonitorConfiguration conf; 
	
	@Before
	public void initialize(){
		conf = HbaseMonitorConfiguration.builder();
		hbaseOperator = new MonitorItemOperator(conf);
	}
	
	
	public void createTableTest(){
		hbaseOperator.createTable();
	}
	
 
	public void dropTableTest(){
		hbaseOperator.dropTable();
	}
	
	@Test 
	public void insertMonitorItems(){
		
		dropTableTest();
		createTableTest();
		
		Map<String,Object> monitorItems = new HashMap<>();
		for(Map.Entry<String, String> e : JmxQueryConstants.PERFORMANCES_MAP.entrySet()){
			monitorItems.put(e.getKey(), e.getValue());
		}
		try {
			hbaseOperator.put(monitorItems);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
