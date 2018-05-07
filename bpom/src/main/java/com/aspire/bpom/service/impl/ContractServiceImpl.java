package com.aspire.bpom.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.aspire.bpom.bean.OrderPO;
import com.aspire.bpom.constants.ErrorReturnCode;
import com.aspire.bpom.extensions.exception.BusinessException;
import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.service.ContractService;
import com.aspire.bpom.service.OrderService;
import com.aspire.bpom.service.SignService;
import com.aspire.bpom.util.HttpClientUtil;
import com.aspire.bpom.util.SystemManager;
import com.aspire.bpom.xml.bean.request.ContractNotifyReq;
import com.aspire.bpom.xml.bean.request.EntrustPayNotifyReq;
import com.aspire.bpom.xml.bean.response.ContractNotifyResp;
import com.aspire.bpom.xml.common.ParseXMLHelper;

@Component("contractService")
public class ContractServiceImpl implements ContractService{
	
	@Resource
	private SignService signService;
	@Resource
	private OrderService orderService;
	@Resource
	private SystemManager sm;

	private static final Logger log = BpomLogger.getLogger(ContractService.class);

	public ContractNotifyResp contractToRWK(EntrustPayNotifyReq entrustPayNotifyReq) throws BusinessException{
		//组装签约&解约结果通知给内部业务平台
		ContractNotifyReq contractNotifyReq = toContractNotifyReq(entrustPayNotifyReq);
		log.info("构造通知内部业务平台的参数为："+contractNotifyReq.toString());

		ContractNotifyResp contractNotifyResp = postContractNotify(contractNotifyReq);
		
		return contractNotifyResp;
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


}
