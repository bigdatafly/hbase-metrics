/**
 * 
 */
package com.bigdatafly.monitor.scheduler;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigdatafly.monitor.messages.Message;
import com.bigdatafly.monitor.serialization.Deserializer;
import com.bigdatafly.monitor.serialization.StringDeserializer;
import com.google.common.base.Preconditions;

/**
 * @author summer
 *
 */
public abstract class AbstractTask implements Task {

	private static final Logger logger = LoggerFactory.getLogger(AbstractTask.class);
	private State state;
	private int interval;
	private final static int DEFAULT_INTREVAL = 60;
	private volatile boolean stop;
	private CountDownLatch latch;
	private boolean started = false;

	protected Handler handler;
	protected Deserializer deserializer;
	protected Callback callback;
	
	public AbstractTask(){

		this(null);
	}
	
	public AbstractTask(Handler handler){
		
		this(null,handler);
	}
	
	
	public AbstractTask(Deserializer deserializer,Handler handler){
		
		this(deserializer,handler,null);
	}
	
	public AbstractTask(Deserializer deserializer,Handler handler,Callback callback){
		
		this.callback = ((callback == null)?new DefaultCallback():callback);
		this.deserializer = ((deserializer == null)?new StringDeserializer():deserializer);
		this.interval = DEFAULT_INTREVAL;
		Preconditions.checkArgument(handler!=null,"message handler must not be null!");
		this.handler = handler;
		
	}

	@Override
	public void start() {
		
		this.stop = false;
		this.state = State.START;
		this.latch = new CountDownLatch(1);
		this.started = true;
		//this.serializer = ((this.serializer == null)?new StringSerializer():this.serializer);
			
	}

	@Override
	public void run() {
		
		if(!this.started)
			throw new RuntimeException();
		
		while(!stop||!Thread.currentThread().isInterrupted()){
			this.state = State.RUNNING;
			if(stop)
				break;
			try {
				this.execute();
				Thread.sleep(interval*1000);
			} catch (InterruptedException e) {
				if(logger.isDebugEnabled())
					logger.debug("InterruptedException:",e);
				stop = true;
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
		
		this.interval = ((interval>0)?interval:DEFAULT_INTREVAL);
		return this;
	}

	@Override
	public void execute() {

		try {
			List<Message> msgs = poll();
			for(Message msg:msgs){
				if(msg!=null){
					if(this.callback!=null)
						this.callback.onMessage(this, msg);
					process(msg);
				}
			}
		} catch (Exception e) {
			this.callback.onError(this, e);
		}
	}

	protected abstract List<Message> poll() throws Exception;
	
	protected  <T extends Message> void process(T message){
		
		if(handler!=null)
			handler.handler(message);
	}

	@Override
	public Task setDeserializer(Deserializer deserializer) {
		this.deserializer = deserializer;
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
			/**
			 * 好像然并卵 直接走异常了
			 */
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
