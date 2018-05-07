package com.aspire.bpom.thread;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;

import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.service.ReconciliationService;

/**
 * 签约对账消费者
 * @author liuweifeng
 *
 */
public class ConsumerCR implements Runnable {
	
	private static final Logger logger = BpomLogger.getLogger(ConsumerCR.class);

	private ReconciliationService contractReconService;
	
	private CountDownLatch latch;
	
	public static int i = 1;// 判断消费者取完数据后退出循环
	
	public ConsumerCR(ReconciliationService contractReconService, CountDownLatch latch){
		this.contractReconService = contractReconService;
		this.latch = latch;
	}
	@Override
	public void run() {				
		try {	
			i = 1;// 判断消费者取完数据后退出循环
			boolean flag = false;
			while(true){
				flag = contractReconService.consume();
				if(flag){
					break;
				}
				i++;
			}
			
		} catch (Exception e) {
			logger.error("ConsumerCR.run().error" + e);
			e.printStackTrace();
		} finally {
			latch.countDown();//减计数器
		}
	}
}
