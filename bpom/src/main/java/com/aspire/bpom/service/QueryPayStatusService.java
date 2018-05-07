package com.aspire.bpom.service;

import com.aspire.bpom.extensions.exception.BusinessException;
import com.aspire.bpom.xml.bean.request.QryPayReq;
import com.aspire.bpom.xml.bean.response.QueryPayStatusResp;

/**
 * 查询支付状态
 * @author chenpeng
 *
 */
public interface QueryPayStatusService {
	QueryPayStatusResp queryPayStatsusToPayGate(QryPayReq qryPayReq) throws BusinessException;
}
