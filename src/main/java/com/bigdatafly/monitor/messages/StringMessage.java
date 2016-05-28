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

	public StringMessage(){
		
	}
	
	public StringMessage(String body){
		
		this.body = body;
	}
	
	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public Object getSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getTarget() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
