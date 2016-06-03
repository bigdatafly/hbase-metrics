/**
 * 
 */
package com.bigdatafly.monitor.mq;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import com.bigdatafly.monitor.messages.Message;

/**
 * @author summer
 *
 */
public class MessageQueue {
	
	public static final Queue<Message> mq = new LinkedList<Message>();
	
	public static synchronized Message poll(){
			return mq.poll();
	}
	
	public static synchronized void offer(Message msg){
		
		System.out.println("mq"+mq);
		mq.offer(msg);
	}
	
	public static synchronized void addAll(Collection<? extends Message> msg){
		mq.addAll(msg);
	}
	
	public static synchronized int size(){
		return mq.size();
	}
	
	
	public static boolean isEmpty(){
		return mq.isEmpty();
	}
	
}
