/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import java.util.List;

import org.springframework.util.StringUtils;

import com.bigdatafly.monitor.http.Fetcher;
import com.bigdatafly.monitor.messages.Message;
import com.bigdatafly.monitor.serialization.Serializer;
/**
 * @author summer
 *
 */
public abstract class AbstractHttpTask extends AbstractTask implements Task{

	protected String url;
	protected Fetcher fetcher;
	protected List<String> servers;
	protected Object lock = new Object();
	
	public AbstractHttpTask(String url){
		
		this(url,null);
	}
	
	public AbstractHttpTask(String url,Handler handler){
		
		this(url,null,handler);
		
	}

	public AbstractHttpTask(String url,Serializer<? extends Message> serializer,Handler handler) {
		this(url,serializer,handler,null);
		
	}
	
	public AbstractHttpTask(String url,Serializer<? extends Message> serializer,Handler handler, Callback callback) {
		super(serializer, handler ,callback);
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
		if(this.serializer!=null && !StringUtils.isEmpty(html))
			return serializer.serialize(html);
		return null;
	}
	
	protected abstract String getHtml() throws Exception;
	
	public List<String> getServers() {
		return servers;
	}

	public void setServers(List<String> servers) {
		this.servers = servers;
	}


	

}
