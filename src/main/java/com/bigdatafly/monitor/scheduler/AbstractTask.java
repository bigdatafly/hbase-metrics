/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import java.util.concurrent.CountDownLatch;

import com.bigdatafly.monitor.messages.Message;
import com.bigdatafly.monitor.serialization.Serializer;
import com.bigdatafly.monitor.serialization.StringSerializer;
import com.google.common.base.Preconditions;

/**
 * @author summer
 *
 */
public abstract class AbstractTask implements Task {

	private State state;
	private int interval;
	private final static int DEFAULT_INTREVAL = 60*1000;
	private volatile boolean stop;
	private CountDownLatch latch;
	private boolean started = false;

	protected Handler handler;
	protected Serializer<? extends Message> serializer;
	protected Callback callback;
	
	public AbstractTask(){

		this(null);
	}
	
	public AbstractTask(Handler handler){
		
		this(null,handler);
	}
	
	
	public AbstractTask(Serializer<? extends Message> serializer,Handler handler){
		
		this(serializer,handler,null);
	}
	
	public AbstractTask(Serializer<? extends Message> serializer,Handler handler,Callback callback){
		
		this.callback = ((this.callback == null)?new DefaultCallback():this.callback);
		this.serializer = ((this.serializer == null)?new StringSerializer():this.serializer);
		Preconditions.checkArgument(handler!=null,"message handler must not be null!");
		this.handler = handler;
		
	}

	@Override
	public void start() {
		
		this.stop = false;
		this.interval = DEFAULT_INTREVAL;
		this.state = State.START;
		this.latch = new CountDownLatch(1);
		this.started = true;
		//this.serializer = ((this.serializer == null)?new StringSerializer():this.serializer);
			
	}

	@Override
	public void run() {
		
		if(!this.started)
			throw new RuntimeException();
		
		while(true){
			this.state = State.RUNNING;
			if(stop)
				break;
			this.execute();
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
				this.state = State.STOP;
				break;
			}
			
		}
		
		if(latch!=null)
			latch.countDown();
	}

	@Override
	public Task setHandler(Handler handler) {
		this.handler = handler;
		return this;
	}
	
	public Handler getHandler(){
		
		return this.handler;
	}
	
	@Override
	public Task setInterval(int interval){
		
		this.interval = ((interval>100)?interval:DEFAULT_INTREVAL);
		return this;
	}

	@Override
	public void execute() {

		try {
			Message msg = poll();
			if(msg!=null){
				if(this.callback!=null)
					this.callback.onMessage(this, msg);
				process(msg);
			}
		} catch (Exception e) {
			this.callback.onError(this, e);
		}
	}

	protected abstract Message poll() throws Exception;
	
	protected  <T extends Message> void process(T message){
		
		if(handler!=null)
			handler.handler(message);
	}

	@Override
	public Task setSerializer(Serializer<? extends Message> serializer) {
		this.serializer = serializer;
		return this;
	}

	@Override
	public synchronized void stop() {
		
		this.stop = true;
		this.waitFor();
		this.state = State.STOP;
	}

	@Override
	public synchronized void waitFor() {
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized State getState() {
		
		return this.state;
	}
}
