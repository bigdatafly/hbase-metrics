/**
 * 
 */
package com.bigdatafly.monitor.messages;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

/**
 * @author summer
 *
 */
public class MessageParser {

	public static List<String> regionServerParse(final String msg){
		List<String> rs = new ArrayList<String>();
		if(StringUtils.hasText(msg) && msg.indexOf("tag.liveRegionServers")>0){
			
		}
		return rs;
	}

	
}
