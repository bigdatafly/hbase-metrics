/**
 * 
 */
package com.bigdatafly.monitor.exception;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
/**
 * @author summer
 *
 */
public class ThrowCauseHander {

	private static final Logger logger = LoggerFactory.getLogger(ThrowCauseHander.class);
	
	public static void throwCauseHander(Object target,Throwable cause){
		
		throwCauseHander(target,cause,"");
	}
	
	public static void throwCauseHander(Object target,Throwable cause,String msg){
		
		if(cause instanceof Error){
			if(logger.isErrorEnabled()){
				logger.error("{target:"+target+",error:"+msg+"}", cause);
			}
			throw (Error)cause;
		}else{
			if(logger.isErrorEnabled()){
				logger.error("{target:"+target+",error:"+msg+"}", cause);
			}
		}
	}
	
}
