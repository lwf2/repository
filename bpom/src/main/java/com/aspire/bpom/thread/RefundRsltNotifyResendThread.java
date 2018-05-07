package com.aspire.bpom.thread;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.aspire.bpom.bean.OrderPO;
import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.extensions.log4j.ThreadLocalTransactionID;
import com.aspire.bpom.queue.RefundRsltNotifyQueue;
import com.aspire.bpom.xml.bean.request.RefundRsltNotifyReq;

/**
 * 重发退款结果通知
 * @author liuweifeng
 *
 */
@Component
public class RefundRsltNotifyResendThread implements Runnable {

	private static final Logger logger = BpomLogger.getLogger(RefundRsltNotifyResendThread.class);

	@Resource
	private RefundRsltNotifyQueue refundRsltNotifyQueue;
	@Resource
	private NotifyThreadPool notifyThreadPool;

	@Override
	public void run() {
		try {
			while (!refundRsltNotifyQueue.isStoped()) {
				List<Object> list = refundRsltNotifyQueue.getNotify();
				long currentTime = new Date().getTime();// 获取当前时间
				RefundRsltNotifyReq refundRsltNotifyReq = (RefundRsltNotifyReq) list.get(0);
				OrderPO orderPO = (OrderPO) list.get(1);
		 		ThreadLocalTransactionID.setTransactionID(refundRsltNotifyReq.getSessionId());
				// 获取每次的定时时间
				long scheduledTime = refundRsltNotifyReq.getScheduledTime().getTime();
				// 获取重发次数
				Integer resendNum = refundRsltNotifyReq.getTimingNumber();
				if (resendNum > 3) {
					logger.warn("重发订单模块退款结果通知次数超过了预定的次数");
				} else {
					// 当当前时间大于每次的预定时间时，就重发一次
					if (currentTime >= scheduledTime) {
						logger.info("订单ID" + refundRsltNotifyReq.getOrderId()
								+ "第" + resendNum + "次重发订单模块退款结果通知");
						notifyThreadPool.execute(refundRsltNotifyReq, orderPO);
					} else {
						refundRsltNotifyQueue.offer(list);
					}
				}
			}
		} catch (InterruptedException e) {
			logger.error("RefundRsltNotifyResendThread.run.error 重发异步通知内部业务平台退款结果通知异常" + e);
			e.printStackTrace();
		}

	}

}
