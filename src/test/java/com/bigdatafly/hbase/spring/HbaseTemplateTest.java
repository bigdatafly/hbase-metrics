/**
 * 
 */
package com.bigdatafly.hbase.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bigdatafly.spring.configuration.HbaseConfiguration;

/**
 * @author summer
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HbaseConfiguration.class})
public class HbaseTemplateTest {

	@Autowired
	HbaseTemplate hbaseTemplate;
	
	@Test
	public void hbasePutTest(){
		
		System.out.println(hbaseTemplate);
	}
}
