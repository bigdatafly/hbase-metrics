/**
 * 
 */
package com.bigdatafly.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author summer
 *
 */
public class DateUtils {

	public static String getTimestamp(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		return sdf.format(new Date());
	}
}
