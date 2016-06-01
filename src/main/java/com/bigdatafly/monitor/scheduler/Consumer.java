/**
 * 
 */
package com.bigdatafly.monitor.scheduler;



import com.bigdatafly.monitor.exception.HbaseMonitorException;
import com.bigdatafly.monitor.messages.Message;

/**
 * @author summer
 *
 */
public interface Consumer {

	public void start();
	
	public void consumer(Message messages) throws HbaseMonitorException;
	
	public void stop();
}
