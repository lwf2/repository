package com.aspire.bpom.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.aspire.bpom.thread.RefundRsltNotifyResendThread;

/**
 * 调用重发订单模块退款结果通知
 * @author liuweifeng
 *
 */
@Component
public class RefundRsltNotifyTask {
	
	@Resource
	private RefundRsltNotifyResendThread refundRsltNotifyResendThread;

	private static final ScheduledExecutorService payPool = Executors
	        .newSingleThreadScheduledExecutor(new ThreadFactory() {
	            @Override
	            public Thread newThread(Runnable r) {
	                return new Thread(r, "RefundRsltNotifyTaskThread");
	            }
	        });
	
	/**
	 * 单例线程池调用一次重复处理线程
	 * 时间是在1分钟后
	 */
	@PostConstruct
	private void init(){
		payPool.schedule(refundRsltNotifyResendThread, 1, TimeUnit.MINUTES);
	}
	
	/**
	 * 销毁线程池
	 */
	public void shutdown(){
		payPool.shutdown();
	}
}
