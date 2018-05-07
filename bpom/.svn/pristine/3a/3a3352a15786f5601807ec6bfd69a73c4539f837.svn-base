package com.aspire.bpom.thread;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.extensions.log4j.ThreadLocalTransactionID;
import com.aspire.bpom.queue.ContractNotifyQueue;
import com.aspire.bpom.xml.bean.request.EntrustPayNotifyReq;


/**
 * 重发签约/解约结果通知
 * @author liuweifeng
 *
 */
@Component
public class ContractNotifyResendThread implements Runnable {

	private static final Logger logger = BpomLogger.getLogger(ContractNotifyResendThread.class);

	@Resource
	private ContractNotifyQueue contractNotifyQueue;
	@Resource
	private NotifyThreadPool notifyThreadPool;

	@Override
	public void run() {
		try {
			while (!contractNotifyQueue.isStoped()) {
				EntrustPayNotifyReq entrustPayNotifyReq = contractNotifyQueue.getNotify();
				ThreadLocalTransactionID.setTransactionID(entrustPayNotifyReq.getSessionId());
				long currentTime = new Date().getTime();// 获取当前时间
				// 获取每次的定时时间
				long scheduledTime = entrustPayNotifyReq.getScheduledTime().getTime();
				// 获取重发次数
				Integer resendNum = entrustPayNotifyReq.getTimingNumber();
				if (resendNum > 3) {
					logger.warn("重发签约/解约结果通知次数超过了预定的次数");
				} else {
					// 当当前时间大于每次的预定时间时，就重发一次
					if (currentTime >= scheduledTime) {
						logger.info("签约协议号" + entrustPayNotifyReq.getWxContract_code()
								+ "第" + resendNum + "次重发签约/解约结果通知");
						notifyThreadPool.execute(entrustPayNotifyReq);
					} else {
						contractNotifyQueue.offer(entrustPayNotifyReq);
					}
				}
			}
		} catch (InterruptedException e) {
			logger.error("ContractNotifyResendThread.run().error 重发异步通知签约结果给内部业务平台异常" + e);
			e.printStackTrace();
		}

	}

}
