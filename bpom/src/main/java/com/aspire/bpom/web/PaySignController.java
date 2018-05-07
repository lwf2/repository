package com.aspire.bpom.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.bpom.constants.ErrorReturnCode;
import com.aspire.bpom.constants.ReturnCode;
import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.extensions.log4j.ThreadLocalTransactionID;
import com.aspire.bpom.service.PaySignService;
import com.aspire.bpom.util.MD5Hex;
import com.aspire.bpom.util.SystemManager;
import com.aspire.bpom.xml.bean.request.PaySignReq;
import com.aspire.bpom.xml.bean.response.BeginPayResp;
import com.aspire.bpom.xml.bean.response.PayPrmts;
import com.aspire.bpom.xml.bean.response.PaySignResp;
import com.aspire.bpom.xml.common.ParseXMLHelper;

/**
 * 支付签约接口
 * @author chenpeng
 *
 */
@Controller
public class PaySignController {

	private static final Logger logger = BpomLogger.getLogger(PaySignController.class);

	@Resource
	private PaySignService paySignService;

	@Resource
	private SystemManager sm;

	@RequestMapping("/weixin/paysign")
	@ResponseBody
	public String paysign(@RequestBody String xml) {
		PaySignReq paysignReq = ParseXMLHelper.parseXMLToObject(xml, PaySignReq.class);
		
		String sessionId = paysignReq.getTradeId();
		String logSession = ThreadLocalTransactionID.generateTransactionID(sessionId);
		ThreadLocalTransactionID.setTransactionID(logSession);
		logger.info("收到内部业务平台请求的消息报文为"+ xml);

		String returnXml = null;
		PaySignResp paySignResp =  new PaySignResp();

		paySignResp.setOrderId(paysignReq.getOrderId());
		paySignResp.setOrderTime(paysignReq.getOrderTime());
		paySignResp.setTradeId(paysignReq.getTradeId());
		try {
			//1.校验请求报文合法性。必填字段
			if (checkParameter(paysignReq)) {
				//TODO进行签名验证
				// 验证签名
				boolean b = MD5Hex.checkSign(paysignReq, sm.getSecretKeyRWK());
				//业务处理
				if (b) {
					BeginPayResp beginPayResp = paySignService.paySignToPayGate(paysignReq);
					paySignResp.setReturnCode(beginPayResp.getReturnCode());
					paySignResp.setReturnMsg(beginPayResp.getReturnMsg());
					if (ReturnCode.SUCCESS.equals(beginPayResp.getReturnCode())) {
						PayPrmts payPrmts = new PayPrmts();
						payPrmts.setP1(beginPayResp.getPayPrmts().getP1());
						payPrmts.setP2(beginPayResp.getPayPrmts().getP2());
						payPrmts.setP3(beginPayResp.getPayPrmts().getP3());
						paySignResp.setPayPrmts(payPrmts);
					}
				} else {
					logger.error("签名校验错误");
					paySignResp.setReturnCode(ErrorReturnCode.CHECK_SIGN_ERROR);
					paySignResp.setReturnMsg("签名校验错误");
				}
			} else {
				//参数校验不正确返回
				paySignResp.setReturnCode(ErrorReturnCode.ERROR_REQUEST_PARAM);
				paySignResp.setReturnMsg("请求参数校验出错");
			}
		}  catch (Exception e) {
			logger.error("支付签约异常"+e);
			paySignResp.setReturnCode(ErrorReturnCode.EXCEPTION);
			paySignResp.setReturnMsg("支付签约异常");
		}
		
		paySignResp.setMsgType("PaySignResp");
		paySignResp.setVersion("1.0.0");

		returnXml = ParseXMLHelper.object2XML(PaySignResp.class, paySignResp);
		logger.info("响应给内部业务平台的报文为："+returnXml);
		return returnXml;

	}

	/**
	 * 请求参数校验
	 * 
	 * @return
	 */
	private boolean checkParameter(PaySignReq paysignReq) {
		boolean flag = false;
		
		if (StringUtils.isEmpty(paysignReq.getOrderTime())) {
			logger.error("请求订单时间为空");
			return flag;
		} else {
			Date date = null;
			if (paysignReq.getOrderTime().length()==14) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				try {
					date = sdf.parse(paysignReq.getOrderTime());
				} catch (ParseException e) {
					logger.error("日期格式转换异常"+e.getMessage(),e);
				}
			}
			if (date == null) {
				logger.error("请求日期格式不是yyyyMMddHHmmss格式");
				return flag;
			}
		}
		
		try {
			if (StringUtils.isNotBlank(paysignReq.getPayType())) {
				Integer.valueOf(paysignReq.getPayType());
			} else if (StringUtils.isNotBlank(paysignReq.getPeriod())) {
				Integer.valueOf(paysignReq.getPeriod());
			} else if (StringUtils.isNotBlank(paysignReq.getAmount())) {
				Integer.valueOf(paysignReq.getAmount());
			}
		} catch (NumberFormatException e) {
			logger.error("payType或period或amount数据类型不正确");
			return flag;
		}
		
		if (!"PaySignReq".equals(paysignReq.getMsgType())) {
			logger.error("消息类型不为PaySignReq");
		} else if (!"1.0.0".equals(paysignReq.getVersion())) {
			logger.error("消息版本号不为1.0.0");
		} else if (StringUtils.isEmpty(paysignReq.getSystemCode())) {
			logger.error("内部系统代码不能为空");
		} else if (StringUtils.isEmpty(paysignReq.getServPltfmCode())) {
			logger.error("内部业务平台代码不能为空");
		} else if (StringUtils.isEmpty(paysignReq.getPayWay())) {
			logger.error("支付通道不能为空");
		} else if (StringUtils.isEmpty(paysignReq.getPayOrganization())) {
			logger.error("支付平台代码不能为空");
		} else if (StringUtils.isEmpty(paysignReq.getPayType())) {
			logger.error("支付方式不能为空");
		} else if (StringUtils.isEmpty(paysignReq.getIpAddress())) {
			logger.error("用户IP不能为空");
		} else if (StringUtils.isEmpty(paysignReq.getOrderId())) {
			logger.error("订单ID不能为空");
		} else if (StringUtils.isEmpty(paysignReq.getTradeId())){
			logger.error("外部交易id不能为空");
		} else if (StringUtils.isEmpty(paysignReq.getUserId())) {
			logger.error("用户id不能为空");
		} else if (StringUtils.isEmpty(paysignReq.getProductId())) {
			logger.error("产品代码不能为空");
		} else if (StringUtils.isEmpty(paysignReq.getProductName())) {
			logger.error("产品名称不能为空");
		} else if (StringUtils.isEmpty(paysignReq.getAmount())) {
			logger.error("订单金额不能为空");
		} else if (StringUtils.isEmpty(paysignReq.getPeriod())) {
			logger.error("订单有效期不能为空");
		} else if (StringUtils.isEmpty(paysignReq.getPeriodUnit())) {
			logger.error("有效期单位不能为空");
		} else if (StringUtils.isEmpty(paysignReq.getHmac())) {
			logger.error("数字签名不能为空");
		} else {
			flag = true;
		}
		return flag;
	}


}
