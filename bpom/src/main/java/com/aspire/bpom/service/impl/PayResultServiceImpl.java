package com.aspire.bpom.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.aspire.bpom.constants.ErrorReturnCode;
import com.aspire.bpom.extensions.exception.BusinessException;
import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.service.OrderService;
import com.aspire.bpom.service.PayResultService;
import com.aspire.bpom.service.PaySignService;
import com.aspire.bpom.util.HttpClientUtil;
import com.aspire.bpom.util.SystemManager;
import com.aspire.bpom.xml.bean.request.PayOrderResultNotifyReq;
import com.aspire.bpom.xml.bean.request.PayResultNotifyReq;
import com.aspire.bpom.xml.bean.response.PayOrderResultNotifyResp;
import com.aspire.bpom.xml.common.ParseXMLHelper;

@Component("payResultService")
public class PayResultServiceImpl implements PayResultService{

	@Resource
	private OrderService orderService;
	@Resource
	private SystemManager sm;

	private static final Logger logger = BpomLogger.getLogger(PaySignService.class);

	public PayOrderResultNotifyResp payResult(PayResultNotifyReq payResultNotifyReq,String tradeId) throws BusinessException{
		//组装请求内部业务系统报文
		PayOrderResultNotifyReq payResultReq = toPayResultNotifyReqs(payResultNotifyReq,tradeId);
		logger.info("构造请求内部业务系统的参数为："+payResultReq.toString());
		PayOrderResultNotifyResp payResultResps = postPayResult(payResultReq);
		
		return payResultResps;
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
			logger.error("内部业务平台响应异常："+e);
			resp = new PayOrderResultNotifyResp();
			resp.setReturnCode(ErrorReturnCode.ERROR_CONNECT_OUT_RWK);
			resp.setReturnMsg("计费平台同内部业务平台通信异常");
		}
		logger.info("收到内部业务平台返回的响应-返回值为："+resp.getReturnCode());
		return resp;
	}
	
	/**
	 * 封装内部业务平台支付通知请求
	 * @param payResultNotifyReq
	 * @return
	 */
	private PayOrderResultNotifyReq toPayResultNotifyReqs(PayResultNotifyReq payResultNotifyReq,String tradeId) {
		PayOrderResultNotifyReq payResultNotifyReqs = new PayOrderResultNotifyReq();
		payResultNotifyReqs.setMsgType("PayResultNotifyReq");
		payResultNotifyReqs.setVersion("1.0.0");
		payResultNotifyReqs.setOrderId(payResultNotifyReq.getOrderId());
		payResultNotifyReqs.setTradeId(tradeId);
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
