package com.aspire.bpom.thread;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.extensions.log4j.ThreadLocalTransactionID;
import com.aspire.bpom.queue.PayResultNotifyQueue;
import com.aspire.bpom.xml.bean.request.PayResultNotifyReq;


/**
 * 重发支付结果通知
 * @author liuweifeng
 *
 */
@Component
public class OrderNotifyResendThread implements Runnable {

	private static final Logger logger = BpomLogger.getLogger(OrderNotifyResendThread.class);

	@Resource
	private PayResultNotifyQueue payResultNotifyQueue;
	@Resource
	private NotifyThreadPool notifyThreadPool;

	@Override
	public void run() {
		try {
			while (!payResultNotifyQueue.isStoped()) {
				List<Object> list = payResultNotifyQueue.getNotify();
				long currentTime = new Date().getTime();// 获取当前时间
				PayResultNotifyReq payResultNotifyReq = (PayResultNotifyReq) list.get(0);
				String orderId = (String) list.get(1);
				ThreadLocalTransactionID.setTransactionID(payResultNotifyReq.getSessionId());
				// 获取每次的定时时间
				long scheduledTime = payResultNotifyReq.getScheduledTime().getTime();
				// 获取重发次数
				Integer resendNum = payResultNotifyReq.getTimingNumber();
				if (resendNum > 3) {
					logger.warn("重发支付结果通知次数超过了预定的次数");
				} else {
					// 当当前时间大于每次的预定时间时，就重发一次
					if (currentTime >= scheduledTime) {
						logger.info("订单" + payResultNotifyReq.getOrderId()
								+ "第" + resendNum + "次重发支付结果通知");
						notifyThreadPool.execute(payResultNotifyReq, orderId);
					} else {
						payResultNotifyQueue.offer(list);
					}
				}
			}
		} catch (InterruptedException e) {
			logger.error("OrderNotifyResendThread.run().error 重发异步通知内部业务平台支付结果通知异常" + e);
			e.printStackTrace();
		}

	}

}
