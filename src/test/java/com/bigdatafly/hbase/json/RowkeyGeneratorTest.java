/**
 * 
 */
package com.bigdatafly.hbase.json;

import org.junit.Test;

import com.bigdatafly.monitor.hbase.RowkeyGenerator;

/**
 * @author summer
 *
 */
public class RowkeyGeneratorTest {

	@Test
	public void timestampTest(){
		
		System.out.println(RowkeyGenerator.getTimestamp());
	}
}
