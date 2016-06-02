/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdatafly.monitor.exception.HbaseMonitorException;
import com.bigdatafly.monitor.hbase.MonitorDataOperator;
import com.bigdatafly.monitor.hbase.MonitorItemOperator;
import com.bigdatafly.monitor.messages.Message;
import com.bigdatafly.monitor.messages.StringMessage;
import com.google.common.collect.Maps;

/**
 * @author summer
 *
 */
public class HbaseConsumer extends AbstractConsumer<StringMessage> implements Consumer{

	private static final Logger logger = LoggerFactory.getLogger(HbaseConsumer.class);
	
	private MonitorDataOperator hbaseOperator;
	private MonitorItemOperator monitorItemOperator;
	
	
	//private int batchSize;  不能入库
	//private static final int DEFAULT_BATCH_SIZE = 1;
	
	public HbaseConsumer(MonitorDataOperator hbaseOperator,MonitorItemOperator monitorItemOperator){
	
		super();
		this.hbaseOperator = hbaseOperator;
		this.monitorItemOperator = monitorItemOperator;
	}
	
	
	@Override
	public void consumer(Message msg) throws HbaseMonitorException{
		
		super.consumer(msg);
		
		if(msg instanceof StringMessage){
			
			StringMessage stringMsg = (StringMessage)msg;
			//String serverName = stringMsg.getResource();
			//String timeStamp = stringMsg.getTimestamp();
			//Map<String,String> header = stringMsg.getHeader();
			//Map<String,Object> body = stringMsg.getBody();
			//List<Map<String,Object>> performances= MessageParser.jmxMessageParse2(body);
			//Map<String,Object> performancesIndex = MessageParser.getParamters(beans, JmxQueryConstants.REGION_SERVER_PERFORMANCES_INDEX);
			try {
				/*
				for(Map<String,Object> e:performances){
					e.putAll(header);
				}*/
				//hbaseOperator.put(timeStamp,performancesIndex);
				//Map<String,Object> performances = body;
				Map<String,Map<String,Object>> performances = convert(stringMsg);
				hbaseOperator.put(performances);
			} catch (Exception e) {
				
				logger.error("insert into hbase fail",e);
				throw new HbaseMonitorException("insert into hbase fail",e);
			}
			if(logger.isDebugEnabled()){
				System.out.println("--------------message begin---------------------");
				System.out.println(stringMsg);
				System.out.println("--------------message end------------------------");
				logger.debug(stringMsg.toString());
			}
		}
	}
	
	@Override
	protected Map<String,Map<String,Object>> convert(StringMessage msg) {
		
		Map<String,String> header = msg.getHeader();
		Map<String,Object> protocolData = msg.getBody();
		Map<String,Map<String,Object>> monitorData = Maps.newHashMap();
		
		//for(Map<String,Object> datas:protocolData){
			//去掉协议头
			String timestamp = msg.getTimestamp();
			String modeName  = msg.getModel();
			String serverNode  = msg.getSource();
			String urlresource  = msg.getResource();
			//转码
			serverNode = getMonitorServerItem(modeName,serverNode);
			for(Map.Entry<String,Object> e:protocolData.entrySet()){
				
				String attribute = e.getKey();
				Object value = e.getValue();
				String key = rowkeyGenerator(getMonitorItem(modeName,attribute),timestamp);
				if(monitorData.containsKey(key))
					monitorData.get(key).put(serverNode, value);
				else{
					Map<String,Object> cf = Maps.newHashMap();
					cf.put(serverNode,value);
					monitorData.put(key, cf);
				}
			}	
		//}
		
		return monitorData;
	}
	
	//还有问题
	private String getMonitorItem(String mode,String key){
		
		return monitorItemOperator.getMonitorItem(mode, key);
	}
	
	private String getMonitorServerItem(String mode,String key){
		return monitorItemOperator.getMonitorServerItem(mode, key);
	}
	
	protected String rowkeyGenerator(String key,String timestamp) {
	
		return key+timestamp;	
	}
}
