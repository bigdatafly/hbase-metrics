/**
 * 
 */
package com.bigdatafly.monitor.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdatafly.monitor.exception.PageNotFoundException;


/**
 * @author summer
 * The return format is JSON and in the form
 * <p>
 *  <code><pre>
 *  {
 *    "beans" : [
 *      {
 *        "name":"bean-name"
 *        ...
 *      }
 *    ]
 *  }
 *  
 *  For example <code>http://.../jmx?qry=Hadoop:*</code> will return
 * all hadoop metrics exposed through JMX.
 * If the <code>qry</code> or the <code>get</code> parameter is not formatted 
 * correctly then a 400 BAD REQUEST http response code will be returned. 
 * <p>
 * If a resouce such as a mbean or attribute can not be found, 
 * a 404 SC_NOT_FOUND http response code will be returned. 
 */
public class Fetcher {

	private static final Logger logger = LoggerFactory.getLogger(Fetcher.class);
	
	private CloseableHttpClient httpclient;
	private String encodingName;
	private String encoding;
	private final static String DEFAULT_ENCODING = "UTF-8";
	private final static Charset DEFAULT_CHARSET = Charset.defaultCharset();
	//private final static Charset UTF8_CHARSET = Charset.forName(DEFAULT_ENCODING);
	private final static int BUFF_SIZE = 512;
	private Charset charset;
	
	public enum METHOD{
		GET,
		POST
	}
	
	protected Fetcher(){
		
		encoding= encodingName =DEFAULT_ENCODING ;
		charset = DEFAULT_CHARSET;
	}
	
	public static Fetcher create(){
		
		Fetcher fetcher = new Fetcher();
		fetcher.httpclient = fetcher.createHttpClient();
		return fetcher;
	}
	
	protected CloseableHttpClient createHttpClient(){
		
		return  HttpClientBuilder.create().build();
	}
	/**
	 * 
	 * @param url
	 * @param method
	 * @return
	 * @see org.apache.hadoop.jmx.JMXJsonServlet
	 */
	public String fetcher(String url,METHOD method) throws IOException,
		IllegalArgumentException,
		PageNotFoundException{
		
		StringBuffer result= new StringBuffer();
		BufferedReader br = null;
		int readBytes = 0;
		try {
			//CloseableHttpResponse response = client.execute(post);
			HttpUriRequest request;
			if(method == METHOD.GET){
				request = new HttpPost(url);
			}else{
				request = new HttpGet(url);
			}
			
			CloseableHttpResponse response = httpclient.execute(request); 
			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode == HttpStatus.SC_OK){
				
				HttpEntity entity = response.getEntity();
				InputStream in = entity.getContent();
				Header header = entity.getContentEncoding();
				if(header != null){
					encodingName = header.getName();
					encoding = header.getValue();
				}else{
					encoding= encodingName = DEFAULT_ENCODING ;
					charset = getCharset(encodingName,encoding);
				}
				
				if(in!=null){
					br = new BufferedReader(new InputStreamReader(in,charset));
					char[] bytebuf ;
					
					while(true){
						bytebuf = new char[BUFF_SIZE];
						readBytes = br.read(bytebuf) ;
						if(readBytes == -1)
							break;
						result.append(bytebuf);
					}
				}
				
			}else if(statusCode == HttpStatus.SC_BAD_REQUEST){ //²ÎÊý´íÎó·µ»Ø400
				if(logger.isDebugEnabled())
					logger.debug(" {error} Http 400 Error, Maybe Jmx qry parameter IllegalArgumentException");
				throw new IllegalArgumentException("Http 400 Error, Maybe parameters of qry Jmx is illegal");
			}else{ //can not be found will return code SC_NOT_FOUND
				if(logger.isDebugEnabled())
					logger.debug("{error:404} "+url+"page not found, Maybe Jmx qry parameter IllegalArgumentException");
				throw new PageNotFoundException(url + " page not found");
			}
		} catch (IOException e) {
			if(logger.isDebugEnabled()){
				logger.debug("{exception}",e);
			}
			throw e;
		}finally{
			
			if(br!=null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw e;
				}
		}
		
		return result.toString();
	}
	
	private Charset getCharset(String encodingName,String encoding){
		Charset charset;
		try{
			charset = Charset.forName(encoding);
		}catch(Exception ex){
			charset = DEFAULT_CHARSET;
		}
		
		return charset;
	}
	
	public String fetcher(String url) throws IOException,
		IllegalArgumentException,PageNotFoundException{
		
		return fetcher(url,METHOD.POST);
	}
	
	
	public void close(){
		
		try {
			httpclient.close();
		} catch (IOException e) {
			if(logger.isDebugEnabled())
				logger.debug("{exception}",e);
		}
			
	}

}
