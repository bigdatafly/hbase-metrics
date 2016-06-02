/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdatafly.monitor.exception.HbaseMonitorException;
import com.bigdatafly.monitor.hbase.MonitorDataOperator;
import com.bigdatafly.monitor.messages.Message;
import com.bigdatafly.monitor.messages.MessageParser;
import com.bigdatafly.monitor.messages.StringMessage;

/**
 * @author summer
 *
 */
public class HbaseConsumer extends AbstractConsumer implements Consumer{

	private static final Logger logger = LoggerFactory.getLogger(HbaseConsumer.class);
	
	private MonitorDataOperator hbaseOperator;
	
	//private int batchSize;  不能入库
	//private static final int DEFAULT_BATCH_SIZE = 1;
	
	public HbaseConsumer(MonitorDataOperator hbaseOperator){
	
		super();
		this.hbaseOperator = hbaseOperator;
	}
	@Override
	public void consumer(Message msg) throws HbaseMonitorException{
		
		if(msg instanceof StringMessage){
			
			StringMessage stringMsg = (StringMessage)msg;
			//String serverName = stringMsg.getResource();
			//String timeStamp = stringMsg.getTimestamp();
			Map<String,String> header = stringMsg.getHeader();
			String body = stringMsg.getBody();
			List<Map<String,Object>> performances= MessageParser.jmxMessageParse2(body);
			//Map<String,Object> performancesIndex = MessageParser.getParamters(beans, JmxQueryConstants.REGION_SERVER_PERFORMANCES_INDEX);
			try {
				for(Map<String,Object> e:performances){
					e.putAll(header);
				}
				//hbaseOperator.put(timeStamp,performancesIndex);
				hbaseOperator.put(performances);
			} catch (Exception e) {
				
				logger.error("insert into hbase fail",e);
				throw new HbaseMonitorException("insert into hbase fail",e);
			}
			if(logger.isDebugEnabled()){
				System.out.println("--------------regionserver("+stringMsg+") begin---------------------");
				System.out.println(body);
				System.out.println("--------------regionserver("+stringMsg+") end------------------------");
				logger.debug(stringMsg.toString());
			}
		}
	}
	
}
