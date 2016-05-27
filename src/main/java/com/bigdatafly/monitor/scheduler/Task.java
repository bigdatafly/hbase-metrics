/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import com.bigdatafly.monitor.messages.Message;
import com.bigdatafly.monitor.serialization.Serializer;

/**
 * @author summer
 *
 */
public interface Task extends Runnable{

	public void start();
	
	public void execute();
	
	public Task setHandler(Handler hander);
	
	public Task setInterval(int interval);
	
	public Task setSerializer(Serializer<? extends Message> serializer );

	public void stop();

	public void waitFor();

	public State getState();
	
	
}
