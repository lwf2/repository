package com.aspire.bpom.web;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.bpom.bean.SignPO;
import com.aspire.bpom.constants.ErrorReturnCode;
import com.aspire.bpom.constants.ReturnCode;
import com.aspire.bpom.constants.SignStatus;
import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.extensions.log4j.ThreadLocalTransactionID;
import com.aspire.bpom.service.ContractService;
import com.aspire.bpom.service.SignService;
import com.aspire.bpom.thread.NotifyThreadPool;
import com.aspire.bpom.xml.bean.request.EntrustPayNotifyReq;
import com.aspire.bpom.xml.bean.response.EntrustPayNotifyResp;
import com.aspire.bpom.xml.common.ParseXMLHelper;

/**
 * 签约/解约结果通知
 * @author chenpeng
 *
 */
@Controller
public class ContractNotifyController {

	private static final Logger log = BpomLogger.getLogger(ContractNotifyController.class);

	@Resource
	private ContractService contractService;
	@Resource
	private SignService signService;
	
	@Resource
	private NotifyThreadPool notifyThreadPool;
	
	@RequestMapping("weixin/contractResultNotify")
	@ResponseBody
	public String contractNotify(@RequestBody String xml) {
		EntrustPayNotifyReq entrustPayNotifyReq = ParseXMLHelper.parseXMLToObject(xml, EntrustPayNotifyReq.class);
		String sessionId = entrustPayNotifyReq.getWxContract_code();
		String logSession = ThreadLocalTransactionID.generateTransactionID(sessionId);
		ThreadLocalTransactionID.setTransactionID(logSession);
		log.info("收到支付网关请求的消息报文为："+ xml);
		
		String returnXml = null;
		EntrustPayNotifyResp entrusPayNotifyResp =  new EntrustPayNotifyResp();

		try {
			//校验请求参数
			if (checkParameter(entrustPayNotifyReq)) {
				SignPO signpo = signService.getSignByContractCode(entrustPayNotifyReq.getWxContract_code());
				if (signpo == null) {
					log.error("查询不到签约信息");
					entrusPayNotifyResp.setReturnCode(ReturnCode.FAIL);
					entrusPayNotifyResp.setReturnMsg("查询不到签约信息");
				} else {
					signpo.setContractId(entrustPayNotifyReq.getWxContract_id());//微信委托代扣协议ID
					//设置签约状态
					if ("ADD".equals(entrustPayNotifyReq.getChange_type())) {
						signpo.setState(Integer.valueOf(SignStatus.SUCCESS_SIGN));//签约
						signpo.setContractId(entrustPayNotifyReq.getWxContract_id());//委托代扣协议id
						signpo.setSignedTime(entrustPayNotifyReq.getWxOperate_time());//签约时间
						signpo.setExpiredTime(entrustPayNotifyReq.getWxContract_expired());//协议到期时间

					} else if("DELETE".equals(entrustPayNotifyReq.getChange_type())) {
						signpo.setState(Integer.valueOf(SignStatus.SURRENDER_SIGN));//解约
						signpo.setUnSignTime(entrustPayNotifyReq.getWxOperate_time());//解约时间
					}
					//更新签约表状态
					signService.updateSign(signpo);
					log.info("更新签约状态成功。");
//					ContractNotifyResp contractNotifyResp = contractService.contractToRWK(entrustPayNotifyReq);
					entrustPayNotifyReq.setTimingNumber(0);//设置重发通知的次数
					entrustPayNotifyReq.setSessionId(logSession);//为了方便查询日志，设的记号
					notifyThreadPool.execute(entrustPayNotifyReq);
					log.info("发送通知成功，启动延时通知线程");
					entrusPayNotifyResp.setReturnCode(ReturnCode.SUCCESS);
					entrusPayNotifyResp.setReturnMsg("");
				}
			} else {
				//参数校验不正确返回
				entrusPayNotifyResp.setReturnCode(ErrorReturnCode.ERROR_REQUEST_PARAM);
				entrusPayNotifyResp.setReturnMsg("通知参数检验出错");
			}
		} catch (Exception e) {
			log.error("签约结果通知处理异常");
			entrusPayNotifyResp.setReturnCode(ErrorReturnCode.EXCEPTION);
			entrusPayNotifyResp.setReturnMsg("签约结果通知处理异常");
		}
		entrusPayNotifyResp.setMsgType("EntrustPayNotifyResp");
		entrusPayNotifyResp.setMsgVer("1.0");
		returnXml = ParseXMLHelper.object2XML(EntrustPayNotifyResp.class, entrusPayNotifyResp);
		log.info("响应给支付网关的数据为："+returnXml);
		return returnXml;
	}
	
	/**
	 * 请求参数校验
	 * 
	 * @return
	 */
	private boolean checkParameter(EntrustPayNotifyReq entrustPayNotifyReq) {
		boolean flag = false;
		if (!"EntrustPayNotifyReq".equals(entrustPayNotifyReq.getMsgType())) {
			log.error("支付网关通知消息类型不为EntrustPayNotifyReq");
		} else if (!"1.0".equals(entrustPayNotifyReq.getMsgVer())) {
			log.error("接口消息版本号不为1.0");
		} else if (StringUtils.isEmpty(entrustPayNotifyReq.getRequestId())) {
			log.error("支付网关交易流水号为空");
		} else if (StringUtils.isEmpty(entrustPayNotifyReq.getChange_type())) {
			log.error("change_type不能为空");
		} else if (StringUtils.isEmpty(entrustPayNotifyReq.getWxContract_id())) {
			log.error("委托代扣协议不能为空");
		} else if (StringUtils.isEmpty(entrustPayNotifyReq.getWxContract_expired())) {
			log.error("协议到期时间不能为空");
		} else if (StringUtils.isEmpty(entrustPayNotifyReq.getWxOperate_time())) {
			log.error("操作时间不能为空");
		} else if (StringUtils.isEmpty(entrustPayNotifyReq.getWxOpenid())) {
			log.error("微信用户标识");
		} else {
			flag = true;
		}
		return flag;
	}
	
}
