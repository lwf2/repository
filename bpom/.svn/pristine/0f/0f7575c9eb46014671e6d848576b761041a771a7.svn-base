package com.aspire.bpom.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.aspire.bpom.constants.ErrorReturnCode;
import com.aspire.bpom.constants.ReturnCode;
import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.extensions.log4j.ThreadLocalTransactionID;
import com.aspire.bpom.queue.PayResultNotifyQueue;
import com.aspire.bpom.util.DateUtil;
import com.aspire.bpom.util.HttpClientUtil;
import com.aspire.bpom.util.SystemManager;
import com.aspire.bpom.xml.bean.request.PayOrderResultNotifyReq;
import com.aspire.bpom.xml.bean.request.PayResultNotifyReq;
import com.aspire.bpom.xml.bean.response.PayOrderResultNotifyResp;
import com.aspire.bpom.xml.common.ParseXMLHelper;

/**
 * 订单通知线程
 * @author chenpeng
 *
 */
@Component
@Scope("prototype")
public class OrderNotifyThread implements Runnable {
	
	private static final Logger log = BpomLogger.getLogger(OrderNotifyThread.class);

	@Resource
	private SystemManager sm;	
	@Resource
	private PayResultNotifyQueue payResultNotifyQueue;
	
	private PayResultNotifyReq payResultNotifyReq;
	
	private String orderId;
	
	public void setNotify(PayResultNotifyReq payResultNotifyReq, String orderId){
		this.payResultNotifyReq = payResultNotifyReq;
		this.orderId = orderId;
	}
	
	@Override
	public void run() {
		try {
			ThreadLocalTransactionID.setTransactionID(payResultNotifyReq.getSessionId());
			if (orderNotify(payResultNotifyReq, orderId)) {	
				log.info("通知支付结果给内部业务平台成功");
			} else {				
				resendNotice();
			}			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("异步通知内部业务平台支付结果通知异常"+e);
			resendNotice();
		}
	}

	/**
	 * 重发支付结果通知
	 */
	private void resendNotice(){
		log.info("通知支付结果给内部业务平台失败");
		Date currTime = new Date();//获取当前时间
		//获取定时次数
		Integer timingNumber = payResultNotifyReq.getTimingNumber() + 1;
		payResultNotifyReq.setTimingNumber(timingNumber);//设定定时次数
		//获取重发时间
		Integer resendNoticeTime = Integer.valueOf(sm.getRetransmissionNoticeTime().split(",")[timingNumber > 3 ? 3 : timingNumber]);	
		//设定每次的重发启动时间
		payResultNotifyReq.setScheduledTime(DateUtil.addMinute(currTime, resendNoticeTime));
		List<Object> list = new ArrayList<Object>();
		list.add(payResultNotifyReq);
		list.add(orderId);
		payResultNotifyQueue.offer(list);
	}
	
	/**
	 * 向内部业务平台发送支付结果通知
	 */
	private boolean orderNotify(PayResultNotifyReq payResultNotifyReq, String orderId) {
		boolean bet = false;
		//组装请求内部业务系统报文
		PayOrderResultNotifyReq payResultReq = toPayResultNotifyReqs(payResultNotifyReq,orderId);
		log.info("构造请求内部业务系统的参数为："+payResultReq.toString());
		PayOrderResultNotifyResp payResultResps = postPayResult(payResultReq);
		if (ReturnCode.SUCCESS.equals(payResultResps.getReturnCode())) {
			bet = true;
		}
		return bet;
	}
	
	/**
	 * 请求业务平台支付结果通知接口
	 * @param beginPayReq
	 * @return
	 */
	private PayOrderResultNotifyResp postPayResult(PayOrderResultNotifyReq payOrderResultReq){
		String payResultReqXml = ParseXMLHelper.object2XML(PayOrderResultNotifyReq.class, payOrderResultReq);
		PayOrderResultNotifyResp resp = null;
		try{
			String payResultRespXml = HttpClientUtil.doHttpResult(sm.getPayresultnotifyUrl()//请求内部业务平台支付通知接口
					, payResultReqXml);
			resp = ParseXMLHelper.parseXMLToObject(payResultRespXml, PayOrderResultNotifyResp.class);
		}catch(Exception e){
			log.error("内部业务平台响应异常："+e);
			resp.setReturnCode(ErrorReturnCode.ERROR_CONNECT_OUT_RWK);
			resp.setReturnMsg("计费平台同内部业务平台通信异常");;
		}
		log.info("收到内部业务平台返回的响应-返回值为："+resp.getReturnCode());
		return resp;
	}
	
	/**
	 * 封装内部业务平台支付通知请求
	 * @param payResultNotifyReq
	 * @return
	 */
	private PayOrderResultNotifyReq toPayResultNotifyReqs(PayResultNotifyReq payResultNotifyReq,String orderId) {
		PayOrderResultNotifyReq payResultNotifyReqs = new PayOrderResultNotifyReq();
		payResultNotifyReqs.setMsgType("PayOrderResultNotifyReq");
		payResultNotifyReqs.setVersion("1.0.0");
		payResultNotifyReqs.setOrderId(orderId);
		payResultNotifyReqs.setTradeId(payResultNotifyReq.getOrderId());
		payResultNotifyReqs.setPayResult(payResultNotifyReq.getPayResult());
		payResultNotifyReqs.setAmount(payResultNotifyReq.getAmount());
		payResultNotifyReqs.setPayOrganization(payResultNotifyReq.getPayOrganization());
		payResultNotifyReqs.setPayTime(payResultNotifyReq.getPayDate());
		payResultNotifyReqs.setReserved1(payResultNotifyReq.getReserved1());
		payResultNotifyReqs.setPayWay(payResultNotifyReq.getPayWay());
		payResultNotifyReqs.setPayType(payResultNotifyReq.getPayType());
		return payResultNotifyReqs;
	}
}
