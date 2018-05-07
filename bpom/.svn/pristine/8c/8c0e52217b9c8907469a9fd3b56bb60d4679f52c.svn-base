package com.aspire.bpom.service;

import com.aspire.bpom.xml.bean.request.CancelContractReq;
import com.aspire.bpom.xml.bean.request.QryContractReq;
import com.aspire.bpom.xml.bean.response.CancelEntrustPayResp;
import com.aspire.bpom.xml.bean.response.QryEntrustPayResp;

public interface WXContractService {

	/**
	 * 查询签约关系接口业务处理
	 * @param qryContractReq
	 * @param qryContractResp
	 * @return
	 */
	public QryEntrustPayResp qryContractService(QryContractReq qryContractReq) throws Exception;
	
	/**
	 * 申请解约接口业务处理
	 * @param contractCode
	 * @return
	 * @throws Exception
	 */
	public CancelEntrustPayResp getCancelEntrustPayResp(CancelContractReq cancelContractReq) throws Exception;
	
}
