/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import com.bigdatafly.monitor.messages.Message;
import com.bigdatafly.monitor.serialization.Deserializer;

/**
 * @author summer
 *
 */
public interface Task extends Runnable{

	public void start();
	
	public void execute();
	
	public Task setHandler(Handler hander);
	
	public Task setInterval(int interval);
	
	public Task setDeserializer(Deserializer<? extends Message> serializer );

	public void stop();

	public void waitFor();

	public State getState();
	
	
}
