package com.bigdatafly.monitor.scheduler;

import java.util.List;
import java.util.Set;

import org.apache.storm.shade.com.google.common.collect.Sets;

import com.bigdatafly.monitor.messages.Message;
import com.google.common.collect.Lists;

public abstract class AbstractHandler implements Handler{

	protected List<Interceptor> interceptors = Lists.newArrayList();
	
	protected Interceptor createDefaultInterceptor(){
		Set<String> attributes = Sets.newHashSet();
		attributes.add("modelerType");
		//attributes.addAll(JmxQueryConstants.MASTER_PERFORMANCES_INDEX);
		//attributes.addAll(JmxQueryConstants.REGION_SERVER_PERFORMANCES_INDEX);
		return new ExcludeInterceptor(attributes);
	}

	@Override
	public  void handler(Message msg) {
		
		for(Interceptor interceptor:interceptors){
			if(interceptor!=null)
				msg = interceptor.intercept(msg);
		}
		
		doHandler(msg);
	}

	protected abstract void doHandler(Message msg);
	
	@Override
	public Handler setInterceptors(List<Interceptor> interceptors) {
		
		interceptors.addAll(interceptors);
		return this;
	}

	@Override
	public Handler addInterceptor(Interceptor interceptor) {
		
		interceptors.add(interceptor);
		return this;
	}

}
