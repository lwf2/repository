package com.aspire.bpom.queue;

import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;
import org.springframework.stereotype.Component;
import com.aspire.bpom.xml.bean.request.EntrustPayNotifyReq;

/**
 * 签约/解约结果通知队列
 * @author liuweifeng
 *
 */
@Component
public class ContractNotifyQueue {

	private LinkedBlockingQueue<EntrustPayNotifyReq> notifyQueue = new LinkedBlockingQueue<EntrustPayNotifyReq>();
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
	public boolean offer(EntrustPayNotifyReq notify) {
		return notifyQueue.offer(notify);
	}

	public EntrustPayNotifyReq poll() {
		return notifyQueue.poll();
	}

	public void putAll(Collection<? extends EntrustPayNotifyReq> c) {
		notifyQueue.addAll(c);
	}

	public EntrustPayNotifyReq getNotify() throws InterruptedException {
		return notifyQueue.take();
	}

}
