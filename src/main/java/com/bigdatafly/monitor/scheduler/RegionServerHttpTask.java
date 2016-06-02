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
import com.bigdatafly.monitor.messages.ProtocolConstants;
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
			Deserializer deserializer, Handler handler,
			Callback callback) {
		super(url, deserializer, handler, callback);
		
	}

	public RegionServerHttpTask(String url,
			Deserializer deserializer, Handler handler) {
		super(url, deserializer, handler);
		
	}

	public RegionServerHttpTask(String url) {
		super(url);
		
	}

	@Override
	protected List<Message> poll() throws Exception{
		
		List<Message> msgs = new ArrayList<Message>();
		
		String html = null;
		int size = 0;
		int curr = 0;
		List<String> cli = Collections.unmodifiableList(new ArrayList<String>(servers));
		synchronized(cli){
			size = cli.size();
			while(curr<size){
				String regionServer = cli.get(curr++);
				String url = getRegionServerJmxQuery(this.url, regionServer);
				try{
					html = fetcher.fetcher(url);
					if(this.deserializer!=null && !StringUtils.isEmpty(html))
						msgs.addAll(deserializer.deserialize(regionServer,ProtocolConstants.PROTOCOL_HEADER_MODEL_HBASE,"",html));
				}catch(PageNotFoundException ex){
					/*
					if(curr == size)
						throw ex;
						*/
				}
				
			}	
		}
			
		return msgs;
	}

	private String getRegionServerJmxQuery(final String url,final String regionServer){
		
		return HbaseJmxQuery.getRegionServerJmxQuery(url, regionServer);
	}

}
