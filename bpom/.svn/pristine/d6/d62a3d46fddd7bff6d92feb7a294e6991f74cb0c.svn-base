package com.aspire.bpom.web;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.bpom.bean.OrderPO;
import com.aspire.bpom.constants.ErrorReturnCode;
import com.aspire.bpom.constants.OrderStatus;
import com.aspire.bpom.constants.PayResultStatus;
import com.aspire.bpom.constants.PayStatus;
import com.aspire.bpom.constants.ReturnCode;
import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.extensions.log4j.ThreadLocalTransactionID;
import com.aspire.bpom.service.OrderService;
import com.aspire.bpom.service.PayResultService;
import com.aspire.bpom.thread.NotifyThreadPool;
import com.aspire.bpom.xml.bean.request.PayResultNotifyReq;
import com.aspire.bpom.xml.bean.response.PayResultNotifyResp;
import com.aspire.bpom.xml.common.ParseXMLHelper;

/**
 * 支付结果通知接口
 * @author chenpeng
 *
 */
@Controller
public class PayResultNotifyController {

	private static final Logger log = BpomLogger.getLogger(PayResultNotifyController.class);

	@Resource
	private PayResultService payResultService;
	@Resource
	private OrderService orderService;
	
	@Resource
	private NotifyThreadPool notifyThreadPool;
	
	@RequestMapping(value = "/weixin/payResultNotify")
	@ResponseBody
	public String payResultNotify(@RequestBody String xml) {

		PayResultNotifyReq payResultNotifyReq = ParseXMLHelper.parseXMLToObject(xml, PayResultNotifyReq.class);
		String sessionId = payResultNotifyReq.getOrderId();
		String logSession = ThreadLocalTransactionID.generateTransactionID(sessionId);
		ThreadLocalTransactionID.setTransactionID(logSession);
		log.info("收到支付网关请求的消息报文为:"+ xml);
		
		//1.校验请求报文合法性。必填字段
		String returnXml = null;
		PayResultNotifyResp payResultResp =  new PayResultNotifyResp();

		try {
			if (checkParameter(payResultNotifyReq)) {
				//更新本地订单状态，根据订单号查询本地订单更新
 				OrderPO order = orderService.getOrderById(payResultNotifyReq.getRequestId(),payResultNotifyReq.getOrderId());
				if (order == null) {
					log.error("查询不到订单信息");
					payResultResp.setReturnCode(ReturnCode.FAIL);
					payResultResp.setReturnMsg("查询不到订单信息");
				} else {
					//支付成功
					if (PayResultStatus.PAY_SUCCESS.equals(payResultNotifyReq.getPayResult())) {
						//设置支付状态
						order.setPayStatus(PayStatus.PAY_STATUS_20);
						//设置订单状态
						order.setState(OrderStatus.SUCCESS);
						//设置支付时间
						order.setPayTime(payResultNotifyReq.getPayDate());
						//对账补单支付成功	
					} else if (PayResultStatus.FILL_BILL_PAY_SUCCESS.equals(payResultNotifyReq.getPayResult())) {
						//设置支付状态
						order.setPayStatus(PayStatus.PAY_STATUS_50);
						//设置订单状态
						order.setState(OrderStatus.SUCCESS);
						//支付失败
					}  else {
						//设置支付状态
						order.setPayStatus(PayStatus.PAY_STATUS_30);
						//设置订单状态
						order.setState(OrderStatus.FAIL);
					}
					log.info("订单状态更新为："+order.getState());
					orderService.updateOrder(order);

					log.info("记录订单流水");
					orderService.insertOrderHis(order);
					
//					payResultService.payResult(payResultNotifyReq, order.getTradeId());
					payResultNotifyReq.setTimingNumber(0);//设置重发通知的次数
					payResultNotifyReq.setSessionId(logSession);
					notifyThreadPool.execute(payResultNotifyReq, order.getOutOrderId());
					log.info("发送通知成功，启动延时通知线程");
					payResultResp.setReturnCode(ReturnCode.SUCCESS);
					payResultResp.setReturnMsg("");
				}
			} else {
				//参数校验不正确返回
				payResultResp.setReturnCode(ErrorReturnCode.ERROR_REQUEST_PARAM);
				payResultResp.setReturnMsg("通知参数检验出错");
			}
		} catch (Exception e) {
			log.error("支付结果通知处理异常");
			payResultResp.setReturnCode(ErrorReturnCode.EXCEPTION);
			payResultResp.setReturnMsg("支付结果通知处理异常");
		}
		payResultResp.setMsgType("PayResultNotifyResp");
		payResultResp.setMsgVer("1.0");;
		returnXml = ParseXMLHelper.object2XML(PayResultNotifyResp.class, payResultResp);
		log.info("响应给支付网关的数据为："+returnXml);
		return returnXml;
	}
	
	/**
	 * 请求参数校验
	 * 
	 * @return
	 */
	private boolean checkParameter(PayResultNotifyReq payResultReq) {
		boolean flag = false;
		if (!"PayResultNotifyReq".equals(payResultReq.getMsgType())) {
			log.error("消息类型不为PayResultNotifyReq");
		} else if (!"1.0".equals(payResultReq.getMsgVer())) {
			log.error("消息版本号不为1.0");
		} else if (StringUtils.isEmpty(payResultReq.getRequestId())) {
			log.error("交易流水号不能为空");
		} else if (StringUtils.isEmpty(payResultReq.getOrderId())) {
			log.error("商户订单 id不能为空");
		} else if (StringUtils.isEmpty(payResultReq.getPayResult())) {
			log.error("支付结果不能为空");
		} else if (StringUtils.isEmpty(payResultReq.getPayWay())) {
			log.error("支付通道不能为空");
		} else if (StringUtils.isEmpty(payResultReq.getPayType())) {
			log.error("支付方式不能为空");
		} else {
			flag = true;
		}
		
		return flag;
	}
	
}
