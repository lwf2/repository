package com.aspire.bpom.service.impl;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.aspire.bpom.bean.SignPO;
import com.aspire.bpom.constants.ReturnCode;
import com.aspire.bpom.dao.SignDao;
import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.service.WXContractService;
import com.aspire.bpom.util.HttpClientUtil;
import com.aspire.bpom.util.SystemManager;
import com.aspire.bpom.xml.bean.request.CancelContractReq;
import com.aspire.bpom.xml.bean.request.CancelEntrustPayReq;
import com.aspire.bpom.xml.bean.request.QryContractReq;
import com.aspire.bpom.xml.bean.request.QryEntrustPayReq;
import com.aspire.bpom.xml.bean.response.CancelEntrustPayResp;
import com.aspire.bpom.xml.bean.response.QryEntrustPayResp;
import com.aspire.bpom.xml.common.ParseXMLHelper;

@Service
public class WXContractServiceImpl implements WXContractService {
	
	private static final Logger logger = BpomLogger.getLogger(WXContractServiceImpl.class);

	@Resource
	private SignDao signDao;

	@Resource
	private SystemManager sm;
	
	@Override
	public QryEntrustPayResp qryContractService(QryContractReq qryContractReq) throws Exception {		
		//组装调用支付网关的参数。
		QryEntrustPayReq qryEntPayReq =  getQryEntrustPayReq(qryContractReq);		
        logger.info("请求支付网关的参数为：" + qryEntPayReq.toString());
		//组装报文请求支付网关
		return postQryEntrustPayResp(qryEntPayReq);
	}

	/**
	 * 组装调用支付网关的参数
	 * @param qryContractReq
	 * @param qryContractResp
	 * @return
	 * @throws Exception 
	 */
	private QryEntrustPayReq getQryEntrustPayReq(QryContractReq qryContractReq)
			throws Exception{
		QryEntrustPayReq qryEntrustPayReq = new QryEntrustPayReq();
		qryEntrustPayReq.setMsgType("QryEntrustPayReq");
		qryEntrustPayReq.setMsgVer("1.0");
		qryEntrustPayReq.setSystemCode(qryContractReq.getSystemCode());//内部系统代码
		qryEntrustPayReq.setWxPlan_id(qryContractReq.getWxPlan_id());//协议模板ID
		qryEntrustPayReq.setWxContract_code(qryContractReq.getContractCode());//签约协议号
		return qryEntrustPayReq;
	}
	
	/**
	 * 请求支付网关
	 * @param qryEntPayReq
	 * @param qryContractResp
	 * @return
	 * @throws Exception
	 */
	private QryEntrustPayResp postQryEntrustPayResp(QryEntrustPayReq qryEntPayReq) throws Exception{
		QryEntrustPayResp qryEntPayResp = null;
		try {
			String subReqXml = ParseXMLHelper.object2XML(QryEntrustPayReq.class, qryEntPayReq);
			String subRespXml = HttpClientUtil.doHttpResult(sm.getQryEntrustPayReqURL()//读取配置文件
					, subReqXml);
			//将支付网关返回的xml数据转化为实体类
			qryEntPayResp = ParseXMLHelper.parseXMLToObject(subRespXml, QryEntrustPayResp.class);
			if(ReturnCode.SUCCESS.equals(qryEntPayResp.getReturnCode())){//查询成功后更新微信签约表
				SignPO signPO = new SignPO();
				signPO.setContractCode(qryEntPayResp.getWxContract_code());//签约协议号
				signPO.setUnSignMode(qryEntPayResp.getContract_termination_mode());//协议解约方式
				signPO.setState(qryEntPayResp.getContract_state() == 0 ? 1 : 2);//更新签约表的状态
				signDao.updateState(signPO);//更新解约状态及信息
			}
		} catch (Exception e) {
			logger.error("WXContractServiceImpl.postQryEntrustPayResp().error 支付网关响应异常" + e);
			throw new Exception("WXContractServiceImpl.postQryEntrustPayResp().error 支付网关响应异常", e);
		}		
		return qryEntPayResp;
	}

	@Override
	public CancelEntrustPayResp getCancelEntrustPayResp(CancelContractReq cancelContractReq)
			throws Exception {
		//组装发送支付网关的参数
		CancelEntrustPayReq cancelEntPayReq = getCancelEntrustPayReq(cancelContractReq);
		logger.info("请求支付网关的参数为：" + cancelEntPayReq.toString());		
		//组装报文请求支付网关并更新签约表
		return postCancelEntrustPayResp(cancelEntPayReq);
	}

	/**
	 * 组装发送支付网关的参数
	 * @param cancelContractReq
	 * @param cancelContractResp
	 * @return
	 * @throws Exception 
	 */
	private CancelEntrustPayReq getCancelEntrustPayReq(CancelContractReq cancelContractReq)
			throws Exception{
		CancelEntrustPayReq CanEntPayReq = new CancelEntrustPayReq();
		try {
			String contractCode = cancelContractReq.getContractCode();//签约协议号
			Map<String, String> paramMap = signDao.getParams(contractCode);		
			CanEntPayReq.setMsgType("CancelEntrustPayReq");//消息类型
			CanEntPayReq.setMsgVer("1.0");//该接口消息的版本号			
			CanEntPayReq.setSystemCode(cancelContractReq.getSystemCode());//接入支付网关内部网元代码
			CanEntPayReq.setServPltfmCode(paramMap.get("SERVPLTFM_CODE"));//内部业务平台代码
			CanEntPayReq.setPayOrganization("WEIXIN");//支付机构
			CanEntPayReq.setWxContract_id(paramMap.get("CONTRACT_ID"));//委托代扣协议id
			CanEntPayReq.setWxPlan_id(cancelContractReq.getWxPlan_id());//协议模板id
			CanEntPayReq.setWxContract_code(contractCode);//签约协议号
			CanEntPayReq.setCancelRemark(cancelContractReq.getUnSignMemo());//解约备注
		} catch (Exception e) {
			logger.error("WXContractServiceImpl.getCancelEntrustPayReq().error 模板ID查询失败：" + e);
            throw new Exception("WXContractServiceImpl.getCancelEntrustPayReq().error 模板ID查询失败", e);
		}
		return CanEntPayReq;
	}
	
	/**
	 * 请求支付网关
	 * @param CanEntPayReq
	 * @param cancelContractResp
	 * @return
	 * @throws Exception
	 */
	private CancelEntrustPayResp postCancelEntrustPayResp(CancelEntrustPayReq CanEntPayReq)
			throws Exception{
		CancelEntrustPayResp canEntPayResp = new CancelEntrustPayResp();
		try {
			//组装报文请求支付网关
			String subReqXml = ParseXMLHelper.object2XML(CancelEntrustPayReq.class, CanEntPayReq);
			String subRespXml = HttpClientUtil.doHttpResult(sm.getCancelEntrustPayReqURL()//读取配置文件
					, subReqXml);
			//把返回的参数转化为实体类
			canEntPayResp = ParseXMLHelper.parseXMLToObject(subRespXml, CancelEntrustPayResp.class);
			if(ReturnCode.SUCCESS.equals(canEntPayResp.getReturnCode())){//解约成功后更新微信签约表的状态
				SignPO signPO = new SignPO();
				signPO.setContractCode(CanEntPayReq.getWxContract_code());//签约协议号
				signPO.setUnSignMemo(CanEntPayReq.getCancelRemark());//解约备注
				signDao.updateState(signPO);//更新解约状态及信息
			}
		} catch (Exception e) {
			logger.error("WXContractServiceImpl.postCancelEntrustPayResp().error 支付网关响应异常" + e);
			throw new Exception("WXContractServiceImpl.postCancelEntrustPayResp().error 支付网关响应异常", e);
		}		
		return canEntPayResp;
	}
}
