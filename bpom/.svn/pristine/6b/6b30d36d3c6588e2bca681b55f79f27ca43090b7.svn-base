package com.aspire.bpom.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.aspire.bpom.bean.OrderPO;
import com.aspire.bpom.bean.SignPO;
import com.aspire.bpom.constants.ErrorReturnCode;
import com.aspire.bpom.constants.OrderStatus;
import com.aspire.bpom.constants.PayStatus;
import com.aspire.bpom.constants.ReturnCode;
import com.aspire.bpom.constants.SignStatus;
import com.aspire.bpom.extensions.exception.BusinessException;
import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.service.OrderService;
import com.aspire.bpom.service.PaySignService;
import com.aspire.bpom.service.SignService;
import com.aspire.bpom.util.DateTimeUtil;
import com.aspire.bpom.util.HttpClientUtil;
import com.aspire.bpom.util.ListMap;
import com.aspire.bpom.util.MD5Hex;
import com.aspire.bpom.util.RandomUtil;
import com.aspire.bpom.util.SystemManager;
import com.aspire.bpom.xml.bean.request.BeginPayReq;
import com.aspire.bpom.xml.bean.request.PaySignReq;
import com.aspire.bpom.xml.bean.response.BeginPayResp;
import com.aspire.bpom.xml.common.ParseXMLHelper;

@Component("paySignService")
public class PaySignServiceImpl implements PaySignService{


	
	@Resource
	private OrderService orderService;
	@Resource
	private SignService signService;
	@Resource
	private SystemManager sm;

	private static final Logger logger = BpomLogger.getLogger(PaySignService.class);
	
	public BeginPayResp paySignToPayGate(PaySignReq paysignReq) throws BusinessException{
		
		//保存订单信息
		OrderPO order = toOrder(paysignReq);
		orderService.insertOrder(order);
		orderService.insertOrderHis(order);
		logger.info("保存订单信息，订单流水信息成功。");
		if(paysignReq.getPayWay().matches("^.*?-CIP$")){
			//保存签约信息
			SignPO sign = toSign(paysignReq);
			signService.insertIntoSign(sign);
			logger.info("签约信息保存成功。");
		}
		//组装请求支付网关报文
		BeginPayReq beginPayReq = toBeginPayReq(paysignReq);
		logger.info("构造请求支付网关的参数为："+beginPayReq.toString());

		BeginPayResp beginPayResp = postBeginPaySign(beginPayReq);
		order.setTradeId(paysignReq.getTradeId());
		logger.info("支付网关响应：requestId："+beginPayResp.getRequestId()+",tradeId:"+beginPayResp.getOrderId());
		if ( beginPayResp.getReturnCode().equals(ReturnCode.SUCCESS)) {
			//更新订单表requestId
			order.setRequestId(beginPayResp.getRequestId());
			order.setPayStatus(PayStatus.PAY_STATUS_10);//待支付
			orderService.updateOrder(order);
			orderService.insertOrderHis(order);
			logger.info("记录订单流水成功");
		} else {
			order.setState(OrderStatus.FAIL);
			orderService.updateOrder(order);
			orderService.insertOrderHis(order);
			logger.info("更新订单成功");
			logger.info("记录订单流水成功");
		}

		return beginPayResp;
	}
	
	/**
	 * 请求支付网关支付并签约接口
	 * @param beginPayReq
	 * @return
	 */
	private BeginPayResp postBeginPaySign(BeginPayReq beginPayReq){
		String begPayReqXml = ParseXMLHelper.object2XML(BeginPayReq.class, beginPayReq);
		BeginPayResp resp = null;
		try {
		String begPayRespXml = HttpClientUtil.doHttpResult(sm.getPaysignUrl()//请求支付网关签约接口
				, begPayReqXml);
		resp = ParseXMLHelper.parseXMLToObject(begPayRespXml, BeginPayResp.class);
		} catch (Exception e) {
			logger.error("e:"+e);
			resp =  new BeginPayResp();
			resp.setReturnCode(ErrorReturnCode.ERROR_CONNECT_OUT_PAYGATE);
			resp.setReturnMsg("支付网关通信异常");
		}
		logger.info("收到支付网关返回的响应-返回值为："+resp.getReturnCode());
		return resp;
	}
	
	/**
	 * 封装订单信息
	 * @param paysignReq
	 * @param orderId
	 * @param orderTime
	 * @return
	 */
	private OrderPO toOrder(PaySignReq paysignReq) {
		OrderPO orderPO = new OrderPO();
		String orderId = DateTimeUtil.getCurrentTime(false) + RandomUtil.generateNumberString(6);
		orderPO.setOrderId(orderId);	//计费模块内部订单id
		orderPO.setOrderTime(paysignReq.getOrderTime());
		orderPO.setSystemCode(paysignReq.getSystemCode());
		orderPO.setServPltfmCode(paysignReq.getServPltfmCode());
		orderPO.setPayWay(paysignReq.getPayWay());
		orderPO.setPayOrganization(paysignReq.getPayOrganization());
		orderPO.setPayType(Integer.valueOf(paysignReq.getPayType()));
		orderPO.setIpAddress(paysignReq.getIpAddress());
		orderPO.setCallbackUrl(paysignReq.getCallbackUrl());
		orderPO.setOutOrderId(paysignReq.getOrderId());
		orderPO.setTradeId(paysignReq.getTradeId());
		orderPO.setUserId(paysignReq.getUserId());
		orderPO.setMerchantName(paysignReq.getMerchantName());
		orderPO.setProductId(paysignReq.getProductId());
		orderPO.setProductName(paysignReq.getProductName());
		orderPO.setProductDesc(paysignReq.getProductDesc());
		orderPO.setChannelId(paysignReq.getChannelId());
		orderPO.setAmount(Integer.valueOf(paysignReq.getAmount()));
		orderPO.setReserved1(paysignReq.getReserved1());
		orderPO.setProductURL(paysignReq.getProductURL());
		orderPO.setWxOpenid(paysignReq.getWxOpenid());
		orderPO.setPayStatus(PayStatus.UN_PAY_SUBMIT);//支付状态：订单未提交支付请求
		orderPO.setState(OrderStatus.PREPARING);//订单状态：预备
		return orderPO;
	}
	
	/**
	 * 封装签约信息
	 * @param paysignReq
	 * @return
	 */
	private SignPO toSign(PaySignReq paysignReq) {
		SignPO signPO = new SignPO();
		signPO.setContractCode(paysignReq.getWxContract_code());//签约协议号
		signPO.setPlanId(paysignReq.getWxPlan_id());//模板号
		signPO.setDisplayName(paysignReq.getWxContract_display());//展示名称
		signPO.setSystemCode(paysignReq.getSystemCode());//内部系统代码
		signPO.setServPltfmCode(paysignReq.getServPltfmCode());//内部业务平台代码
		signPO.setUserId(paysignReq.getUserId());//用户id
		signPO.setOpenId(paysignReq.getWxOpenid());//微信用户标识
		signPO.setProductId(paysignReq.getProductId());//产品代码
		signPO.setProductName(paysignReq.getProductName());//产品名
		signPO.setProductDesc(paysignReq.getProductDesc());//对商品的描述
		signPO.setChannelId(paysignReq.getChannelId());//渠道代码
		signPO.setReserved1(paysignReq.getReserved1());//交易返回时原样返回给商家
		signPO.setProductURL(paysignReq.getProductURL());//商品展示链接地址
		signPO.setExpiredTime(DateTimeUtil.getCurrentTime());//协议到期时间TODO
		signPO.setState(SignStatus.UN_SIGN);//0:未签约
		return signPO;
	};
	
	/**
	 * 封装支付网关请求
	 * @param paysignReq
	 * @return
	 */
	private BeginPayReq toBeginPayReq(PaySignReq paysignReq) {
		BeginPayReq beginPayReq = new BeginPayReq();
		beginPayReq.setMsgType("BeginPayReq");
		beginPayReq.setMsgVer("1.0");
		beginPayReq.setSystemCode(paysignReq.getSystemCode());
		beginPayReq.setServPltfmCode(paysignReq.getServPltfmCode());
		beginPayReq.setPayWay(paysignReq.getPayWay());
		beginPayReq.setPayOrganization(paysignReq.getPayOrganization());
		beginPayReq.setPayType(Integer.valueOf(paysignReq.getPayType()));
		beginPayReq.setIpAddress(paysignReq.getIpAddress());
		beginPayReq.setCallbackUrl(paysignReq.getCallbackUrl());
		beginPayReq.setUserId(paysignReq.getUserId());
		beginPayReq.setOrderId(paysignReq.getTradeId());
		beginPayReq.setAmount(Integer.valueOf(paysignReq.getAmount()));
		beginPayReq.setOrderDate(paysignReq.getOrderTime());
		beginPayReq.setPeriod(Integer.valueOf(paysignReq.getPeriod()));//数字，订单有效期，取值待定
		beginPayReq.setPeriodUnit(paysignReq.getPeriodUnit());//只能取以下枚举值：00- 分 01- 小时 02- 日 03- 月  取值待定
		beginPayReq.setMerchantName(paysignReq.getMerchantName());
		beginPayReq.setProductId(paysignReq.getProductId());
		beginPayReq.setProductName(paysignReq.getProductName());
		beginPayReq.setProductDesc(paysignReq.getProductDesc());
		beginPayReq.setReserved1(paysignReq.getReserved1());
		beginPayReq.setProductURL(paysignReq.getProductURL());
		beginPayReq.setWxOpenid(paysignReq.getWxOpenid());
		beginPayReq.setWxPlan_id(paysignReq.getWxPlan_id());
		beginPayReq.setWxContract_code(paysignReq.getWxContract_code());
		beginPayReq.setWxContract_display(paysignReq.getWxContract_display());
		beginPayReq.setWxContract_id(paysignReq.getWxContract_id());
		
		//生成给支付网关签名
		ListMap<String, String> map =  MD5Hex.createMap(beginPayReq);
		logger.info("map:"+map.toString());
		String hmac = MD5Hex.getHmac(map, sm.getSecretKeyBPOM());
		logger.info("计费平台生成给支付网关的签名为hmac："+hmac);
		beginPayReq.setHmac(hmac);
		return beginPayReq;
	}

}
