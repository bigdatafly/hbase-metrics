/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import java.util.Collections;
import java.util.List;

import org.springframework.util.StringUtils;

import com.bigdatafly.monitor.exception.PageNotFoundException;
import com.bigdatafly.monitor.http.Fetcher;
import com.bigdatafly.monitor.messages.Message;
import com.bigdatafly.monitor.serialization.Serializer;
/**
 * @author summer
 *
 */
public class HttpTask extends AbstractTask implements Task{

	String url;
	Fetcher fetcher;
	List<String> servers;
	Object lock = new Object();
	
	public HttpTask(String url){
		
		this(url,null);
	}
	
	public HttpTask(String url,Handler handler){
		
		this(url,null,handler);
		
	}

	public HttpTask(String url,Serializer<? extends Message> serializer,Handler handler) {
		this(url,serializer,handler,null);
		
	}
	
	public HttpTask(String url,Serializer<? extends Message> serializer,Handler handler, Callback callback) {
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
	
	protected String getHtml() throws Exception{
		
		String html = null;
		int size = 0;
		int curr = 0;
		List<String> cli = Collections.unmodifiableList(servers);
		synchronized(cli){
			size = cli.size();
			while(true){
				this.url = cli.get(curr++);
				try{
					html = fetcher.fetcher(this.url);
					if(!StringUtils.isEmpty(html))
						break;
				}catch(PageNotFoundException ex){
					continue;
				}
				
			}
			
			if(curr == size)
				throw new PageNotFoundException();
		}
		
		return html;
	}
	
	
	public List<String> getServers() {
		return servers;
	}

	public void setServers(List<String> servers) {
		this.servers = servers;
	}


	public static class Builder{
		
		Handler handler;
		Serializer<? extends Message> serializer;
		Callback callback;
		String url;
		List<String> servers;
		
		private Builder(){
			
		}
		
		public static Builder builder(){
			
			return new Builder();
		}
		
		public Builder setServers(List<String> servers) {
			this.servers = servers;
			return this;
		}

		public Builder setHandler(Handler handler) {
			this.handler = handler;
			return this;
		}
		
		public Builder setSerializer(Serializer<? extends Message> serializer) {
			this.serializer = serializer;
			return this;
		}

		public Builder setCallback(Callback callback) {
			this.callback = callback;
			return this;
		}

		public Builder setUrl(String url) {
			this.url = url;
			return this;
		}

		public Task create(){
			HttpTask task = new HttpTask(url,serializer,handler,callback);
			task.setServers(servers);
			task.start();
			return task;
		}
		
	}

}
