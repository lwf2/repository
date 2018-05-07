package com.aspire.bpom.web;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.bpom.constants.ErrorReturnCode;
import com.aspire.bpom.constants.ReturnCode;
import com.aspire.bpom.extensions.exception.BusinessException;
import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.extensions.log4j.ThreadLocalTransactionID;
import com.aspire.bpom.service.WXContractService;
import com.aspire.bpom.util.MD5Util;
import com.aspire.bpom.util.SystemManager;
import com.aspire.bpom.xml.bean.request.CancelContractReq;
import com.aspire.bpom.xml.bean.request.QryContractReq;
import com.aspire.bpom.xml.bean.response.CancelContractResp;
import com.aspire.bpom.xml.bean.response.CancelEntrustPayResp;
import com.aspire.bpom.xml.bean.response.QryContractResp;
import com.aspire.bpom.xml.bean.response.QryEntrustPayResp;
import com.aspire.bpom.xml.common.ParseXMLHelper;

@Controller
@RequestMapping("/weixin")
public class QryContractController {
	private static final Logger logger = BpomLogger.getLogger(QryContractController.class);
	
	private static ThreadLocal<String> dubugInfo = new ThreadLocal<String>();
	
	@Resource
	private  SystemManager sm;	
	@Resource
	private WXContractService wXContractService;

	/**
	 * 查询签约关系接口
	 * @param xml
	 * @return
	 * @throws BusinessException
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/qryContract", method = RequestMethod.POST)
	@ResponseBody
	public String qryContract(@RequestBody String xml){
		QryContractReq qryContractReq = ParseXMLHelper.parseXMLToObject(xml, QryContractReq.class);	
		String sessionId = qryContractReq.getContractCode();
		String logSession = ThreadLocalTransactionID.generateTransactionID(sessionId);
		ThreadLocalTransactionID.setTransactionID(logSession);
		logger.info("收到内部业务平台请求《查询签约关系接口》的消息报文为" + xml);
		
		
		
		QryContractResp qryContractResp = new QryContractResp();
		String returnXml = null;				
		try{
			//校验请求报文合法性。必填字段
			if(validataQryContractReq(qryContractReq)){
				//生成本地签名校验
				String _hmac = MD5Util.getCode(MD5Util.getEntityStr(qryContractReq) + sm.getSecretKeyRWK());
				logger.info("查询签约关系本地签名校验=" + _hmac);
				//签名校验
				if(_hmac.equals(qryContractReq.getHmac())){				
					//进行业务处理
					QryEntrustPayResp qryEnResp = wXContractService.qryContractService(qryContractReq);
					logger.info("支付网关返回的参数：" + qryEnResp.toString());
				    //组装返回任我看平台的参数报文
					if(ReturnCode.SUCCESS.equals(qryEnResp.getReturnCode())){	
						qryContractResp.setReturnCode(ReturnCode.SUCCESS);
						qryContractResp.setContractCode(qryEnResp.getWxContract_code());
						qryContractResp.setState(qryEnResp.getContract_state() == 0 ? 1 : 2);
						qryContractResp.setWxPlan_id(qryEnResp.getWxPlan_id());
						qryContractResp.setWxOpenid(qryEnResp.getWxOpenid());
						qryContractResp.setSignedTime(qryEnResp.getContract_signed_time());
						qryContractResp.setExpiredTime(qryEnResp.getContract_expired_time());
						qryContractResp.setUnSignTime(qryEnResp.getContract_terminated_time());
						qryContractResp.setUnSignMode(qryEnResp.getContract_termination_mode());
						qryContractResp.setUnSignMemo(qryEnResp.getCancelRemark());						
					}else{
						qryContractResp.setReturnCode(qryEnResp.getReturnCode());
						qryContractResp.setReturnMsg(qryEnResp.getReturnMsg());          
					}
				}else{
					logger.error("校验签名错误");
					qryContractResp.setReturnCode(ErrorReturnCode.CHECK_SIGN_ERROR);
					qryContractResp.setReturnMsg("签名校验错误");
				}
			}else{
				//参数不正确返回
				qryContractResp.setReturnCode(ErrorReturnCode.ERROR_REQUEST_PARAM);
				qryContractResp.setReturnMsg(dubugInfo.get());				
			}						
		} catch(Exception e){			
			logger.error("QryContractController.qryContract().error" + e);
			qryContractResp.setReturnCode(ErrorReturnCode.EXCEPTION);
			qryContractResp.setReturnMsg("查询签约关系接口异常");
		} finally {
			qryContractResp.setMsgType("QryContractResp");
			qryContractResp.setVersion("1.0.0");
			returnXml = ParseXMLHelper.object2XML(QryContractResp.class, qryContractResp);
			logger.info("返回任我看平台的xml returnXml：" + returnXml);
			return returnXml;
		}
	}
	
	/**
	 * 申请解约接口
	 * @param xml
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = "/cancelContract", method = RequestMethod.POST)
	@ResponseBody
	public String cancelContract(@RequestBody String xml){		
		CancelContractReq cancelContractReq = ParseXMLHelper.parseXMLToObject(xml, CancelContractReq.class);
		String sessionId = cancelContractReq.getContractCode();
		String logSession = ThreadLocalTransactionID.generateTransactionID(sessionId);
		ThreadLocalTransactionID.setTransactionID(logSession);
		logger.info("收到内部业务平台请求《申请解约接口》的消息报文为" + xml);
		CancelContractResp cancelContractResp = new CancelContractResp();
		String returnXml = null;
		try {
			//校验内部平台请求的参数
			if(validataCancelContractReq(cancelContractReq)){
				//生成本地签名校验
				String _hmac = MD5Util.getCode(MD5Util.getEntityStr(cancelContractReq) + sm.getSecretKeyRWK());		
				logger.info("申请解约接口本地签名校验=" + _hmac);
				//签名校验
				if(_hmac.equals(cancelContractReq.getHmac())){
					//业务处理
					CancelEntrustPayResp canEntPayResp = wXContractService.getCancelEntrustPayResp(cancelContractReq);
					//封装返回参数，并转化为xml格式
					cancelContractResp.setReturnCode(canEntPayResp.getReturnCode());
					cancelContractResp.setReturnMsg(canEntPayResp.getReturnMsg());
				}else{
					logger.error("签名校验错误");
					cancelContractResp.setReturnCode(ErrorReturnCode.CHECK_SIGN_ERROR);
					cancelContractResp.setReturnMsg("签名校验错误");	
				}				
			}else{	
				//参数不正确返回
				cancelContractResp.setReturnCode(ErrorReturnCode.ERROR_REQUEST_PARAM);
				cancelContractResp.setReturnMsg(dubugInfo.get());				
			}
		} catch(Exception e){			
			logger.error("QryContractController.cancelContract().error" + e);
			cancelContractResp.setReturnCode(ErrorReturnCode.EXCEPTION);
			cancelContractResp.setReturnMsg("申请解约接口异常");
		} finally{
			cancelContractResp.setMsgType("CancelContractResp");
			cancelContractResp.setVersion("1.0.0");
			returnXml = ParseXMLHelper.object2XML(CancelContractResp.class, cancelContractResp);
			logger.info("返回任我看平台的xml returnXml：" + returnXml);
			return returnXml;
		}	
	}
	
	/**
	 * 验证任我行平台-计费平台查询签约关系的数据
	 * @param qryContractReq
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private boolean validataQryContractReq(QryContractReq qryContractReq){
		String msg = "";
		if(StringUtils.isBlank(qryContractReq.getMsgType())){
			msg = "MsgType 消息类型为空 ";
			logger.error(msg);
			dubugInfo.set(msg);
			return false;
		}else if(!"QryContractReq".equals(qryContractReq.getMsgType())){
			msg = "MsgType 消息类型传参错误，该值为： QryContractReq";
			logger.error(msg);
			dubugInfo.set(msg);
			return false;
		}
		if(StringUtils.isBlank(qryContractReq.getVersion())){
			msg = "Version 版本号为空 ";
			logger.error(msg);
			dubugInfo.set(msg);
			return false;
		}else if(!"1.0.0".equals(qryContractReq.getVersion())){
			msg = "Version 版本号传参错误，该值为： 1.0.0";
			logger.error(msg);
			dubugInfo.set(msg);
			return false;
		}
		if(StringUtils.isBlank(qryContractReq.getSystemCode())){
			msg = "systemCode 内部系统代码为空 ";
			logger.error(msg);
			dubugInfo.set(msg);
			return false;
		}
		if(StringUtils.isBlank(qryContractReq.getWxPlan_id())){
		    msg = "wxPlan_id 协议模板id为空 ";
			logger.error(msg);
			dubugInfo.set(msg);
			return false;
		}
		if(StringUtils.isBlank(qryContractReq.getContractCode())){
			msg = "contractCode 签约协议号为空 ";
			logger.error(msg);
			dubugInfo.set(msg);
			return false;
		}		
		if(StringUtils.isBlank(qryContractReq.getHmac())){
			msg = "hmac 数字签名为空 ";
			logger.error(msg);
			dubugInfo.set(msg);
			return false;
		}
		return true;
	}

	/**
	 * 验证任我行平台-计费平台申请解约接口的数据
	 * @param canConReq
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private boolean validataCancelContractReq(CancelContractReq canConReq){
		String msg = "";
		if(StringUtils.isBlank(canConReq.getMsgType())){
			msg = "MsgType 消息类型为空";
			logger.error(msg);
			dubugInfo.set(msg);
			return false;
		}else if(!"CancelContractReq".equals(canConReq.getMsgType())){
			msg = "MsgType 消息类型传参错误，该值为：CancelContractReq";
			logger.error(msg);
			dubugInfo.set(msg);
			return false;
		}
		if(StringUtils.isBlank(canConReq.getVersion())){
			msg = "Version 版本号为空 ";
			logger.error(msg);
			dubugInfo.set(msg);
			return false;
		}else if(!"1.0.0".equals(canConReq.getVersion())){
			msg = "Version 版本号传参错误，该值为： 1.0.0";
			logger.error(msg);
			dubugInfo.set(msg);
			return false;
		}
		if(StringUtils.isBlank(canConReq.getSystemCode())){
			msg = "systemCode 内部系统代码为空 ";
			logger.error(msg);
			dubugInfo.set(msg);
			return false;
		}
		if(StringUtils.isBlank(canConReq.getWxPlan_id())){
		    msg = "wxPlan_id 协议模板id为空 ";
			logger.error(msg);
			dubugInfo.set(msg);
			return false;
		}
		if(StringUtils.isBlank(canConReq.getContractCode())){
			msg = "contractCode 签约协议号为空 ";
			logger.error(msg);
			dubugInfo.set(msg);
			return false;
		}
		if(StringUtils.isBlank(canConReq.getUnSignMemo())){
			msg = "unSignMemo 解约备注为空 ";
			logger.error(msg);
			dubugInfo.set(msg);
			return false;
		}
		if(StringUtils.isBlank(canConReq.getHmac())){
			msg = "hmac 数字签名为空";
			logger.error(msg);
			dubugInfo.set(msg);
			return false;
		}
		return true;
	}
}
