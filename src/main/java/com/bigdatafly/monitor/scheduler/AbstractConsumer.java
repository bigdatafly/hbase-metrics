/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import java.util.Deque;
import java.util.Map;

import org.apache.storm.shade.com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdatafly.monitor.exception.HbaseMonitorException;
import com.bigdatafly.monitor.messages.Message;
import com.bigdatafly.monitor.mq.MessageQueue;
import com.google.common.collect.Lists;

/**
 * @author summer
 *
 */
public abstract class AbstractConsumer<T extends Message> implements Consumer{

	private static final Logger logger = LoggerFactory.getLogger(AbstractConsumer.class);
	
	protected Thread innerThread;
	protected volatile boolean stop;
	//private MonitorDataOperator hbaseOperator;
	//private Map<String,Message> mq;
	protected boolean failDiscard = true;
	//private int batchSize;  不能入库
	//private static final int DEFAULT_BATCH_SIZE = 1;
	
	public AbstractConsumer(){
		this(true);	
	}
	
	public AbstractConsumer(boolean failDiscard){
		
		this.stop = false;
		this.failDiscard = failDiscard;
		//this.mq = mq;	
		//this.interceptors.add(createDefaultInterceptor());
	}
	
	public void start(){
		innerThread = new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					if(stop || innerThread.isInterrupted())
						break;
					if(!MessageQueue.mq.isEmpty()){
						synchronized(MessageQueue.mq){
							Deque<Message> discardMessages = Lists.newLinkedList();
							while(true){
								Message value = MessageQueue.poll();
								if(value == null)
									break;
								else{
									try {
										if(logger.isDebugEnabled())
											logger.debug(value.toString());
										consumer(value);
									} catch (HbaseMonitorException e) {
										discardMessages.addFirst(value);
									}
								}
							
							}//while
						
							if(!failDiscard){
								MessageQueue.mq.addAll(discardMessages);
							}
						}
					}
					try{
						Thread.sleep(200);
					}catch(InterruptedException ex){
						stop = true;
						break;
					}
				}
			}
		});
		
		innerThread.setDaemon(true);
		innerThread.start();
	}
	
	public void stop(){
			
		stop = true;
		innerThread.interrupt();
	}
	
	
	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public boolean isFailDiscard() {
		return failDiscard;
	}

	public void setFailDiscard(boolean failDiscard) {
		this.failDiscard = failDiscard;
	}

	public  void consumer(Message msg) throws HbaseMonitorException{
		
	}
	
	protected  Map<String,Map<String,Object>> convert( T msg){
		
		return Maps.newHashMap();
	}

}
