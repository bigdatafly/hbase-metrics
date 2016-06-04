/**
 * 
 */
package com.bigdatafly.monitor.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdatafly.configurations.ConfigurationConstants;
import com.bigdatafly.monitor.configurations.HbaseMonitorConfiguration;
import com.bigdatafly.monitor.exception.HbaseMonitorException;
import com.bigdatafly.monitor.messages.Message;
import com.bigdatafly.monitor.mq.MessageQueue;
import com.bigdatafly.monitor.serialization.Deserializer;
import com.bigdatafly.monitor.serialization.StringDeserializer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;


/**
 * @author summer
 *
 */
public class MonitorHttpServer {

	private final static Logger logger = LoggerFactory.getLogger(MonitorHttpServer.class);
	
	private int httpPort;
	private int httpRequestNum;
	private String requestPath;
	HttpServer httpserver=null;
	Deserializer deserializer;
	private volatile boolean started;
	private static final String RESPONSE_MSG = "OK";   
	
	public MonitorHttpServer(){
		this(ConfigurationConstants.DEFAULT_HTTP_SERVER_REQ_PATH);
	}
	
	public MonitorHttpServer(File propertyFile){
		this(HbaseMonitorConfiguration.Builder.newSingletonConfiguration(propertyFile));
	}
	
	public MonitorHttpServer(String requestPath){
		this(ConfigurationConstants.DEFAULT_HTTP_SERVER_PORT,requestPath);
	}
	
	public MonitorHttpServer(int httpPort,String requestPath){
		this(httpPort,requestPath,ConfigurationConstants.DEFAULT_HTTP_MAX_REQUEST_NUM);
	}
	
	public MonitorHttpServer(HbaseMonitorConfiguration conf){
		
		this(conf.getHttpServerPort(),
				conf.getHttpServerContextPath(),
				conf.getHttpServerMaxRequestNum());
		
	}
	/**
	 * 
	 * @param httpPort httpserver port 
	 * @param requestPath httpserver context request path
	 * @param maxReqNum max request num
	 */
	public MonitorHttpServer(int httpPort,String requestPath,int maxReqNum){
		
		this.httpPort = (httpPort <23)?ConfigurationConstants.DEFAULT_HTTP_SERVER_PORT:httpPort;
		this.httpRequestNum = maxReqNum;
		this.requestPath = requestPath;
		this.started = false;
	}
	
	public synchronized void start() throws HbaseMonitorException{
		
		if(started){
			throw new HbaseMonitorException("http server already was started");
		}else{
			deserializer = new StringDeserializer();
			
			try {
				httpserver = createHttpserverService();
				httpserver.start(); 
				started = true;
			} catch (IOException e) {
				
				logger.error("http server start failed {"
						+ "httpPort:"+httpPort+","
						+ "httpRequestNum:"+httpRequestNum+","
						+ "httpPort:"+requestPath
						+ "}", e);
				
			}
			
			if(logger.isDebugEnabled())
				logger.debug("http server start failed {"
						+ "httpPort:"+httpPort+","
						+ "httpRequestNum:"+httpRequestNum+","
						+ "httpPort:"+requestPath
						+ "}");
			
	        System.out.println("server started"); 
		}
	}
	
	public synchronized void stop(){
		if(httpserver !=null){
			httpserver.stop(100);
			
		}
		started = false;
	}
	
	public synchronized void await(){
		
		while(started){
			try {
				Thread.sleep(1000*60);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
				break;
			}
		}
	}
	
	protected HttpServer createHttpserverService() throws IOException {
	        HttpServerProvider provider = HttpServerProvider.provider();  
	        HttpServer httpserver =provider.createHttpServer(new InetSocketAddress(httpPort), httpRequestNum);
	        httpserver.createContext(requestPath,new MyHttpHandler());   
	        httpserver.setExecutor(Executors.newCachedThreadPool()); 
	        return httpserver;
	         
	 }  
	
	 class MyHttpHandler implements HttpHandler { 
	    	
	        public void handle(HttpExchange httpExchange) throws IOException{  
	           String resMsg = RESPONSE_MSG;
	        	InputStream in = httpExchange.getRequestBody();
	            if(in == null){
	            	resMsg = "Stream Not Found";
	            }else{
	            	 String readStr = read(in);
	            	 Collection<? extends Message> stringMsg = deserializer.deserialize("storm", "storm","", readStr);
	            	 for(Message msg:stringMsg)
	            		 MessageQueue.offer(msg);
	            }
	    		
	            httpExchange.sendResponseHeaders(200, resMsg.length());
	            OutputStream out = httpExchange.getResponseBody();
	            out.write(resMsg.getBytes());  
	            out.flush();  
	            httpExchange.close();                                 
	              
	        }  
	}
	 
	private String read(InputStream in) throws IOException{
		try{
	        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
	        StringBuffer sb = new StringBuffer();
	        String temp = null;  
	        while((temp = reader.readLine()) != null) {  
	            sb.append(temp);
	        }
	        return sb.toString();
        }catch(IOException ex){
        	logger.error("http server read failed",ex);
        	throw ex;
        }finally{
        	/*
        	if(in!=null)
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
        }
	}
	 
	public int getHttpPort() {
		return httpPort;
	}
	public void setHttpPort(int httpPort) {
		this.httpPort = httpPort;
	}
	public int getHttpRequestNum() {
		return httpRequestNum;
	}
	public void setHttpRequestNum(int httpRequestNum) {
		this.httpRequestNum = httpRequestNum;
	}
	public String getRequestPath() {
		return requestPath;
	}
	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}
	 
	 
}
