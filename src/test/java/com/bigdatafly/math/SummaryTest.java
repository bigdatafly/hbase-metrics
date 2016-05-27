package com.bigdatafly.math;

import org.apache.commons.math.stat.descriptive.SummaryStatistics;
import org.apache.hadoop.hbase.client.Get;

public class SummaryTest {

	
	public static void main(String[] args) throws Exception{
		
		SummaryStatistics summary = new SummaryStatistics();
		for(double i = 1;i<11;i++){
			summary.addValue(i/5);
		}
		
		System.out.println(summary.toString());
	}
}
