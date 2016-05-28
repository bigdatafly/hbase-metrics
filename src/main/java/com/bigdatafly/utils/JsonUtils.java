/**
 * 
 */
package com.bigdatafly.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author summer
 *
 */
public class JsonUtils {

	static Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	public static String toJson(Object obj){
		try{
			return gson.toJson(obj);
		}catch(Exception ex){
			return "";
		}
	}
	
	public static <T> T fromJson(String json,Class<T> type) {	
		try{
			
			return gson.fromJson(json, type);
		}catch(Exception ex){
			return null;
		}
	}
	
}
