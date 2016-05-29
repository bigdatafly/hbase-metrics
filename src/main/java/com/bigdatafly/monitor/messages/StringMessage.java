/**
 * 
 */
package com.bigdatafly.monitor.messages;



/**
 * @author summer
 *
 */
public class StringMessage implements Message{

	private String body;
	private Object target;
	private Object source;
	
	public StringMessage(Object source,Object target,String body){
		this.source = source;
		this.target = target;
		this.body = body;
	}
	
	public StringMessage(String body){
		
		this("",body);
	} 
	
	public StringMessage(Object source,String body){
		this(source,null,body);
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

	@Override
	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("source:"+source+",");
		sb.append("target:"+target+",");
		sb.append("body:"+body);
		sb.append("}");
		return sb.toString();
	}
	
	
	
}
