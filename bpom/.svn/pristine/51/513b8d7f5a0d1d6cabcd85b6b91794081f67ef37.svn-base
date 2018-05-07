package com.aspire.bpom.web;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.bpom.service.CloseOrderPayService;
import com.aspire.bpom.service.OrderService;
import com.aspire.bpom.bean.OrderPO;
import com.aspire.bpom.constants.ErrorReturnCode;
import com.aspire.bpom.constants.OrderStatus;
import com.aspire.bpom.constants.ReturnCode;
import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.extensions.log4j.ThreadLocalTransactionID;
import com.aspire.bpom.util.MD5Hex;
import com.aspire.bpom.util.SystemManager;
import com.aspire.bpom.xml.bean.request.ClosePayReq;
import com.aspire.bpom.xml.bean.response.ClosePayOrderResp;
import com.aspire.bpom.xml.bean.response.ClosePayResp;
import com.aspire.bpom.xml.common.ParseXMLHelper;

/**
 * 关闭订单接口
 * @author chenpeng
 *
 */
@Controller
public class CloseOrderPayController {

	private static final Logger log = BpomLogger.getLogger(CloseOrderPayController.class);

	@Resource
	private CloseOrderPayService CloseOrderPayService;
	@Resource
	private OrderService orderService;
	@Resource
	private SystemManager sm;


	@RequestMapping("/weixin/closeOrderPay")
	@ResponseBody
	public String closeOrderPay(@RequestBody String xml) {
		ClosePayReq closePayReq = ParseXMLHelper.parseXMLToObject(xml, ClosePayReq.class);
		String sessionId = closePayReq.getTradeId();
		String logSession = ThreadLocalTransactionID.generateTransactionID(sessionId);
		ThreadLocalTransactionID.setTransactionID(logSession);
		
		log.info("收到内部业务平台请求的关闭订单消息报文为："+ xml);
		
		String returnXml = null;
		ClosePayResp closePayResp =  new ClosePayResp();

		try {
			//校验请求参数
			if (checkParameter(closePayReq)) {
				//进行签名校验TODO
				// 验证签名
				boolean b = MD5Hex.checkSign(closePayReq, sm.getSecretKeyRWK());
				if (b) {
					//根据tradeId查询订单状态
					OrderPO orderPO = orderService.getOrderById(null, closePayReq.getTradeId());
					if (orderPO == null) {
						log.error("查询不到订单信息");
						closePayResp.setReturnCode(ErrorReturnCode.ORDER_NOT_EXIST);
						closePayResp.setReturnMsg("未查到订单信息");
					} else {
						if (orderPO.getState().equals(OrderStatus.CLOSE)) {
							log.error("订单已关闭");
							closePayResp.setReturnCode(ErrorReturnCode.ORDER_CLOSED);
							closePayResp.setReturnMsg("订单已关闭");
						} else {
							//业务处理
							ClosePayOrderResp closePayOrderResp = CloseOrderPayService.closePayOrderRespToPayGate(closePayReq,orderPO.getRequestId());
							if (ReturnCode.SUCCESS.equals(closePayOrderResp.getReturnCode())) {
								closePayResp.setReturnCode(closePayOrderResp.getReturnCode());
								closePayResp.setOrderId(orderPO.getOutOrderId());
								closePayResp.setTradeId(orderPO.getTradeId());
								orderPO.setRequestId(closePayOrderResp.getRequestId());
								orderPO.setState(OrderStatus.CLOSE);
								log.info("支付网关响应返回成功更新本地订单状态为关闭");
								orderService.updateOrder(orderPO);

							} else if (ReturnCode.FAIL_8.equals(closePayOrderResp.getReturnCode())){
								closePayResp.setReturnCode(ReturnCode.FAIL);
								closePayResp.setReturnMsg("订单已支付，不能发起关单");
							} else if (ReturnCode.FAIL_9.equals(closePayOrderResp.getReturnCode())) {
								closePayResp.setReturnCode(ReturnCode.FAIL);
								closePayResp.setReturnMsg("订单已关闭，无法重复关闭");
							} else {
								closePayResp.setReturnCode(ReturnCode.FAIL);
								closePayResp.setReturnMsg("关闭订单失败");
							}
						}
					}
				} else {
					log.error("验证签名错误");
					closePayResp.setReturnCode(ErrorReturnCode.CHECK_SIGN_ERROR);
					closePayResp.setReturnMsg("验证签名错误");
				}

			} else {
				//参数校验不正确返回
				closePayResp.setReturnCode(ErrorReturnCode.ERROR_REQUEST_PARAM);
				closePayResp.setReturnMsg("请求参数检验出错");
			}
		} catch (Exception e) {
			log.error("关闭订单处理异常");
			closePayResp.setReturnCode(ErrorReturnCode.EXCEPTION);
			closePayResp.setReturnMsg("关闭订单处理异常");
		}
		closePayResp.setMsgType("ClosePayResp");
		closePayResp.setVersion("1.0.0");;
		returnXml = ParseXMLHelper.object2XML(ClosePayResp.class, closePayResp);
		log.info("响应给内部业务平台的消息报文为："+returnXml);
		return returnXml;
	}

	/**
	 * 校验请求参数
	 * @param closePayReq
	 * @return
	 */
	private boolean checkParameter(ClosePayReq closePayReq) {
		boolean flag = false;
		if (!"ClosePayReq".equals(closePayReq.getMsgType())) {
			log.error("消息类型不为ClosePayReq");
		} else if (!"1.0.0".equals(closePayReq.getVersion())) {
			log.error("消息版本号不为1.0.0");
		} else if (StringUtils.isEmpty(closePayReq.getSystemCode())){
			log.error("内部系统代码不能为空");
		} else if (StringUtils.isEmpty(closePayReq.getOrderId())) {
			log.error("订单id不为空");
		} else if (StringUtils.isEmpty(closePayReq.getTradeId())) {
			log.error("外部交易id不能为空");
		} else if (StringUtils.isEmpty(closePayReq.getHmac())) {
			log.error("数字签名不能为空");
		} else {
			flag = true;
		}

		return flag;
	}
}
