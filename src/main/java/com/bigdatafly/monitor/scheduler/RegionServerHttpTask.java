/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.util.StringUtils;

import com.bigdatafly.monitor.exception.PageNotFoundException;
import com.bigdatafly.monitor.http.HbaseJmxQuery;
import com.bigdatafly.monitor.messages.Message;
import com.bigdatafly.monitor.serialization.Deserializer;

/**
 * @author summer
 *
 */
public class RegionServerHttpTask extends AbstractHttpTask{

	public RegionServerHttpTask(String url, Handler handler) {
		super(url, handler);
		
	}

	public RegionServerHttpTask(String url,
			Deserializer<? extends Message> deserializer, Handler handler,
			Callback callback) {
		super(url, deserializer, handler, callback);
		
	}

	public RegionServerHttpTask(String url,
			Deserializer<? extends Message> deserializer, Handler handler) {
		super(url, deserializer, handler);
		
	}

	public RegionServerHttpTask(String url) {
		super(url);
		
	}

	protected String getHtml() throws Exception{
		
		String html = null;
		int size = 0;
		int curr = 0;
		List<String> cli = Collections.unmodifiableList(new ArrayList<String>(servers));
		synchronized(cli){
			size = cli.size();
			while(curr<size){
				String url = getRegionServerJmxQuery(this.url, cli.get(curr++));
				try{
					html = fetcher.fetcher(url);
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
	
	private String getRegionServerJmxQuery(final String url,final String regionServer){
		
		return HbaseJmxQuery.getRegionServerJmxQuery(url, regionServer);
	}

}
