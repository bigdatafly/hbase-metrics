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
public class MasterHttpTask extends AbstractHttpTask{

	public MasterHttpTask(String url, Handler handler) {
		super(url, handler);
		// TODO Auto-generated constructor stub
	}

	public MasterHttpTask(String url, Deserializer<? extends Message> serializer,
			Handler handler, Callback callback) {
		super(url, serializer, handler, callback);
		// TODO Auto-generated constructor stub
	}

	public MasterHttpTask(String url, Deserializer<? extends Message> serializer,
			Handler handler) {
		super(url, serializer, handler);
		// TODO Auto-generated constructor stub
	}

	public MasterHttpTask(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}

	public String getHtml() throws Exception{
		
		return fetcher.fetcher(this.url);
	}
	

}
