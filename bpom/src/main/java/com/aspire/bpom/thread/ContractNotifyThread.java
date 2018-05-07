package com.aspire.bpom.thread;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.aspire.bpom.bean.OrderPO;
import com.aspire.bpom.constants.ErrorReturnCode;
import com.aspire.bpom.constants.ReturnCode;
import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.extensions.log4j.ThreadLocalTransactionID;
import com.aspire.bpom.queue.ContractNotifyQueue;
import com.aspire.bpom.service.OrderService;
import com.aspire.bpom.util.DateUtil;
import com.aspire.bpom.util.HttpClientUtil;
import com.aspire.bpom.util.SystemManager;
import com.aspire.bpom.xml.bean.request.ContractNotifyReq;
import com.aspire.bpom.xml.bean.request.EntrustPayNotifyReq;
import com.aspire.bpom.xml.bean.response.ContractNotifyResp;
import com.aspire.bpom.xml.common.ParseXMLHelper;

@Component
@Scope("prototype")
public class ContractNotifyThread implements Runnable{

	private static final Logger log = BpomLogger.getLogger(ContractNotifyThread.class);

	@Resource
	private SystemManager sm;
	@Resource
	private OrderService orderService;
	@Resource
	private ContractNotifyQueue contractNotifyQueue;

	private EntrustPayNotifyReq entrustPayNotifyReq;
	public void setNotify(EntrustPayNotifyReq entrustPayNotifyReq){
		this.entrustPayNotifyReq = entrustPayNotifyReq;
	}
	
	@Override
	public void run() {
		try {
			ThreadLocalTransactionID.setTransactionID(entrustPayNotifyReq.getSessionId());
			if (contractNotify(entrustPayNotifyReq)) {	
				log.info("通知签约结果给内部业务平台成功");
			} else {
				resendNotice();
			}			
		} catch (Exception e) {
			log.error("通知签约结果给内部业务平台异常" + e);
			e.printStackTrace();
			resendNotice();
		}
	}

	/**
	 * 重发签约结果通知
	 */
	private void resendNotice(){
		log.info("通知签约结果给内部业务平台失败");
		Date currTime = new Date();//获取当前时间
		//获取定时次数
		Integer timingNumber = entrustPayNotifyReq.getTimingNumber() + 1;
		entrustPayNotifyReq.setTimingNumber(timingNumber);//设定定时次数
		//获取重发时间
		Integer resendNoticeTime = Integer.valueOf(sm.getRetransmissionNoticeTime().split(",")[timingNumber > 3 ? 3 : timingNumber]);	
		//设定每次的重发启动时间
		entrustPayNotifyReq.setScheduledTime(DateUtil.addMinute(currTime, resendNoticeTime));
		contractNotifyQueue.offer(entrustPayNotifyReq);
	}
	
	/**
	 * 向内部业务平台发送签约结果通知
	 */
	private boolean contractNotify(EntrustPayNotifyReq entrustPayNotifyReq) {
		boolean bet = false;
		
		//组装签约&解约结果通知给内部业务平台
		ContractNotifyReq contractNotifyReq = toContractNotifyReq(entrustPayNotifyReq);
		log.info("构造通知内部业务平台的参数为："+contractNotifyReq.toString());

		ContractNotifyResp contractNotifyResp = postContractNotify(contractNotifyReq);
		if (ReturnCode.SUCCESS.equals(contractNotifyResp.getReturnCode())) {
			bet = true;
		}
		return bet;		
		
	}
	
	private ContractNotifyReq toContractNotifyReq(EntrustPayNotifyReq entrustPayNotifyReq) {
		ContractNotifyReq contractNotifyReq = new ContractNotifyReq();
		contractNotifyReq.setMsgType("ContractNotifyReq");
		contractNotifyReq.setVersion("1.0.0");
		contractNotifyReq.setWxPlan_id(entrustPayNotifyReq.getWxPlan_id());
		contractNotifyReq.setContractCode(entrustPayNotifyReq.getWxContract_code());
		contractNotifyReq.setWxContract_id(entrustPayNotifyReq.getWxContract_id());//委托代扣协议id,签约成功后，微信返回的委托代扣协议id
		contractNotifyReq.setChange_type(entrustPayNotifyReq.getChange_type());
		contractNotifyReq.setExpiredTime(entrustPayNotifyReq.getWxContract_expired());
		contractNotifyReq.setOperateTime(entrustPayNotifyReq.getWxOperate_time());
		contractNotifyReq.setWxOpenid(entrustPayNotifyReq.getWxOpenid());
		//根据requestId（支付网关交易流水号）查询订单号和外部交易ID
		OrderPO orderPO = orderService.getOrderById(entrustPayNotifyReq.getRequestId(),null);
		if (orderPO != null) {
			contractNotifyReq.setOrderId(orderPO.getOrderId());
			contractNotifyReq.setTradeId(orderPO.getTradeId());
		} else {
			log.error("根据支付网关流水requestId:"+entrustPayNotifyReq.getRequestId()+",查询不到订单记录");
		}
		return contractNotifyReq;
	}

	/**
	 * 请求内部业务平台签约/解约通知接口
	 * @param beginPayReq
	 * @return
	 */
	private ContractNotifyResp postContractNotify(ContractNotifyReq contractNotifyReq){
		String contractNotifyReqXml = ParseXMLHelper.object2XML(ContractNotifyReq.class, contractNotifyReq);
		ContractNotifyResp resp = null;
		try{
			String contractNotifyRespXml = HttpClientUtil.doHttpResult(sm.getContractnotifyUrl()//请求内部业务平台签约/解约地址
					, contractNotifyReqXml);
			resp = ParseXMLHelper.parseXMLToObject(contractNotifyRespXml, ContractNotifyResp.class);
		}catch(Exception e){
			log.error("内部业务平台响应异常："+e);
			resp = new ContractNotifyResp();
			resp.setReturnCode(ErrorReturnCode.ERROR_CONNECT_OUT_RWK);
			resp.setReturnMsg("计费平台同内部业务平台通信异常");
			return resp;
		}
		log.info("收到内部业务平台返回的响应-返回值为："+resp.getReturnCode());
		return resp;
	}
}
