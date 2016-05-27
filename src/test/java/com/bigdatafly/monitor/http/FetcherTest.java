/**
 * 
 */
package com.bigdatafly.monitor.http;

import org.junit.Test;

/**
 * @author summer
 *
 */

public class FetcherTest {

	public static final String URL_TEST = "http://172.27.102.116:60010/jmx?";
	
	@Test
	public void fetcherTest() throws Exception{
		Fetcher fetcher = Fetcher.create();
		String content = fetcher.fetcher(URL_TEST);
		fetcher.close();
		System.out.println(content);
	}
}
