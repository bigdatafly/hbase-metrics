/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import java.io.IOException;
import java.util.Deque;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdatafly.monitor.exception.HbaseMonitorException;
import com.bigdatafly.monitor.hbase.JmxQueryConstants;
import com.bigdatafly.monitor.hbase.MonitorDataOperator;
import com.bigdatafly.monitor.hbase.model.Beans;
import com.bigdatafly.monitor.messages.Message;
import com.bigdatafly.monitor.messages.MessageParser;
import com.bigdatafly.monitor.messages.StringMessage;
import com.bigdatafly.monitor.mq.MessageQueue;
import com.google.common.collect.Lists;

/**
 * @author summer
 *
 */
public class HbaseConsumer implements Consumer{

	private static final Logger logger = LoggerFactory.getLogger(HbaseConsumer.class);
	
	private Thread innerThread;
	private volatile boolean stop;
	private MonitorDataOperator hbaseOperator;
	//private Map<String,Message> mq;
	private boolean failDiscard = true;
	//private int batchSize;  不能入库
	//private static final int DEFAULT_BATCH_SIZE = 1;
	
	public HbaseConsumer(MonitorDataOperator hbaseOperator){
	
		this.stop = false;
		this.hbaseOperator = hbaseOperator;
		//this.mq = mq;
		
	}
	
	public void start(){
		innerThread = new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					if(stop || innerThread.isInterrupted())
						break;
					if(!MessageQueue.isEmpty()){
						synchronized(MessageQueue.mq){
							Deque<Message> messages = Lists.newLinkedList();
							while(true){
								Message value = MessageQueue.poll();
								if(value == null)
									break;
								else{
									try {
										consumer(value);
									} catch (HbaseMonitorException e) {
										messages.addFirst(value);
									}
								}
							
							}//while
						
							if(!failDiscard){
								MessageQueue.mq.addAll(messages);
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
	
	public void consumer(Message msg) throws HbaseMonitorException{
		
		if(msg instanceof StringMessage){
			StringMessage stringMsg = (StringMessage)msg;
			String serverName = stringMsg.getResource();
			String body = stringMsg.getBody();
			Beans beans = MessageParser.jmxMessageParse(body);
			Map<String,Object> performancesIndex = MessageParser.getParamters(beans, JmxQueryConstants.REGION_SERVER_PERFORMANCES_INDEX);
			try {
				hbaseOperator.put(serverName,performancesIndex);
			} catch (IOException e) {
				
				logger.error("insert into hbase fail",e);
				throw new HbaseMonitorException("insert into hbase fail",e);
			}
			if(logger.isDebugEnabled()){
				System.out.println("--------------regionserver("+serverName+") begin---------------------");
				System.out.println(body);
				System.out.println("--------------regionserver("+serverName+") end------------------------");
				logger.debug(stringMsg.toString());
			}
		}
	}
	
}
