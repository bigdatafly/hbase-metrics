/**
 * 
 */

package com.bigdatafly.monitor.messages;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.storm.shade.com.google.common.collect.Maps;
import org.springframework.util.StringUtils;

import com.bigdatafly.monitor.hbase.JmxQueryConstants;
import com.bigdatafly.monitor.hbase.model.Beans;
import com.bigdatafly.utils.JsonUtils;

/**
 * @author summer
 *
 */
public class MessageParser {

	public static Beans jmxMessageParse(final String json){
		
		return JsonUtils.fromJson(json, Beans.class);
	}

	public static List<Map<String,Object>> jmxMessageParse2(final String json){
		Beans beans =   JsonUtils.fromJson(json, Beans.class);
		return beans.getBeans();
	}
	
	public static List<String> getLiveRegionServerFromJmxMessage(Beans beans){
		
		List<String> result= new ArrayList<String>();
		if(beans!=null && beans.getBeans()!=null && !beans.getBeans().isEmpty()){
			for(Map<String,Object> bean : beans.getBeans()){
				String regionServers = bean.get(JmxQueryConstants.LIVE_REGION_SERVERS_TAG)+"";
				if(!StringUtils.isEmpty(regionServers)){
					result.addAll(getRegionServerHostname(regionServers));
				}	
			}
		}
		
		return result;
	}
	
	public static List<String> getDeadRegionServerFromJmxMessage(Beans beans){
		
		List<String> result= new ArrayList<String>();
		if(beans!=null && beans.getBeans()!=null && !beans.getBeans().isEmpty()){
			for(Map<String,Object> bean : beans.getBeans()){
				String regionServers = bean.get(JmxQueryConstants.deadRegionServers)+"";
				if(!StringUtils.isEmpty(regionServers)){
					result.addAll(getRegionServerHostname(regionServers));
				}	
			}
		}
		
		return result;
	}
	
	private static List<String> getRegionServerHostname(String rs){
		
		List<String> result= new ArrayList<String>();
		String[] regionServers = rs.split(";");
		int regionServerNum = regionServers.length;
		for(int i=0;i<regionServerNum;i++){
			String regionServer = regionServers[i];
			if(!StringUtils.isEmpty(regionServer)){
				if(regionServer.indexOf(",")>0){
					String[] hosts = regionServer.split(",");
					if(!StringUtils.isEmpty(hosts[0]))
						result.add(hosts[0]);
				}
			}
		}
		
		return result;
	}
	
	public static Map<String,Object> getParamters(Beans beans,String key){
		
		Map<String,Object> jmxParams = Maps.newHashMap();
		if(beans!=null && beans.getBeans()!=null && !StringUtils.isEmpty(key)){
			for(Map<String,Object> bean : beans.getBeans()){
				if(bean.containsKey(key))
					jmxParams.put(key, bean.get(key));
			}
		}
		return jmxParams;
	}
	
	public static Map<String,Object> getParamters(Beans beans,Set<String> keys){
		Map<String,Object> jmxParams = Maps.newHashMap();
		for(String key:keys)
			jmxParams.putAll(getParamters(beans,key));
		return jmxParams;
	}
}
