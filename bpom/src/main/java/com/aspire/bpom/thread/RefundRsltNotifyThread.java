package com.aspire.bpom.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.aspire.bpom.bean.OrderPO;
import com.aspire.bpom.constants.ReturnCode;
import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.extensions.log4j.ThreadLocalTransactionID;
import com.aspire.bpom.queue.RefundRsltNotifyQueue;
import com.aspire.bpom.service.TUnsubscribeService;
import com.aspire.bpom.util.DateUtil;
import com.aspire.bpom.util.SystemManager;
import com.aspire.bpom.xml.bean.request.RefundRsltNotifyReq;
import com.aspire.bpom.xml.bean.response.RefundNotifyResp;

@Component
@Scope("prototype")
public class RefundRsltNotifyThread implements Runnable {
	
	private static final Logger logger = BpomLogger.getLogger(RefundRsltNotifyThread.class);

	@Resource
	private SystemManager sm;
	@Resource
	private TUnsubscribeService tUnsubscribeService;	
	@Resource
	private RefundRsltNotifyQueue refundRsltNotifyQueue;

	private RefundRsltNotifyReq refundRsltNotifyReq;

	private OrderPO orderPO;

	private RefundNotifyResp refundNotifyResp;

	public  void setRefundRsltNotifyThread(RefundRsltNotifyReq refundRsltNotifyReq,OrderPO orderPO) {
		this.refundRsltNotifyReq=refundRsltNotifyReq;
		this.orderPO=orderPO;
	}

	@Override
	public void run() {
		try {	
	 		ThreadLocalTransactionID.setTransactionID(refundRsltNotifyReq.getSessionId());
			refundNotifyResp = tUnsubscribeService.refundRsltNotifyService(refundRsltNotifyReq, orderPO);				
			if (ReturnCode.SUCCESS.equals(Integer.parseInt(refundNotifyResp.getReturnCode()))) {
				logger.info("通知退款结果给内部业务平台成功");
			 } else {
				 resendNotice();
			}
		} catch (Exception e) {
			logger.error("RefundRsltNotifyThread.run().error 异步通知内部业务平台退款结果通知异常" + e);
			e.printStackTrace();
			resendNotice();
		}
	}
	
	/**
	 * 重发退款结果通知
	 */
	private void resendNotice(){
		logger.info("通知退款结果给内部业务平台失败");
		Date currTime = new Date();//获取当前时间
		//获取定时次数
		Integer timingNumber = refundRsltNotifyReq.getTimingNumber() + 1;
		refundRsltNotifyReq.setTimingNumber(timingNumber);//设定定时次数
		//获取重发时间
		Integer resendNoticeTime = Integer.valueOf(sm.getRetransmissionNoticeTime().split(",")[timingNumber > 3 ? 3 : timingNumber]);	
		//设定每次的重发启动时间
		refundRsltNotifyReq.setScheduledTime(DateUtil.addMinute(currTime, resendNoticeTime));
		List<Object> list = new ArrayList<Object>();
		list.add(refundRsltNotifyReq);
		list.add(orderPO);
		refundRsltNotifyQueue.offer(list);
	}
}
