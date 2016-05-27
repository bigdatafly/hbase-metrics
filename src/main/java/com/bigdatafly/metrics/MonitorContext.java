package com.bigdatafly.metrics;

import java.io.IOException;

import org.apache.hadoop.metrics.spi.AbstractMetricsContext;
import org.apache.hadoop.metrics.spi.OutputRecord;

public class MonitorContext extends AbstractMetricsContext{

	@Override
	protected void emitRecord(String contextName, String recordName, OutputRecord outRec) throws IOException {
		// TODO Auto-generated method stub
		
	}

	
}
