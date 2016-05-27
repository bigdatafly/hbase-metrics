/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import com.bigdatafly.monitor.messages.Message;

/**
 * @author summer
 *
 */
public interface Handler {

	public <T extends Message> void handler(T msg);
}
