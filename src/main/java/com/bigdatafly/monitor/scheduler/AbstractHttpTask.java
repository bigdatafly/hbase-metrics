/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import java.util.Set;

import org.springframework.util.StringUtils;

import com.bigdatafly.monitor.http.Fetcher;
import com.bigdatafly.monitor.messages.Message;
import com.bigdatafly.monitor.serialization.Deserializer;
/**
 * @author summer
 *
 */
public abstract class AbstractHttpTask extends AbstractTask implements Task{

	protected String url;
	protected Fetcher fetcher;
	protected Set<String> servers;
	protected Object lock = new Object();
	
	public AbstractHttpTask(String url){
		
		this(url,null);
	}
	
	public AbstractHttpTask(String url,Handler handler){
		
		this(url,null,handler);
		
	}

	public AbstractHttpTask(String url,Deserializer<? extends Message> deserializer,Handler handler) {
		this(url,deserializer,handler,null);
		
	}
	
	public AbstractHttpTask(String url,Deserializer<? extends Message> deserializer,Handler handler, Callback callback) {
		super(deserializer, handler ,callback);
		this.url = url;
	}


	@Override
	public void start() {
		super.start();
		this.fetcher = Fetcher.create();
	}

	@Override
	public void stop() {
		super.stop();
		this.fetcher.close();
	}

	@Override
	protected Message poll() throws Exception {
		
		String html = getHtml();
		if(this.deserializer!=null && !StringUtils.isEmpty(html))
			return deserializer.deserialize(this.url,this,html);
		return null;
	}
	
	protected abstract String getHtml() throws Exception;
	
	public Set<String> getServers() {
		return servers;
	}

	public void setServers(Set<String> servers) {
		this.servers = servers;
	}


	

}
