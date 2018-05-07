package com.aspire.bpom.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.aspire.bpom.bean.OrderPO;
import com.aspire.bpom.constants.ErrorReturnCode;
import com.aspire.bpom.extensions.exception.BusinessException;
import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.service.OrderService;
import com.aspire.bpom.service.QueryPayStatusService;
import com.aspire.bpom.util.HttpClientUtil;
import com.aspire.bpom.util.SystemManager;
import com.aspire.bpom.xml.bean.request.QryPayReq;
import com.aspire.bpom.xml.bean.request.QueryPayStatusReq;
import com.aspire.bpom.xml.bean.response.QueryPayStatusResp;
import com.aspire.bpom.xml.common.ParseXMLHelper;

@Component("queryPayStatusService")
public class QueryPayStatusServiceImpl implements QueryPayStatusService{

	private static final Logger log = BpomLogger.getLogger(QueryPayStatusServiceImpl.class);

	@Resource
	private SystemManager sm;
	
	@Resource
	private OrderService orderService;
	
	public QueryPayStatusResp queryPayStatsusToPayGate(QryPayReq qryPayReq)  throws BusinessException{
		//组装请求支付网关报文
		QueryPayStatusReq queryPayStatusReq = toQueryPayStatusReq(qryPayReq);
		log.info("构造请求支付网关的参数为："+queryPayStatusReq.toString());

		QueryPayStatusResp queryPayStatusResp = postQueryPayStatus(queryPayStatusReq);
		
		return queryPayStatusResp;
	}
	
	/**
	 * 请求支付网关支付并签约接口
	 * @param beginPayReq
	 * @return
	 */
	private QueryPayStatusResp postQueryPayStatus(QueryPayStatusReq queryPayStatusReq){
		String queryPayStatusReqXml = ParseXMLHelper.object2XML(QueryPayStatusReq.class, queryPayStatusReq);
		QueryPayStatusResp resp = null;
		try{
			String queryPayStatusRespXml = HttpClientUtil.doHttpResult(sm.getQuerypaystatusUrl()//请求支付网关支付状态查询接口
					, queryPayStatusReqXml);
			resp = ParseXMLHelper.parseXMLToObject(queryPayStatusRespXml, QueryPayStatusResp.class);
		}catch(Exception e){
			log.error("支付网关响应异常："+e);
			resp = new QueryPayStatusResp();
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
	private QueryPayStatusReq toQueryPayStatusReq(QryPayReq qryPayReq) {
		QueryPayStatusReq queryPayStatusReq = new QueryPayStatusReq();
		queryPayStatusReq.setMsgType("QueryPayStatusReq");
		queryPayStatusReq.setMsgVer("1.0");
		queryPayStatusReq.setSystemCode(qryPayReq.getSystemCode());
		//根据orderId或者tradeId查询订单表获取订单信息
		OrderPO OrderPO = orderService.getOrderById(null, qryPayReq.getTradeId());
		if (OrderPO != null) {
			queryPayStatusReq.setRequestId(OrderPO.getRequestId());
			queryPayStatusReq.setOrderId(OrderPO.getTradeId());
		} else {
			log.error("根据外部交易tradeId:"+qryPayReq.getTradeId()+",查询不到订单信息");
			queryPayStatusReq.setOrderId(qryPayReq.getTradeId());
		}
		return queryPayStatusReq;
	}

}
