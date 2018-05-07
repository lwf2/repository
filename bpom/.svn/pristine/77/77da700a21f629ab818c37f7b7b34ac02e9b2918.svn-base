package com.aspire.bpom.web;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang3.StringUtils;

import com.aspire.bpom.bean.OrderPO;
import com.aspire.bpom.constants.ErrorReturnCode;
import com.aspire.bpom.constants.PayStatus;
import com.aspire.bpom.constants.RWKRefundResultStatus;
import com.aspire.bpom.constants.ReturnCode;
import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.extensions.log4j.ThreadLocalTransactionID;
import com.aspire.bpom.service.OrderService;
import com.aspire.bpom.service.QueryPayStatusService;
import com.aspire.bpom.util.DateTimeUtil;
import com.aspire.bpom.util.MD5Hex;
import com.aspire.bpom.util.SystemManager;
import com.aspire.bpom.xml.bean.request.QryPayReq;
import com.aspire.bpom.xml.bean.response.QryPayResp;
import com.aspire.bpom.xml.bean.response.QueryPayStatusResp;
import com.aspire.bpom.xml.common.ParseXMLHelper;

/**
 * 支付状态查询
 * @author chenpeng
 *
 */
@Controller
public class QueryPayStatusController {

	private static final Logger log = BpomLogger.getLogger(PaySignController.class);

	@Resource
	private QueryPayStatusService queryPayStatusService;
	@Resource
	private OrderService orderService;
	@Resource
	private SystemManager sm;

	@RequestMapping("/weixin/queryPayStatus")
	@ResponseBody
	public String queryPayStatus(@RequestBody String xml) {
		QryPayReq qryPayReq = ParseXMLHelper.parseXMLToObject(xml, QryPayReq.class);

		String sessionId = qryPayReq.getTradeId();
		String logSession = ThreadLocalTransactionID.generateTransactionID(sessionId);
		ThreadLocalTransactionID.setTransactionID(logSession);
		log.info("接收到内部业务平台请求过来的支付查询报文为："+xml);
		
		String returnXml = null;
		QryPayResp qryPayResp =  new QryPayResp();
		try {
			//1.校验请求参数
			if (checkParam(qryPayReq)) {
				log.info("请求参数合法");
				//进行数字签名TODO
				// 验证签名
				boolean b = MD5Hex.checkSign(qryPayReq, sm.getSecretKeyRWK());
				if (b) {
					OrderPO orderPO = orderService.getOrderById(null, qryPayReq.getTradeId());
					if (orderPO != null) {
						//进行业务处理
						QueryPayStatusResp queryPayStatusResp =	queryPayStatusService.queryPayStatsusToPayGate(qryPayReq);
						if (ReturnCode.SUCCESS.equals(queryPayStatusResp.getReturnCode())) {
							//设置orderId
							qryPayResp.setOrderId(orderPO.getOutOrderId());
							qryPayResp.setReturnCode(ReturnCode.SUCCESS);
							qryPayResp.setTradeId(queryPayStatusResp.getOrderId());
							qryPayResp.setPayStatus(queryPayStatusResp.getPayStatus());
							qryPayResp.setAmount(queryPayStatusResp.getAmount());
							if (PayStatus.PAY_STATUS_20.equals(queryPayStatusResp.getPayStatus()) 
									||PayStatus.PAY_STATUS_50.equals(queryPayStatusResp.getPayStatus()) 
									||PayStatus.PAY_STATUS_60.equals(queryPayStatusResp.getPayStatus())
									||PayStatus.PAY_STATUS_70.equals(queryPayStatusResp.getPayStatus()) 
									||PayStatus.PAY_STATUS_90.equals(queryPayStatusResp.getPayStatus())) {
								qryPayResp.setPayTime(queryPayStatusResp.getPayDate());
							}
							if (PayStatus.PAY_STATUS_60.equals(queryPayStatusResp.getPayStatus())
									||PayStatus.PAY_STATUS_70.equals(queryPayStatusResp.getPayStatus()) 
									||PayStatus.PAY_STATUS_90.equals(queryPayStatusResp.getPayStatus())) {
								qryPayResp.setRefundAmount(orderPO.getAmount());
								qryPayResp.setRefundDate(DateTimeUtil.format(DateTimeUtil.parse(orderPO.getOperateTime()), false));
								qryPayResp.setRefundStatus(RWKRefundResultStatus.REFUND_SUCCESS);
							}

							//本地状态不一致更新本地表的支付状态
							if (!orderPO.getPayStatus().equals(queryPayStatusResp.getPayStatus())) {
								orderPO.setPayStatus(Integer.valueOf(queryPayStatusResp.getPayStatus()));

								if (PayStatus.PAY_STATUS_20.equals(queryPayStatusResp.getPayStatus()) 
										||PayStatus.PAY_STATUS_50.equals(queryPayStatusResp.getPayStatus()) 
										||PayStatus.PAY_STATUS_60.equals(queryPayStatusResp.getPayStatus())
										||PayStatus.PAY_STATUS_70.equals(queryPayStatusResp.getPayStatus()) 
										||PayStatus.PAY_STATUS_90.equals(queryPayStatusResp.getPayStatus())) {
									orderPO.setPayTime(queryPayStatusResp.getPayDate());
								}

								if (PayStatus.PAY_STATUS_60.equals(queryPayStatusResp.getPayStatus())
										||PayStatus.PAY_STATUS_70.equals(queryPayStatusResp.getPayStatus()) 
										||PayStatus.PAY_STATUS_90.equals(queryPayStatusResp.getPayStatus())) {
									orderPO.setOperateTime(queryPayStatusResp.getRefundDate());
								}

								orderService.updateOrder(orderPO);
							}
						} else if (ErrorReturnCode.ERROR_CONNECT_OUT_PAYGATE==queryPayStatusResp.getReturnCode()){
							qryPayResp.setReturnCode(queryPayStatusResp.getReturnCode());
							qryPayResp.setReturnMsg(queryPayStatusResp.getReturnMsg());
						} else {
							qryPayResp.setReturnCode(ReturnCode.FAIL);
							qryPayResp.setReturnMsg(queryPayStatusResp.getReturnMsg());
						}
						//						}

					} else {
						log.error("查询不到订单信息");
						qryPayResp.setReturnCode(ErrorReturnCode.ORDER_NOT_EXIST);
						qryPayResp.setReturnMsg("查询不到交易订单信息");
					}

				} else {
					log.error("签名校验错误");
					qryPayResp.setReturnCode(ErrorReturnCode.CHECK_SIGN_ERROR);
					qryPayResp.setReturnMsg("签名校验错误");
				}

			} else {
				//参数校验不正确返回
				qryPayResp.setReturnCode(ErrorReturnCode.ERROR_REQUEST_PARAM);
				qryPayResp.setReturnMsg("请求参数检验出错");
			}
		} catch (Exception e) {
			log.error("支付状态查询异常。");
			qryPayResp.setReturnCode(ErrorReturnCode.EXCEPTION);
			qryPayResp.setReturnMsg("支付状态查询异常");

		}
		qryPayResp.setMsgType("QryPayResp");
		qryPayResp.setVersion("1.0.0");
		returnXml = ParseXMLHelper.object2XML(QryPayResp.class, qryPayResp);

		log.info("响应给任我看的消息报文为："+returnXml);
		return returnXml;
	}

	/**
	 * 校验请求参数
	 * @param qryPayReq
	 * @return
	 */
	private boolean checkParam(QryPayReq qryPayReq) {
		boolean flag = false;
		if (!"QryPayReq".equals(qryPayReq.getMsgType())) {
			log.error("消息类型不为QryPayReq");
		} else if (!"1.0.0".equals(qryPayReq.getVersion())) {
			log.error("消息版本号不为1.0.0");
		} else if (StringUtils.isEmpty(qryPayReq.getSystemCode())) {
			log.error("内部系统代码不能为空");
		} else if (StringUtils.isEmpty(qryPayReq.getTradeId())) {
			log.error("外部交易id不能为空");
		} else if (StringUtils.isEmpty(qryPayReq.getHmac())) {
			log.error("数字签名不能为空");
		} else {
			flag = true;
		}

		return flag;
	}

}
