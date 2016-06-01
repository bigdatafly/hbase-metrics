/**
 * 
 */
package com.bigdatafly.monitor.mq;

import java.util.Deque;
import java.util.LinkedList;

import com.bigdatafly.monitor.messages.Message;

/**
 * @author summer
 *
 */
public class MessageQueue {
	
	public static final Deque<Message> mq = new LinkedList<Message>();
	
	public static synchronized Message poll(){
			return mq.poll();
	}
	
	public static synchronized void offer(Message msg){
		mq.offer(msg);
	}
	
	public static synchronized int size(){
		return mq.size();
	}
	
	
	public static boolean isEmpty(){
		return mq.isEmpty();
	}
	
}
