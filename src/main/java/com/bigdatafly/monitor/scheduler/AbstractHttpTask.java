/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import java.util.Set;

import com.bigdatafly.monitor.http.Fetcher;
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

	public AbstractHttpTask(String url,Deserializer deserializer,Handler handler) {
		this(url,deserializer,handler,null);
		
	}
	
	public AbstractHttpTask(String url,Deserializer deserializer,Handler handler, Callback callback) {
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

	protected  String getHtml(String url) throws Exception{
		return fetcher.fetcher(this.url);
	}
	
	public Set<String> getServers() {
		return servers;
	}

	public void setServers(Set<String> servers) {
		this.servers = servers;
	}


	

}
