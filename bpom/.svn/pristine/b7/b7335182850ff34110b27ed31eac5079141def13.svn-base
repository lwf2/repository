package com.aspire.bpom.thread;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;

import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.service.ReconciliationService;

/**
 * 订单对账，订单结算 消费者
 * @author liuweifeng
 *
 */
public class ConsumerOR extends Thread {
	
	private static final Logger logger = BpomLogger.getLogger(ConsumerOR.class);

	private ReconciliationService orderReconService;
	
	private CountDownLatch latch;
	
	public static int i = 1;// 判断消费者取完数据后退出循环
	//订单结算
	public static int j = 0;// 总记录数
	public static int k = 0;// 支付记录条数
	public static int l = 0;// 支付金额
	public static int m = 0;// 退款记录条数
	public static int n = 0;// 退款金额
	public static int o = 0;//用于生成文件名称的数字序号
	
	public ConsumerOR(ReconciliationService orderReconService,
			CountDownLatch latch) {
		super();
		this.orderReconService = orderReconService;
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			i = 1;// 判断消费者取完数据后退出循环
			//订单结算
			j = 0;// 总记录数
			k = 0;// 支付记录条数
			l = 0;// 支付金额
			m = 0;// 退款记录条数
			n = 0;// 退款金额
			o = 0;//用于生成文件名称的数字序号
			boolean flag = false;
			while(true){
				flag = orderReconService.consume();
				if(flag){
					break;
				}
				i++;
			}
		} catch (Exception e) {
			logger.error("ConsumerOR.run().error" + e);
			e.printStackTrace();
		} finally {
			latch.countDown();//减计数器
		}
	}
}
