/**
 * 
 */
package com.bigdatafly.monitor.messages;

import com.bigdatafly.utils.DateUtils;

/**
 * @author summer
 *
 */
public class StringMessage implements Message{

	private String body;
	private Object target;
	private Object source;//存放 servername
	private String resource;//存放URL
	private String id;
	
	public StringMessage(Object source,String resource,Object target,String body){
		this.source = source;
		this.target = target;
		this.body = body;
		this.resource = resource;
		this.id = DateUtils.getTimestamp();
	}
	
	public StringMessage(String body){
		
		this("","",body);
	} 
	
	public StringMessage(Object source,String resource,String body){
		this(source, resource,null,body);
	}
	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public Object getSource() {
		
		return this.source;
	}

	@Override
	public Object getTarget() {
		
		return target;
	}

	
	public void setTarget(Object target) {
		this.target = target;
	}

	public void setSource(Object source) {
		this.source = source;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	@Override
	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("id:"+id+",");
		sb.append("source:"+source+",");
		sb.append("resource:"+resource+",");
		sb.append("target:"+target+",");
		sb.append("body:"+body);
		sb.append("}");
		return sb.toString();
	}

	@Override
	public String getResource() {
		
		return this.resource;
	}

	@Override
	public String getId() {
		
		return this.id;
	}
	
	
	
}
