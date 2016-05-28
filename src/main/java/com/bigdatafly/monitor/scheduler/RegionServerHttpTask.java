/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import java.util.Collections;
import java.util.List;

import org.springframework.util.StringUtils;

import com.bigdatafly.monitor.exception.PageNotFoundException;
import com.bigdatafly.monitor.messages.Message;
import com.bigdatafly.monitor.serialization.Serializer;

/**
 * @author summer
 *
 */
public class RegionServerHttpTask extends AbstractHttpTask{

	public RegionServerHttpTask(String url, Handler handler) {
		super(url, handler);
		// TODO Auto-generated constructor stub
	}

	public RegionServerHttpTask(String url,
			Serializer<? extends Message> serializer, Handler handler,
			Callback callback) {
		super(url, serializer, handler, callback);
		// TODO Auto-generated constructor stub
	}

	public RegionServerHttpTask(String url,
			Serializer<? extends Message> serializer, Handler handler) {
		super(url, serializer, handler);
		// TODO Auto-generated constructor stub
	}

	public RegionServerHttpTask(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}

	protected String getHtml() throws Exception{
		
		String html = null;
		int size = 0;
		int curr = 0;
		List<String> cli = Collections.unmodifiableList(servers);
		synchronized(cli){
			size = cli.size();
			while(curr<size){
				this.url = cli.get(curr++);
				try{
					html = fetcher.fetcher(this.url);
					if(!StringUtils.isEmpty(html))
						break;
				}catch(PageNotFoundException ex){
					if(curr == size)
						throw ex;
				}
				
			}	
		}
		
		return html;
	}

}
