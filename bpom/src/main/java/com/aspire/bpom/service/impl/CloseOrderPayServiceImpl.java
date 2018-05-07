package com.aspire.bpom.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.aspire.bpom.constants.ErrorReturnCode;
import com.aspire.bpom.extensions.exception.BusinessException;
import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.service.CloseOrderPayService;
import com.aspire.bpom.service.OrderService;
import com.aspire.bpom.util.HttpClientUtil;
import com.aspire.bpom.util.SystemManager;
import com.aspire.bpom.xml.bean.request.ClosePayOrderReq;
import com.aspire.bpom.xml.bean.request.ClosePayReq;
import com.aspire.bpom.xml.bean.response.ClosePayOrderResp;
import com.aspire.bpom.xml.common.ParseXMLHelper;

@Component("closeOrderPayService")
public class CloseOrderPayServiceImpl implements CloseOrderPayService{

	private static final Logger log = BpomLogger.getLogger(CloseOrderPayServiceImpl.class);

	@Resource
	private OrderService orderService;

	@Resource
	private SystemManager sm;

	public ClosePayOrderResp closePayOrderRespToPayGate(ClosePayReq closePayReq,String requestId) throws BusinessException{
		//组装请求支付网关报文
		ClosePayOrderReq closePayOrderReq = toClosePayOrderReq(closePayReq,requestId);
		log.info("构造请求支付网关关闭订单的参数为："+closePayOrderReq.toString());

		ClosePayOrderResp closePayOrderResp = postClosePayOrder(closePayOrderReq);
		log.info("收到支付网关响应返回码为："+closePayOrderResp.getReturnCode());

		return closePayOrderResp;
	}

	/**
	 * 请求支付网关支付并签约接口
	 * @param beginPayReq
	 * @return
	 */
	private ClosePayOrderResp postClosePayOrder(ClosePayOrderReq closePayOrderReq){
		String closePayOrderReqXml = ParseXMLHelper.object2XML(ClosePayOrderReq.class, closePayOrderReq);
		ClosePayOrderResp resp = null;
		try{
			String queryPayStatusRespXml = HttpClientUtil.doHttpResult(sm.getCloseorderUrl()//请求支付网关关闭订单接口
					, closePayOrderReqXml);
			resp = ParseXMLHelper.parseXMLToObject(queryPayStatusRespXml, ClosePayOrderResp.class);
		}catch(Exception e){
			log.error("支付网关响应异常："+e);
			resp = new ClosePayOrderResp();
			resp.setReturnCode(ErrorReturnCode.ERROR_CONNECT_OUT_PAYGATE);
			resp.setReturnMsg("计费平台同支付网关通信异常");
		}
		log.info("收到支付网关返回的响应-返回值为："+resp.getReturnCode());
		return resp;
	}


	/**
	 * 封装支付网关请求
	 * @param paysignReq
	 * @return
	 */
	private ClosePayOrderReq toClosePayOrderReq(ClosePayReq closePayReq,String requestId) {
		ClosePayOrderReq closePayOrderReq = new ClosePayOrderReq();
		closePayOrderReq.setMsgType("ClosePayOrderReq");
		closePayOrderReq.setMsgVer("1.0");
		closePayOrderReq.setSystemCode(closePayReq.getSystemCode());
		closePayOrderReq.setOrderId(closePayReq.getOrderId());
		closePayOrderReq.setRequestId(requestId);
		return closePayOrderReq;
	}

}
