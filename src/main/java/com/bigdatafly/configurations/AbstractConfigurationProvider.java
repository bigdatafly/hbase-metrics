/**
 * 
 */
package com.bigdatafly.configurations;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import org.apache.storm.shade.com.google.common.collect.Maps;

/**
 * @author summer
 *
 */
public abstract class AbstractConfigurationProvider implements ConfigurationProvider{

	public abstract Configuration getConfiguration();
	
	protected Map<String, String> toMap(Properties properties){
		Map<String,String> result = Maps.newHashMap();
		Enumeration<?> propertyNames = properties.propertyNames();
		for(;propertyNames.hasMoreElements(); ){
			String name = (String) propertyNames.nextElement();
			String value = properties.getProperty(name);
			result.put(name, value);
		}
		return result;	
	}
}
