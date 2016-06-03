/**
 * 
 */
package com.bigdatafly.monitor.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class HttpMonitorServer {

	private final static Logger logger = LoggerFactory.getLogger(HttpMonitorServer.class);
	
	private int httpPort;
	private int httpRequestNum;
	private String requestPath;
	private HbaseMonitorConfiguration conf;
	HttpServer httpserver=null;
	Deserializer deserializer;
	private boolean started;
	private static final String RESPONSE_MSG = "ok";   
	
	public HttpMonitorServer(HbaseMonitorConfiguration conf){
		this.conf = conf;
		this.httpPort = this.conf.getHttpServerPort();
		this.httpRequestNum = this.conf.getHttpServerMaxRequestNum();
		this.requestPath = this.conf.getHttpServerContextPath();
		this.started = false;
	}
	
	public void start() throws HbaseMonitorException{
		
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
	
	public void stop(){
		if(started && httpserver !=null)
			httpserver.stop(100);
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
	            	 Collection<? extends Message> stringMsg = deserializer.deserialize("", "/storm/jsonData","", readStr);
	 	    		 MessageQueue.addAll(stringMsg);
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
