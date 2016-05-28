package com.bigdatafly.configurations;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.ImmutableMap;

public class Configuration {

	ImmutableMap<String, String> properties;
	
	public Configuration(Map<String, String> properties) {
		this.properties = ImmutableMap.copyOf(properties);
	}

	public String getString(String key){
		
		return getString(key,StringUtils.EMPTY);
	}
	
	public Integer getInteger(String key){
		
		return getInteger(key,null);
	}
	
	public Integer getInteger(String key,Integer defaultVal){
		
		try{
			String val = getString(key);
			return Integer.valueOf(val);
		}catch(NumberFormatException ex){
			return defaultVal;
		}
	}
	
	public String getString(String key,String defaultVal){
		String val;
		return  ((val=this.properties.get(key))==null)?defaultVal:val;
	}

	public String toString(){
		return String.valueOf(this.properties);
	}
}
