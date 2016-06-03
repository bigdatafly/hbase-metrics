/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.bigdatafly.monitor.http.ProtocolConstants;
import com.bigdatafly.monitor.messages.Message;
import com.bigdatafly.monitor.serialization.Deserializer;

/**
 * @author summer
 *
 */
public class MasterHttpTask extends AbstractHttpTask{

	public MasterHttpTask(String url, Handler handler) {
		super(url, handler);
		// TODO Auto-generated constructor stub
	}

	public MasterHttpTask(String url, Deserializer serializer,
			Handler handler, Callback callback) {
		super(url, serializer, handler, callback);
		// TODO Auto-generated constructor stub
	}

	public MasterHttpTask(String url, Deserializer serializer,
			Handler handler) {
		super(url, serializer, handler);
		// TODO Auto-generated constructor stub
	}

	public MasterHttpTask(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected List<Message> poll() throws Exception {
		
		List<Message> msgs = new ArrayList<Message>();
		
		String html = super.getHtml(url);
		if(this.deserializer!=null && !StringUtils.isEmpty(html))
				msgs.addAll(deserializer.deserialize("",ProtocolConstants.PROTOCOL_HEADER_MODEL_HBASE,"",html));

		return msgs;
	}
	

}
