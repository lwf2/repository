package com.aspire.bpom.queue;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import org.springframework.stereotype.Component;

/**
 * 支付结果通知队列
 * @author liuweifeng
 *
 */
@Component
public class PayResultNotifyQueue {

	private LinkedBlockingQueue<List<Object>> notifyQueue = new LinkedBlockingQueue<List<Object>>();
	private volatile boolean stoped = false;
	
	public boolean isStoped(){
		return stoped;
	}
	
	public void stop(){
		stoped=true;
	}
	public boolean isEmpty(){
		return notifyQueue.isEmpty();
	}
	public boolean offer(List<Object> notify) {
		return notifyQueue.offer(notify);
	}

	public List<Object> poll() {
		return notifyQueue.poll();
	}

	public void putAll(Collection<? extends List<Object>> c) {
		notifyQueue.addAll(c);
	}

	public List<Object> getNotify() throws InterruptedException {
		return notifyQueue.take();
	}

}
